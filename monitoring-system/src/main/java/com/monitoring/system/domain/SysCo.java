package com.monitoring.system.domain;

import com.monitoring.common.annotation.Excel;
import com.monitoring.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 覆盖优化记录对象 sys_co
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public class SysCo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 优化记录
     */
    private Long coId;

    /**
     * 初始覆盖率
     */
    @Excel(name = "初始覆盖率")
    private Double origin;

    /**
     * 优化后覆盖率
     */
    @Excel(name = "优化后覆盖率")
    private Double afterCoverage;

    /**
     * 初始能量
     */
    @Excel(name = "初始能量")
    private Double energy;

    /**
     * 剩余能量
     */
    @Excel(name = "剩余能量")
    private Double afterEnergy;

    /**
     * 节点状态
     */
    @Excel(name = "节点状态")
    private String status;

    /**
     * 实验数据ID
     */
    @Excel(name = "实验数据ID")
    private Long dataId;

    /**
     * 覆盖率曲线数据
     */
    @Excel(name = "覆盖率曲线数据")
    private String curveCoverage;

    /**
     * 能量消耗曲线数据
     */
    @Excel(name = "能量消耗曲线数据")
    private String curveEnergy;

    /**
     * 优化时长
     */
    @Excel(name = "优化时长")
    private String runTime;

    public void setCoId(Long coId) {
        this.coId = coId;
    }

    public Long getCoId() {
        return coId;
    }

    public void setOrigin(Double origin) {
        this.origin = origin;
    }

    public Double getOrigin() {
        return origin;
    }

    public void setAfterCoverage(Double afterCoverage) {
        this.afterCoverage = afterCoverage;
    }

    public Double getAfterCoverage() {
        return afterCoverage;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setAfterEnergy(Double afterEnergy) {
        this.afterEnergy = afterEnergy;
    }

    public Double getAfterEnergy() {
        return afterEnergy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setCurveCoverage(String curveCoverage) {
        this.curveCoverage = curveCoverage;
    }

    public String getCurveCoverage() {
        return curveCoverage;
    }

    public void setCurveEnergy(String curveEnergy) {
        this.curveEnergy = curveEnergy;
    }

    public String getCurveEnergy() {
        return curveEnergy;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getRunTime() {
        return runTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("coId", getCoId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("origin", getOrigin())
                .append("afterCoverage", getAfterCoverage())
                .append("energy", getEnergy())
                .append("afterEnergy", getAfterEnergy())
                .append("status", getStatus())
                .append("dataId", getDataId())
                .append("curveCoverage", getCurveCoverage())
                .append("curveEnergy", getCurveEnergy())
                .append("runTime", getRunTime())
                .toString();
    }
}
