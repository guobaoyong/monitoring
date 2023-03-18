package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 位置数据对象 sys_data_record
 *

 * @date 2023-03-15
 */
public class SysDataRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 数据记录ID
     */
    private Long dataId;

    /**
     * 部署区域长/宽
     */
    @Excel(name = "部署区域长/宽")
    private Long dataNum;

    /**
     * 部署维度
     */
    @Excel(name = "部署维度")
    private String dataDim;

    /**
     * 参与人员
     */
    @Excel(name = "参与人员")
    private String people;

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataNum(Long dataNum) {
        this.dataNum = dataNum;
    }

    public Long getDataNum() {
        return dataNum;
    }

    public void setDataDim(String dataDim) {
        this.dataDim = dataDim;
    }

    public String getDataDim() {
        return dataDim;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dataId", getDataId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("dataNum", getDataNum())
                .append("dataDim", getDataDim())
                .append("people", getPeople())
                .toString();
    }
}
