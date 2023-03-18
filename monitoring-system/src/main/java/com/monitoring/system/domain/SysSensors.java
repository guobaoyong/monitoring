package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 传感器管理对象 sys_sensors
 * 

 * @date 2023-03-15
 */
public class SysSensors extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 传感器ID */
    private Long sensorsId;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 部署地址 */
    @Excel(name = "部署地址")
    private String address;

    /** mac地址 */
    @Excel(name = "mac地址")
    private String mac;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    /** 预警值 */
    @Excel(name = "预警值")
    private String earlyWarning;

    public void setSensorsId(Long sensorsId) 
    {
        this.sensorsId = sensorsId;
    }

    public Long getSensorsId() 
    {
        return sensorsId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setMac(String mac) 
    {
        this.mac = mac;
    }

    public String getMac() 
    {
        return mac;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setEarlyWarning(String earlyWarning) 
    {
        this.earlyWarning = earlyWarning;
    }

    public String getEarlyWarning() 
    {
        return earlyWarning;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("sensorsId", getSensorsId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("type", getType())
            .append("status", getStatus())
            .append("address", getAddress())
            .append("mac", getMac())
            .append("ip", getIp())
            .append("earlyWarning", getEarlyWarning())
            .toString();
    }
}
