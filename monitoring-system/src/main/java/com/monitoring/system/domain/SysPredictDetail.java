package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 预测详情对象 sys_predict_detail
 * 
 * @author ruoyi
 * @date 2023-03-17
 */
public class SysPredictDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 详细ID */
    private Long detailId;

    /** 预测ID */
    private Long predictId;

    /** 预测时间 */
    @Excel(name = "预测时间")
    private String predictDay;

    /** 预测值 */
    @Excel(name = "预测值")
    private String predictValue;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setPredictId(Long predictId) 
    {
        this.predictId = predictId;
    }

    public Long getPredictId() 
    {
        return predictId;
    }
    public void setPredictDay(String predictDay) 
    {
        this.predictDay = predictDay;
    }

    public String getPredictDay() 
    {
        return predictDay;
    }
    public void setPredictValue(String predictValue) 
    {
        this.predictValue = predictValue;
    }

    public String getPredictValue() 
    {
        return predictValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("predictId", getPredictId())
            .append("predictDay", getPredictDay())
            .append("predictValue", getPredictValue())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
