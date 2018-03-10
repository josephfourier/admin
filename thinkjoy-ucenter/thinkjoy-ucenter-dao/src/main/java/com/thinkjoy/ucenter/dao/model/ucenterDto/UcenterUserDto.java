package com.thinkjoy.ucenter.dao.model.ucenterDto;

import java.io.Serializable;

public class UcenterUserDto implements Serializable {
    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 用户逻辑主键uuid
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * 账号
     *
     * @mbg.generated
     */
    private String username;

    /**
     * 姓名
     *
     * @mbg.generated
     */
    private String fullname;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 盐
     *
     * @mbg.generated
     */
    private String salt;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 学号
     *
     * @mbg.generated
     */
    private String studentCode;

    /**
     * 考生号
     *
     * @mbg.generated
     */
    private String examineeNumber;

    /**
     * 手机号码
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 身份证号
     *
     * @mbg.generated
     */
    private String idcard;

    /**
     * 昵称
     *
     * @mbg.generated
     */
    private String nickname;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private String sex;

    /**
     * 头像
     *
     * @mbg.generated
     */
    private String avatar;

    /**
     * 创建ip
     *
     * @mbg.generated
     */
    private String createIp;

    /**
     * 最后一次登录时间
     *
     * @mbg.generated
     */
    private Long lastLoginTime;

    /**
     * 最后一次登录ip
     *
     * @mbg.generated
     */
    private String lastLoginIp;

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
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     *  状态：    0.锁定
                 1.正常(默认)
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 年份
     *
     * @mbg.generated
     */
    private Integer year;

    /**
     * 学校编码
     *
     * @mbg.generated
     */
    private String schoolCode;

    /**
     * 账号身份类型 1:学生,2:家长,3:老师,4:教育机构
     *
     * @mbg.generated
     */

    private String usertype;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getExamineeNumber() {
        return examineeNumber;
    }

    public void setExamineeNumber(String examineeNumber) {
        this.examineeNumber = examineeNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}