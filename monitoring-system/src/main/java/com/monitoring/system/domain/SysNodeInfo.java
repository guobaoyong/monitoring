package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 位置详情对象 sys_node_info
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public class SysNodeInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long nodeId;

    /**
     * 位置x
     */
    @Excel(name = "位置x")
    private Double nodeX;

    /**
     * 位置y
     */
    @Excel(name = "位置y")
    private Double nodeY;

    /**
     * 位置z
     */
    @Excel(name = "位置z")
    private Double nodeZ;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long nodeSort;

    /**
     * 初始能量
     */
    @Excel(name = "初始能量")
    private Double nodeEnergy;

    /**
     * 位置数据ID
     */
    @Excel(name = "位置数据ID")
    private Long dataId;

    /**
     * 位置类型
     */
    @Excel(name = "位置类型")
    private String nodeType;

    /**
     * 感知半径
     */
    @Excel(name = "感知半径")
    private Double nodeRs;

    /**
     * 传感器ID
     */
    @Excel(name = "传感器ID")
    private Long sensorsId;

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeX(Double nodeX) {
        this.nodeX = nodeX;
    }

    public Double getNodeX() {
        return nodeX;
    }

    public void setNodeY(Double nodeY) {
        this.nodeY = nodeY;
    }

    public Double getNodeY() {
        return nodeY;
    }

    public void setNodeZ(Double nodeZ) {
        this.nodeZ = nodeZ;
    }

    public Double getNodeZ() {
        return nodeZ;
    }

    public void setNodeSort(Long nodeSort) {
        this.nodeSort = nodeSort;
    }

    public Long getNodeSort() {
        return nodeSort;
    }

    public void setNodeEnergy(Double nodeEnergy) {
        this.nodeEnergy = nodeEnergy;
    }

    public Double getNodeEnergy() {
        return nodeEnergy;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeRs(Double nodeRs) {
        this.nodeRs = nodeRs;
    }

    public Double getNodeRs() {
        return nodeRs;
    }

    public void setSensorsId(Long sensorsId) {
        this.sensorsId = sensorsId;
    }

    public Long getSensorsId() {
        return sensorsId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("nodeId", getNodeId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("nodeX", getNodeX())
                .append("nodeY", getNodeY())
                .append("nodeZ", getNodeZ())
                .append("nodeSort", getNodeSort())
                .append("nodeEnergy", getNodeEnergy())
                .append("dataId", getDataId())
                .append("nodeType", getNodeType())
                .append("nodeRs", getNodeRs())
                .append("sensorsId", getSensorsId())
                .toString();
    }
}
