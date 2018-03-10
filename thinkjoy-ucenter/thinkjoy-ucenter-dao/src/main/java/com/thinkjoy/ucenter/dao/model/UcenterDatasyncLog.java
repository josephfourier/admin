package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.Date;

public class UcenterDatasyncLog implements Serializable {
    /**
     * 同步数据id
     *
     * @mbg.generated
     */
    private Integer datasyncId;

    /**
     * 同步系统：取自定义的系统名称
     *
     * @mbg.generated
     */
    private String datasyncSystem;

    /**
     * 同步时间:取数据中的时间
     *
     * @mbg.generated
     */
    private Date datasyncTime;

    /**
     * 同步数据内容:json格式数据串
     *
     * @mbg.generated
     */
    private String data;

    /**
     * 学校code
     *
     * @mbg.generated
     */
    private String schoolCode;

    /**
     * 学校名称
     *
     * @mbg.generated
     */
    private String schoolName;

    /**
     * 同步状态：    0.失败
                 1.成功默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 创建用户：对应同步系统中的操作用户
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * 创建表
     *
     * @mbg.generated
     */
    private String tableName;

    /**
     * 日志信息
     *
     * @mbg.generated
     */
    private String dataLog;

    private static final long serialVersionUID = 1L;

    public Integer getDatasyncId() {
        return datasyncId;
    }

    public void setDatasyncId(Integer datasyncId) {
        this.datasyncId = datasyncId;
    }

    public String getDatasyncSystem() {
        return datasyncSystem;
    }

    public void setDatasyncSystem(String datasyncSystem) {
        this.datasyncSystem = datasyncSystem;
    }

    public Date getDatasyncTime() {
        return datasyncTime;
    }

    public void setDatasyncTime(Date datasyncTime) {
        this.datasyncTime = datasyncTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataLog() {
        return dataLog;
    }

    public void setDataLog(String dataLog) {
        this.dataLog = dataLog;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", datasyncId=").append(datasyncId);
        sb.append(", datasyncSystem=").append(datasyncSystem);
        sb.append(", datasyncTime=").append(datasyncTime);
        sb.append(", data=").append(data);
        sb.append(", schoolCode=").append(schoolCode);
        sb.append(", schoolName=").append(schoolName);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", tableName=").append(tableName);
        sb.append(", dataLog=").append(dataLog);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UcenterDatasyncLog other = (UcenterDatasyncLog) that;
        return (this.getDatasyncId() == null ? other.getDatasyncId() == null : this.getDatasyncId().equals(other.getDatasyncId()))
            && (this.getDatasyncSystem() == null ? other.getDatasyncSystem() == null : this.getDatasyncSystem().equals(other.getDatasyncSystem()))
            && (this.getDatasyncTime() == null ? other.getDatasyncTime() == null : this.getDatasyncTime().equals(other.getDatasyncTime()))
            && (this.getData() == null ? other.getData() == null : this.getData().equals(other.getData()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()))
            && (this.getSchoolName() == null ? other.getSchoolName() == null : this.getSchoolName().equals(other.getSchoolName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getDataLog() == null ? other.getDataLog() == null : this.getDataLog().equals(other.getDataLog()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDatasyncId() == null) ? 0 : getDatasyncId().hashCode());
        result = prime * result + ((getDatasyncSystem() == null) ? 0 : getDatasyncSystem().hashCode());
        result = prime * result + ((getDatasyncTime() == null) ? 0 : getDatasyncTime().hashCode());
        result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        result = prime * result + ((getSchoolName() == null) ? 0 : getSchoolName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getDataLog() == null) ? 0 : getDataLog().hashCode());
        return result;
    }
}