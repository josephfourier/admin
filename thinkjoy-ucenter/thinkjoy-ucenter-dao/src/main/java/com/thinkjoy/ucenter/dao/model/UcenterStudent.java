package com.thinkjoy.ucenter.dao.model;

import java.io.Serializable;
import java.util.Date;

public class UcenterStudent implements Serializable {
    /**
     * 学生ID
     *
     * @mbg.generated
     */
    private Integer studentId;

    /**
     * 学生姓名
     *
     * @mbg.generated
     */
    private String studentName;

    /**
     * 入学日期
     *
     * @mbg.generated
     */
    private Date enterTime;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private String sex;

    /**
     * 出生日期
     *
     * @mbg.generated
     */
    private Date birthday;

    /**
     * 院系Code
     *
     * @mbg.generated
     */
    private String facultyCode;

    /**
     * 院系名称
     *
     * @mbg.generated
     */
    private String facultyName;

    /**
     * 专业Code
     *
     * @mbg.generated
     */
    private String specialtyCode;

    /**
     * 专业名称
     *
     * @mbg.generated
     */
    private String specialtyName;

    /**
     * 班级ID
     *
     * @mbg.generated
     */
    private Integer classId;

    /**
     * 班级名称
     *
     * @mbg.generated
     */
    private String className;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 入学年份
     *
     * @mbg.generated
     */
    private Integer enterYear;

    /**
     * 学籍号
     *
     * @mbg.generated
     */
    private String studentNo;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String mail;

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
     * 国籍
     *
     * @mbg.generated
     */
    private String nationality;

    /**
     * 民族
     *
     * @mbg.generated
     */
    private String nation;

    /**
     * 籍贯
     *
     * @mbg.generated
     */
    private String origin;

    /**
     * 生源地
     *
     * @mbg.generated
     */
    private String fromplace;

    /**
     * 准考证号
     *
     * @mbg.generated
     */
    private String examnum;

    /**
     * 政治面貌（团员，党员）
     *
     * @mbg.generated
     */
    private String politics;

    /**
     * 是否为贫困生
     *
     * @mbg.generated
     */
    private Boolean isPoor;

    /**
     * 家庭地址
     *
     * @mbg.generated
     */
    private String address;

    /**
     * 邮政编码
     *
     * @mbg.generated
     */
    private String postalCode;

    /**
     * 家庭电话
     *
     * @mbg.generated
     */
    private String familyPhone;

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
     * 学校编码,学校数据隔离
     *
     * @mbg.generated
     */
    private String schoolCode;

    /**
     * 户口所在地
     *
     * @mbg.generated
     */
    private String domicile;

    /**
     * 户籍性质
     * (城市、农村、县镇非农)
     *
     * @mbg.generated
     */
    private String domicileType;

    /**
     * 是否住校
     *
     * @mbg.generated
     */
    private Boolean isLiveschool;

    /**
     * 毕业学校
     *
     * @mbg.generated
     */
    private String gradSchool;

    /**
     * 毕业类别(小学，初中，高中)
     *
     * @mbg.generated
     */
    private String studentType;

    /**
     * 毕业时间
     *
     * @mbg.generated
     */
    private Date gradTime;

    /**
     * 毕业班主任姓名
     *
     * @mbg.generated
     */
    private String gradHeadteacher;

