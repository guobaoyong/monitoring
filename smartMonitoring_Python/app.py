from flask import Flask, jsonify, request
import interface.lstm as lstm
import interface.predict as test
import interface.read_data as read

app = Flask(__name__)


@app.route('/predict')
def predict():
    sensors_id = request.args["sensors_id"]
    type = request.args["type"]
    # 训练模型
    score = lstm.fit_model(sensors_id,type)
    # 预测数据并写入数据库
    test.predict(sensors_id,type,score)
    return jsonify(code=200,message="预测模块执行成功")

@app.route('/read_data')
def read_data():
    read.data()
    return jsonify(code=200,message="数据读取写入成功")

if __name__ == '__main__':
    app.run()
