package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;

public class UcenterPaySetting implements Serializable {
    /**
     * 商户id
     *
     * @mbg.generated
     */
    private Integer paysettingId;

    /**
     * 商户名称
     *
     * @mbg.generated
     */
    private String mchName;

    /**
     * 商户号
     *
     * @mbg.generated
     */
    private String mchId;

    /**
     * 商户私钥
     *
     * @mbg.generated
     */
    private String mckKey;

    /**
     * 商户backUrl
     *
     * @mbg.generated
     */
    private String mchBackurl;

    /**
     * 商户pageUrl
     *
     * @mbg.generated
     */
    private String mchPageurl;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 创建人
     *
     * @mbg.generated
     */
    private String creator;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long ctime;

    /**
     *  状态：    0.锁定
                 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 学校编码,学校数据隔离
     *
     * @mbg.generated
     */
    private String schoolCode;

    private static final long serialVersionUID = 1L;

    public Integer getPaysettingId() {
        return paysettingId;
    }

    public void setPaysettingId(Integer paysettingId) {
        this.paysettingId = paysettingId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMckKey() {
        return mckKey;
    }

    public void setMckKey(String mckKey) {
        this.mckKey = mckKey;
    }

    public String getMchBackurl() {
        return mchBackurl;
    }

    public void setMchBackurl(String mchBackurl) {
        this.mchBackurl = mchBackurl;
    }

    public String getMchPageurl() {
        return mchPageurl;
    }

    public void setMchPageurl(String mchPageurl) {
        this.mchPageurl = mchPageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paysettingId=").append(paysettingId);
        sb.append(", mchName=").append(mchName);
        sb.append(", mchId=").append(mchId);
        sb.append(", mckKey=").append(mckKey);
        sb.append(", mchBackurl=").append(mchBackurl);
        sb.append(", mchPageurl=").append(mchPageurl);
        sb.append(", description=").append(description);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", status=").append(status);
        sb.append(", schoolCode=").append(schoolCode);
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
        UcenterPaySetting other = (UcenterPaySetting) that;
        return (this.getPaysettingId() == null ? other.getPaysettingId() == null : this.getPaysettingId().equals(other.getPaysettingId()))
            && (this.getMchName() == null ? other.getMchName() == null : this.getMchName().equals(other.getMchName()))
            && (this.getMchId() == null ? other.getMchId() == null : this.getMchId().equals(other.getMchId()))
            && (this.getMckKey() == null ? other.getMckKey() == null : this.getMckKey().equals(other.getMckKey()))
            && (this.getMchBackurl() == null ? other.getMchBackurl() == null : this.getMchBackurl().equals(other.getMchBackurl()))
            && (this.getMchPageurl() == null ? other.getMchPageurl() == null : this.getMchPageurl().equals(other.getMchPageurl()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaysettingId() == null) ? 0 : getPaysettingId().hashCode());
        result = prime * result + ((getMchName() == null) ? 0 : getMchName().hashCode());
        result = prime * result + ((getMchId() == null) ? 0 : getMchId().hashCode());
        result = prime * result + ((getMckKey() == null) ? 0 : getMckKey().hashCode());
        result = prime * result + ((getMchBackurl() == null) ? 0 : getMchBackurl().hashCode());
        result = prime * result + ((getMchPageurl() == null) ? 0 : getMchPageurl().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }

    public void getStatus(String normal) {

    }
}