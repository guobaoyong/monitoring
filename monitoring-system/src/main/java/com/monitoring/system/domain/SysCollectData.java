package com.monitoring.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 采集记录对象 sys_collect_data
 *

 * @date 2023-03-16
 */
public class SysCollectData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 采集记录ID
     */
    private Long collectId;

    /**
     * 传感器ID
     */
    @Excel(name = "传感器ID")
    private Long sensorId;

    /**
     * 温度数据
     */
    @Excel(name = "温度数据")
    private String temperature;

    /**
     * 湿度数据
     */
    @Excel(name = "湿度数据")
    private String humidity;

    /**
     * 采集时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;

    /**
     * 是否异常
     */
    @Excel(name = "是否异常")
    private String abnormal;

    /**
     * 是否预警
     */
    @Excel(name = "是否预警")
    private String earlyWarning;

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public Long getCollectId() {
        return collectId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public String getAbnormal() {
        return abnormal;
    }

    public void setEarlyWarning(String earlyWarning) {
        this.earlyWarning = earlyWarning;
    }

    public String getEarlyWarning() {
        return earlyWarning;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("collectId", getCollectId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("sensorId", getSensorId())
                .append("temperature", getTemperature())
                .append("humidity", getHumidity())
                .append("collectTime", getCollectTime())
                .append("abnormal", getAbnormal())
                .append("earlyWarning", getEarlyWarning())
                .toString();
    }
}
