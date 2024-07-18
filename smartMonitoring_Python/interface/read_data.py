import pandas as pd
import pymysql


def data():
    # MySQL相关配置
    sql_connection = pymysql.connect(host='localhost', user='root', password='root',
                                     db='monitoring', port=3306, autocommit=False, charset='utf8mb4')
    # 设置游标
    cursor = sql_connection.cursor()

    # 从csv中读取数据
    data_path = "D:\program\PyCharmProjects\smartMonitoring_lstm\interface\jena_climate_2016.csv"
    dataset = pd.read_csv(data_path, parse_dates=['Date Time'], index_col=['Date Time'])

    # 温度数据
    dataset['T (degC)']
    # 湿度数据
    dataset['rh (%)']

    # 遍历dataset
    for index, row in dataset.iterrows():
        sql1 = "INSERT INTO sys_collect_data(sensor_id,temperature,collect_time) VALUES (%s,%s,%s)"
        args1 = (1, round(row['T (degC)'], 2), index)
        cursor.execute(sql1, args1)
        sql2 = "INSERT INTO sys_collect_data(sensor_id,temperature,collect_time) VALUES (%s,%s,%s)"
        args2 = (2, round(row['T (degC)'] + 0.5, 2), index)
        cursor.execute(sql2, args2)
        sql3 = "INSERT INTO sys_collect_data(sensor_id,temperature,collect_time) VALUES (%s,%s,%s)"
        args3 = (3, round(row['T (degC)'] - 0.5, 2), index)
        cursor.execute(sql3, args3)
        sql4 = "INSERT INTO sys_collect_data(sensor_id,humidity,collect_time) VALUES (%s,%s,%s)"
        args4 = (4, round(row['rh (%)'], 2), index)
        cursor.execute(sql4, args4)
        sql5 = "INSERT INTO sys_collect_data(sensor_id,humidity,collect_time) VALUES (%s,%s,%s)"
        args5 = (5, round(row['rh (%)'] + 0.1, 2), index)
        cursor.execute(sql5, args5)
        sql6 = "INSERT INTO sys_collect_data(sensor_id,humidity,collect_time) VALUES (%s,%s,%s)"
        args6 = (6, round(row['rh (%)'] - 0.1, 2), index)
        cursor.execute(sql6, args6)
        sql_connection.commit()

    cursor.close()
    sql_connection.close()
