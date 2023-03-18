package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 预测记录对象 sys_predict
 *

 * @date 2023-03-17
 */
public class SysPredict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预测ID
     */
    private Long predictId;

    /**
     * 预测曲线数据
     */
    @Excel(name = "预测曲线数据")
    private String predictCurve;

    /**
     * 预测日期
     */
    @Excel(name = "预测日期")
    private String predictDay;

    /**
     * 传感器ID
     */
    @Excel(name = "传感器ID")
    private Long sensorId;

    /**
     * loss图像
     */
    @Excel(name = "loss图像")
    private String lossCurve;

    /**
     * 测试图像
     */
    @Excel(name = "测试图像")
    private String testCurve;

    /**
     * 准确率
     */
    @Excel(name = "准确率")
    private String score;

    /**
     * 训练数据
     */
    @Excel(name = "训练数据")
    private String predictTest;

    public void setPredictId(Long predictId) {
        this.predictId = predictId;
    }

    public Long getPredictId() {
        return predictId;
    }

    public void setPredictCurve(String predictCurve) {
        this.predictCurve = predictCurve;
    }

    public String getPredictCurve() {
        return predictCurve;
    }

    public void setPredictDay(String predictDay) {
        this.predictDay = predictDay;
    }

    public String getPredictDay() {
        return predictDay;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setLossCurve(String lossCurve) {
        this.lossCurve = lossCurve;
    }

    public String getLossCurve() {
        return lossCurve;
    }

    public void setTestCurve(String testCurve) {
        this.testCurve = testCurve;
    }

    public String getTestCurve() {
        return testCurve;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setPredictTest(String predictTest) {
        this.predictTest = predictTest;
    }

    public String getPredictTest() {
        return predictTest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("predictId", getPredictId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("predictCurve", getPredictCurve())
                .append("predictDay", getPredictDay())
                .append("sensorId", getSensorId())
                .append("lossCurve", getLossCurve())
                .append("testCurve", getTestCurve())
                .append("score", getScore())
                .append("predictTest", getPredictTest())
                .toString();
    }
}