    private static final long serialVersionUID = 1L;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(Integer enterYear) {
        this.enterYear = enterYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFromplace() {
        return fromplace;
    }

    public void setFromplace(String fromplace) {
        this.fromplace = fromplace;
    }

    public String getExamnum() {
        return examnum;
    }

    public void setExamnum(String examnum) {
        this.examnum = examnum;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public Boolean getIsPoor() {
        return isPoor;
    }

    public void setIsPoor(Boolean isPoor) {
        this.isPoor = isPoor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
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

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getDomicileType() {
        return domicileType;
    }

    public void setDomicileType(String domicileType) {
        this.domicileType = domicileType;
    }

    public Boolean getIsLiveschool() {
        return isLiveschool;
    }

    public void setIsLiveschool(Boolean isLiveschool) {
        this.isLiveschool = isLiveschool;
    }

    public String getGradSchool() {
        return gradSchool;
    }

    public void setGradSchool(String gradSchool) {
        this.gradSchool = gradSchool;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public Date getGradTime() {
        return gradTime;
    }

    public void setGradTime(Date gradTime) {
        this.gradTime = gradTime;
    }

    public String getGradHeadteacher() {
        return gradHeadteacher;
    }

    public void setGradHeadteacher(String gradHeadteacher) {
        this.gradHeadteacher = gradHeadteacher;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", enterTime=").append(enterTime);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", facultyCode=").append(facultyCode);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", specialtyCode=").append(specialtyCode);
        sb.append(", specialtyName=").append(specialtyName);
        sb.append(", classId=").append(classId);
        sb.append(", className=").append(className);
        sb.append(", userId=").append(userId);
        sb.append(", enterYear=").append(enterYear);
        sb.append(", studentNo=").append(studentNo);
        sb.append(", mail=").append(mail);
        sb.append(", studentCode=").append(studentCode);
        sb.append(", examineeNumber=").append(examineeNumber);
        sb.append(", phone=").append(phone);
        sb.append(", idcard=").append(idcard);
        sb.append(", nationality=").append(nationality);
        sb.append(", nation=").append(nation);
        sb.append(", origin=").append(origin);
        sb.append(", fromplace=").append(fromplace);
        sb.append(", examnum=").append(examnum);
        sb.append(", politics=").append(politics);
        sb.append(", isPoor=").append(isPoor);
        sb.append(", address=").append(address);
        sb.append(", postalCode=").append(postalCode);
        sb.append(", familyPhone=").append(familyPhone);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", year=").append(year);
        sb.append(", schoolCode=").append(schoolCode);
        sb.append(", domicile=").append(domicile);
        sb.append(", domicileType=").append(domicileType);
        sb.append(", isLiveschool=").append(isLiveschool);
        sb.append(", gradSchool=").append(gradSchool);
        sb.append(", studentType=").append(studentType);
        sb.append(", gradTime=").append(gradTime);
        sb.append(", gradHeadteacher=").append(gradHeadteacher);
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
        UcenterStudent other = (UcenterStudent) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getEnterTime() == null ? other.getEnterTime() == null : this.getEnterTime().equals(other.getEnterTime()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getFacultyCode() == null ? other.getFacultyCode() == null : this.getFacultyCode().equals(other.getFacultyCode()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getSpecialtyCode() == null ? other.getSpecialtyCode() == null : this.getSpecialtyCode().equals(other.getSpecialtyCode()))
            && (this.getSpecialtyName() == null ? other.getSpecialtyName() == null : this.getSpecialtyName().equals(other.getSpecialtyName()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getEnterYear() == null ? other.getEnterYear() == null : this.getEnterYear().equals(other.getEnterYear()))
            && (this.getStudentNo() == null ? other.getStudentNo() == null : this.getStudentNo().equals(other.getStudentNo()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getStudentCode() == null ? other.getStudentCode() == null : this.getStudentCode().equals(other.getStudentCode()))
            && (this.getExamineeNumber() == null ? other.getExamineeNumber() == null : this.getExamineeNumber().equals(other.getExamineeNumber()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getNationality() == null ? other.getNationality() == null : this.getNationality().equals(other.getNationality()))
            && (this.getNation() == null ? other.getNation() == null : this.getNation().equals(other.getNation()))
            && (this.getOrigin() == null ? other.getOrigin() == null : this.getOrigin().equals(other.getOrigin()))
            && (this.getFromplace() == null ? other.getFromplace() == null : this.getFromplace().equals(other.getFromplace()))
            && (this.getExamnum() == null ? other.getExamnum() == null : this.getExamnum().equals(other.getExamnum()))
            && (this.getPolitics() == null ? other.getPolitics() == null : this.getPolitics().equals(other.getPolitics()))
            && (this.getIsPoor() == null ? other.getIsPoor() == null : this.getIsPoor().equals(other.getIsPoor()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getPostalCode() == null ? other.getPostalCode() == null : this.getPostalCode().equals(other.getPostalCode()))
            && (this.getFamilyPhone() == null ? other.getFamilyPhone() == null : this.getFamilyPhone().equals(other.getFamilyPhone()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()))
            && (this.getDomicile() == null ? other.getDomicile() == null : this.getDomicile().equals(other.getDomicile()))
            && (this.getDomicileType() == null ? other.getDomicileType() == null : this.getDomicileType().equals(other.getDomicileType()))
            && (this.getIsLiveschool() == null ? other.getIsLiveschool() == null : this.getIsLiveschool().equals(other.getIsLiveschool()))
            && (this.getGradSchool() == null ? other.getGradSchool() == null : this.getGradSchool().equals(other.getGradSchool()))
            && (this.getStudentType() == null ? other.getStudentType() == null : this.getStudentType().equals(other.getStudentType()))
            && (this.getGradTime() == null ? other.getGradTime() == null : this.getGradTime().equals(other.getGradTime()))
            && (this.getGradHeadteacher() == null ? other.getGradHeadteacher() == null : this.getGradHeadteacher().equals(other.getGradHeadteacher()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getEnterTime() == null) ? 0 : getEnterTime().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getFacultyCode() == null) ? 0 : getFacultyCode().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getSpecialtyCode() == null) ? 0 : getSpecialtyCode().hashCode());
        result = prime * result + ((getSpecialtyName() == null) ? 0 : getSpecialtyName().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getEnterYear() == null) ? 0 : getEnterYear().hashCode());
        result = prime * result + ((getStudentNo() == null) ? 0 : getStudentNo().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getStudentCode() == null) ? 0 : getStudentCode().hashCode());
        result = prime * result + ((getExamineeNumber() == null) ? 0 : getExamineeNumber().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getNationality() == null) ? 0 : getNationality().hashCode());
        result = prime * result + ((getNation() == null) ? 0 : getNation().hashCode());
        result = prime * result + ((getOrigin() == null) ? 0 : getOrigin().hashCode());
        result = prime * result + ((getFromplace() == null) ? 0 : getFromplace().hashCode());
        result = prime * result + ((getExamnum() == null) ? 0 : getExamnum().hashCode());
        result = prime * result + ((getPolitics() == null) ? 0 : getPolitics().hashCode());
        result = prime * result + ((getIsPoor() == null) ? 0 : getIsPoor().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPostalCode() == null) ? 0 : getPostalCode().hashCode());
        result = prime * result + ((getFamilyPhone() == null) ? 0 : getFamilyPhone().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        result = prime * result + ((getDomicile() == null) ? 0 : getDomicile().hashCode());
        result = prime * result + ((getDomicileType() == null) ? 0 : getDomicileType().hashCode());
        result = prime * result + ((getIsLiveschool() == null) ? 0 : getIsLiveschool().hashCode());
        result = prime * result + ((getGradSchool() == null) ? 0 : getGradSchool().hashCode());
        result = prime * result + ((getStudentType() == null) ? 0 : getStudentType().hashCode());
        result = prime * result + ((getGradTime() == null) ? 0 : getGradTime().hashCode());
        result = prime * result + ((getGradHeadteacher() == null) ? 0 : getGradHeadteacher().hashCode());
        return result;
    }
}