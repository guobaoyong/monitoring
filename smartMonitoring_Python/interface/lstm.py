import pymysql
import tensorflow as tf
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import r2_score
from keras import utils
from keras.callbacks import ModelCheckpoint


def fit_model(sensors_id, type):
    # MySQL相关配置
    sql_connection = pymysql.connect(host='localhost', user='root', password='root',
                                     db='monitoring', port=3306, autocommit=False, charset='utf8mb4')
    # 设置游标
    cursor = sql_connection.cursor()
    # 从数据库里读取数据进行预测
    sql = """SELECT {},collect_time FROM sys_collect_data WHERE sensor_id = {}""".format(type, sensors_id)
    dataset = pd.read_sql(sql, sql_connection)

    dataset['year'] = dataset['collect_time'].dt.year
    dataset['month'] = dataset['collect_time'].dt.month
    dataset['day'] = dataset['collect_time'].dt.day
    dataset['hour'] = dataset['collect_time'].dt.hour
    dataset['minute'] = dataset['collect_time'].dt.minute

    # 用三角函数提取周期信息，sin和cos同时使用是因为确保24小时为一个周期
    dataset['sin(h)'] = [np.sin((x) * (2 * np.pi / 24)) for x in dataset['hour']]
    dataset['cos(h)'] = [np.cos((x) * (2 * np.pi / 24)) for x in dataset['hour']]

    # 定义切分函数，x是选取的特征组成的例表，y是标签列（x=dataset[future=] ，y=dataset['temperature']）
    # 上面的一个使用的意思就是：从0开始数到10万，按照3条x数据作为一个元素放入data-》1条y数据作为一个元素存入labels，step=1表示每一条数据就按照上面包装一次，比如data[0]=x[0，1，2]->labels[0]=y[3];data[1]=x[1,2,3]->labels[1]=y[4];
    # single_step意思是只预测目标的一个未来状态，只预测后1小时，设置为false可以预测未来0到target_size小时内的温度。

    def multivariate_data(x, y, start_index, end_index, history_size,
                          target_size, step, single_step):
        data = []
        labels = []
        start_index = start_index + history_size

        if end_index is None:
            end_index = len(dataset) - target_size

        for i in range(start_index, end_index):
            indices = range(i - history_size, i, step)  # step表示滑动步长
            mid_data = x.iloc[indices]
            data.append(mid_data)

            if single_step:
                mid_data = y.iloc[i + target_size]
                labels.append(mid_data)
            else:
                labels.append(y.iloc[i:i + target_size])

        return np.array(data), np.array(labels)

    future = ['sin(h)', 'cos(h)', 'year', 'month', 'day', 'hour', 'minute', type]
    # 数据归一化，由于sin和cos本来就是-1到1，不用归一化
    for col in future:
        scaler = MinMaxScaler()
        if (col not in ['sin(h)', 'cos(h)']):
            dataset[col] = scaler.fit_transform(dataset[col].values.reshape(-1, 1))

    # 获取训练特征和训练标签
    x = dataset[future]
    y = dataset[type]

    # 通过3-7划分训练集和测试集，70%为训练集
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.7, shuffle=False, random_state=13)

    # 取得训练集，和测试集的格式——》(3,6)->(1,)通过3行历史数据7列目标特征预测1行1列的目标
    train_dataset, train_labels = multivariate_data(x_train, y_train, 0, 10000, 3, 1, 1, True)
    test_dataset, test_labels = multivariate_data(x_test, y_test, 0, 10000, 3, 1, 1, True)

    # 创建训练组
    # 该函数目标是把刚建好的训练集/测试集转化成tensorflow的数据集格式，打乱分组方便训练模型......
    def create_batch_dataset(x, y, train=True, buffer_size=1000, batch_size=128):
        batch_data = tf.data.Dataset.from_tensor_slices((tf.constant(x), tf.constant(y)))
        if train:
            return batch_data.cache().shuffle(buffer_size).batch(batch_size)
        else:
            return batch_data.batch(batch_size)

    # 使用上面函数
    train_batch_dataset = create_batch_dataset(train_dataset, train_labels)
    test_batch_dataset = create_batch_dataset(test_dataset, test_labels, train=False)
    # 拿一个测试集元素查看格式
    list(test_batch_dataset.as_numpy_iterator())[0]

    # 建立神经网络模型-3层LSTM和一个输出层
    model = tf.keras.models.Sequential([
        tf.keras.layers.LSTM(256, input_shape=train_dataset.shape[-2:], return_sequences=True),
        # input_shape=(20,1) 不包含批处理维度
        tf.keras.layers.Dropout(0.4),
        tf.keras.layers.LSTM(128, return_sequences=True),
        tf.keras.layers.Dropout(0.3),
        tf.keras.layers.LSTM(32),
        tf.keras.layers.Dense(1)
    ])

    # 优化器和损失函数设置
    model.compile(optimizer='adam', loss='mse')

    # 模型保存的相关设置
    utils.plot_model(model)
    checkpoint_file = type + '.hdf5'
    checkpoint_callback = ModelCheckpoint(filepath=checkpoint_file, monitor='loss', moode='min', save_best_only=True,
                                          save_weights_only=True)
    # 模型训练
    history = model.fit(train_batch_dataset, epochs=30, validation_data=test_batch_dataset,
                        callbacks=[checkpoint_callback])
    model.save(type + '.h5')

    # 最喜欢的绘图环节，通过history获取模型每步训练取得的结果loss和val_loss
    plt.figure(figsize=(8, 8), dpi=200)
    plt.plot(history.history['loss'])
    plt.plot(history.history['val_loss'])
    plt.title('model train vs validation loss')
    plt.ylabel('loss')
    plt.xlabel('epoch')
    plt.legend(['train', 'validation'], loc='best')
    plt.savefig('loss'+type+'.jpg')

    test_dataset.shape
    # 通过输入一组数据预测
    test_preds = model.predict(test_dataset, verbose=1)
    test_preds[:10]
    # 将预测后的一组数据转化为1维方便比较
    test_preds = test_preds[:, 0]
    test_preds[:10]

    test_labels.shape
    # r2检验，越接近1效果越好
    score = r2_score(test_labels, test_preds)

    # 做出预测结果和实际结果的曲线对比，使用1000次结果对比
    plt.figure(figsize=(16, 8))
    plt.plot(test_labels[:1000], label="True value")
    plt.plot(test_preds[:1000], label="Pred value")
    plt.legend(loc='best')
    plt.savefig('test' + type + '.jpg')

    cursor.close()
    sql_connection.close()

    return score
