import pymysql
import datetime
import numpy as np
import pandas as pd
from sklearn.preprocessing import MinMaxScaler
from keras.models import load_model
import json
import base64


def predict(sensors_id, type, score):
    print(score)
    # MySQL相关配置
    sql_connection = pymysql.connect(host='localhost', user='root', password='root',
                                     db='monitoring', port=3306, autocommit=False, charset='utf8mb4')
    # 设置游标
    cursor = sql_connection.cursor()

    # 从数据库里读取数据进行预测
    sql = """SELECT collect_id,{},collect_time FROM sys_collect_data WHERE sensor_id = {}""".format(type,
                                                                                                    sensors_id)
    dataset = pd.read_sql(sql, sql_connection)

    dataset['year'] = dataset['collect_time'].dt.year
    dataset['month'] = dataset['collect_time'].dt.month
    dataset['day'] = dataset['collect_time'].dt.day
    dataset['hour'] = dataset['collect_time'].dt.hour
    dataset['minute'] = dataset['collect_time'].dt.minute

    # 由于温度与每日的小时变化有关系，而且0-23作为一个循环，所以用三角函数提取周期信息，sin和cos同时使用是因为确保24小时为一个周期——用就完了（建议参考相关资料）
    dataset['sin(h)'] = [np.sin((x) * (2 * np.pi / 24)) for x in dataset['hour']]
    dataset['cos(h)'] = [np.cos((x) * (2 * np.pi / 24)) for x in dataset['hour']]

    data = []
    ids = []
    for index, row in dataset.iterrows():
        collect_time = datetime.datetime.strptime(str(row['collect_time']), '%Y-%m-%d %H:%M:%S')
        if ((datetime.datetime.now().month == collect_time.month) and (
                datetime.datetime.now().day == collect_time.day)):
            data.append(row)
            ids.append(row['collect_id'])

    model = load_model(type + '.h5')

    future = ['sin(h)', 'cos(h)', 'year', 'month', 'day', 'hour', 'minute', type]
    for col in future:
        scaler = MinMaxScaler()
        if (col not in ['sin(h)', 'cos(h)']):
            dataset[col] = scaler.fit_transform(dataset[col].values.reshape(-1, 1))

    x = dataset[future]
    y = dataset[type]

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

    test_dataset, test_labels = multivariate_data(x, y, 0, len(data), 3, 1, 1, True)

    curve = json.dumps(scaler.inverse_transform(model.predict(test_dataset)).tolist())

    # 'rb'加上才能将图片读取为二进制
    fin1 = open('loss' + type + '.jpg', 'rb')
    fin2 = open('test' + type + '.jpg', 'rb')
    # 将二进制数据读取到img中
    ls_f1 = base64.b64encode(fin1.read())
    ls_f2 = base64.b64encode(fin2.read())
    fin1.close()
    fin2.close()

    sql1 = "INSERT INTO sys_predict(predict_curve,predict_day,sensor_id,loss_curve,test_curve,score,predict_test) VALUES (%s,%s,%s,%s,%s,%s,%s)"
    args = (curve, datetime.datetime.today().date() + datetime.timedelta(days=1), sensors_id, ls_f1, ls_f2, score,
            ",".join([str(x) for x in ids]))
    cursor.execute(sql1, args)

    sql_connection.commit()

    cursor.close()
    sql_connection.close()
