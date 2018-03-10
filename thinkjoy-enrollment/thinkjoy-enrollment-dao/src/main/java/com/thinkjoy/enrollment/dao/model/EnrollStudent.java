package com.thinkjoy.enrollment.dao.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class EnrollStudent implements Serializable {
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
     * 性别
     *
     * @mbg.generated
     */
    private String sex;

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
     * 出生日期
     *
     * @mbg.generated
     */
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 政治面貌（团员，党员）
     *
     * @mbg.generated
     */
    private String politics;

    /**
     * 身份证号
     *
     * @mbg.generated
     */
    private String idcard;

    /**
     * 户口所在地
     *
     * @mbg.generated
     */
    private String domicile;

    /**
     * 城市、农村、县镇非农
     *
     * @mbg.generated
     */
    private String domicileType;

    /**
     * 生源地
     *
     * @mbg.generated
     */
    private String fromplace;

    /**
     * 是否为贫困生
     *
     * @mbg.generated
     */
    private Boolean isPoor;

    /**
     * 是否住校
     *
     * @mbg.generated
     */
    private Boolean isLiveschool;

    /**
     * 手机号码
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String mail;

    /**
     * 家庭电话(固定电话)
     *
     * @mbg.generated
     */
    private String familyPhone;

    /**
     * 家庭地址(详细通讯地址)
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
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date gradTime;

    /**
     * 招生批次ID
     *
     * @mbg.generated
     */
    private Integer batchId;

    /**
     * 招生批次名称
     *
     * @mbg.generated
     */
    private String batchName;

    /**
     * 人员id
     *
     * @mbg.generated
     */
    private Integer enrollteacherId;

    /**
     * 招生人员名称
     *
     * @mbg.generated
     */
    private String teacherName;

    /**
     * 毕业班主任姓名
     *
     * @mbg.generated
     */
    private String gradHeadteacher;

    /**
     * 准考证号
     *
     * @mbg.generated
     */
    private String examnum;

    /**
     * 考生号
     *
     * @mbg.generated
     */
    private String examineeNumber;

    /**
     * 录取专业编码，对应 校内专业编码,每个学校专业不同，学校会创建自己的专业编码
     *
     * @mbg.generated
     */
    private String specialtyCode;

    /**
     * 录取专业名称
     *
     * @mbg.generated
     */
    private String specialtyName;

    /**
     * 录取专业培养方向
     *
     * @mbg.generated
     */
    private String trdrName;

    /**
     * 录取专业学制,对应学制（五年，三年制专科）
     *
     * @mbg.generated
     */
    private String schoolSystem;

    /**
     * 录取院系编码
     *
     * @mbg.generated
     */
    private String facultyCode;

    /**
     * 录取院系名称
     *
     * @mbg.generated
     */
    private String facultyName;

    /**
     * 第一志愿编码,对应校内专业编码
     *
     * @mbg.generated
     */
    private String firstVolcode;

    /**
     * 第一志愿（专业代码+专业名称）
     *
     * @mbg.generated
     */
    private String firstVol;

    /**
     * 第一志愿培养方向
     *
     * @mbg.generated
     */
    private String firstTrdrname;

    /**
     * 第一志愿学制（五年，三年制专科）
     *
     * @mbg.generated
     */
    private String firstSchsys;

    /**
     * 第一志愿院系编码
     *
     * @mbg.generated
     */
    private String firstFacultycode;

    /**
     * 第一志愿院系名称
     *
     * @mbg.generated
     */
    private String firstFacultyname;

    /**
     * 第二志愿编码,对应校内专业编码
     *
     * @mbg.generated
     */
    private String secondVolcode;

    /**
     * 第二志愿（专业代码+专业名称）
     *
     * @mbg.generated
     */
    private String secondVol;

    /**
     * 第二志愿培养方向
     *
     * @mbg.generated
     */
    private String secondTrdrname;

    /**
     * 第二志愿学制（五年，三年制专科）
     *
     * @mbg.generated
     */
    private String secondSchsys;

    /**
     * 第二志愿院系编码
     *
     * @mbg.generated
     */
    private String secondFacultycode;

    /**
     * 第二志愿院系名称
     *
     * @mbg.generated
     */
    private String secondFacultname;

    /**
     * 录取志愿:1-第一志愿，2-第二志愿
     *
     * @mbg.generated
     */
    private String enrollVol;

    /**
     * 招生年度
     *
     * @mbg.generated
     */
    private Integer batchYear;

    /**
     * 成绩
     *
     * @mbg.generated
     */
    private Integer score;

    /**
     * 通知书存放地址
     *
     * @mbg.generated
     */
    private String noticeUrl;

    /**
     * 缴费状态(1-未缴费，2-预缴费，3-已缴费，4-待退费，5-已退费)
     *
     * @mbg.generated
     */
    private String feeStatus;

    /**
     * 录取状态（1-未录取，2-预录取，3-已录取，4-已录取被删除)
     *
     * @mbg.generated
     */
    private String enrollStatus;

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
     *  状态：    0.锁定1.正常(默认)
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
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

    public String getFromplace() {
        return fromplace;
    }

    public void setFromplace(String fromplace) {
        this.fromplace = fromplace;
    }

    public Boolean getIsPoor() {
        return isPoor;
    }

    public void setIsPoor(Boolean isPoor) {
        this.isPoor = isPoor;
    }

    public Boolean getIsLiveschool() {
        return isLiveschool;
    }

    public void setIsLiveschool(Boolean isLiveschool) {
        this.isLiveschool = isLiveschool;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
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

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getEnrollteacherId() {
        return enrollteacherId;
    }

    public void setEnrollteacherId(Integer enrollteacherId) {
        this.enrollteacherId = enrollteacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getGradHeadteacher() {
        return gradHeadteacher;
    }

    public void setGradHeadteacher(String gradHeadteacher) {
        this.gradHeadteacher = gradHeadteacher;
    }

    public String getExamnum() {
        return examnum;
    }

    public void setExamnum(String examnum) {
        this.examnum = examnum;
    }

    public String getExamineeNumber() {
        return examineeNumber;
    }

    public void setExamineeNumber(String examineeNumber) {
        this.examineeNumber = examineeNumber;
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

    public String getTrdrName() {
        return trdrName;
    }

    public void setTrdrName(String trdrName) {
        this.trdrName = trdrName;
    }

    public String getSchoolSystem() {
        return schoolSystem;
    }

    public void setSchoolSystem(String schoolSystem) {
        this.schoolSystem = schoolSystem;
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

    public String getFirstVolcode() {
        return firstVolcode;
    }

    public void setFirstVolcode(String firstVolcode) {
        this.firstVolcode = firstVolcode;
    }

    public String getFirstVol() {
        return firstVol;
    }

    public void setFirstVol(String firstVol) {
        this.firstVol = firstVol;
    }

    public String getFirstTrdrname() {
        return firstTrdrname;
    }

    public void setFirstTrdrname(String firstTrdrname) {
        this.firstTrdrname = firstTrdrname;
    }

    public String getFirstSchsys() {
        return firstSchsys;
    }

    public void setFirstSchsys(String firstSchsys) {
        this.firstSchsys = firstSchsys;
    }

    public String getFirstFacultycode() {
        return firstFacultycode;
    }

    public void setFirstFacultycode(String firstFacultycode) {
        this.firstFacultycode = firstFacultycode;
    }

    public String getFirstFacultyname() {
        return firstFacultyname;
    }

    public void setFirstFacultyname(String firstFacultyname) {
        this.firstFacultyname = firstFacultyname;
    }

    public String getSecondVolcode() {
        return secondVolcode;
    }

    public void setSecondVolcode(String secondVolcode) {
        this.secondVolcode = secondVolcode;
    }

    public String getSecondVol() {
        return secondVol;
    }

    public void setSecondVol(String secondVol) {
        this.secondVol = secondVol;
    }

    public String getSecondTrdrname() {
        return secondTrdrname;
    }

    public void setSecondTrdrname(String secondTrdrname) {
        this.secondTrdrname = secondTrdrname;
    }

    public String getSecondSchsys() {
        return secondSchsys;
    }

    public void setSecondSchsys(String secondSchsys) {
        this.secondSchsys = secondSchsys;
    }

    public String getSecondFacultycode() {
        return secondFacultycode;
    }

    public void setSecondFacultycode(String secondFacultycode) {
        this.secondFacultycode = secondFacultycode;
    }

    public String getSecondFacultname() {
        return secondFacultname;
    }

    public void setSecondFacultname(String secondFacultname) {
        this.secondFacultname = secondFacultname;
    }

    public String getEnrollVol() {
        return enrollVol;
    }

    public void setEnrollVol(String enrollVol) {
        this.enrollVol = enrollVol;
    }

    public Integer getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(Integer batchYear) {
        this.batchYear = batchYear;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(String enrollStatus) {
        this.enrollStatus = enrollStatus;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", sex=").append(sex);
        sb.append(", nation=").append(nation);
        sb.append(", origin=").append(origin);
        sb.append(", birthday=").append(birthday);
        sb.append(", politics=").append(politics);
        sb.append(", idcard=").append(idcard);
        sb.append(", domicile=").append(domicile);
        sb.append(", domicileType=").append(domicileType);
        sb.append(", fromplace=").append(fromplace);
        sb.append(", isPoor=").append(isPoor);
        sb.append(", isLiveschool=").append(isLiveschool);
        sb.append(", phone=").append(phone);
        sb.append(", mail=").append(mail);
        sb.append(", familyPhone=").append(familyPhone);
        sb.append(", address=").append(address);
        sb.append(", postalCode=").append(postalCode);
        sb.append(", gradSchool=").append(gradSchool);
        sb.append(", studentType=").append(studentType);
        sb.append(", gradTime=").append(gradTime);
        sb.append(", batchId=").append(batchId);
        sb.append(", batchName=").append(batchName);
        sb.append(", enrollteacherId=").append(enrollteacherId);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", gradHeadteacher=").append(gradHeadteacher);
        sb.append(", examnum=").append(examnum);
        sb.append(", examineeNumber=").append(examineeNumber);
        sb.append(", specialtyCode=").append(specialtyCode);
        sb.append(", specialtyName=").append(specialtyName);
        sb.append(", trdrName=").append(trdrName);
        sb.append(", schoolSystem=").append(schoolSystem);
        sb.append(", facultyCode=").append(facultyCode);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", firstVolcode=").append(firstVolcode);
        sb.append(", firstVol=").append(firstVol);
        sb.append(", firstTrdrname=").append(firstTrdrname);
        sb.append(", firstSchsys=").append(firstSchsys);
        sb.append(", firstFacultycode=").append(firstFacultycode);
        sb.append(", firstFacultyname=").append(firstFacultyname);
        sb.append(", secondVolcode=").append(secondVolcode);
        sb.append(", secondVol=").append(secondVol);
        sb.append(", secondTrdrname=").append(secondTrdrname);
        sb.append(", secondSchsys=").append(secondSchsys);
        sb.append(", secondFacultycode=").append(secondFacultycode);
        sb.append(", secondFacultname=").append(secondFacultname);
        sb.append(", enrollVol=").append(enrollVol);
        sb.append(", batchYear=").append(batchYear);
        sb.append(", score=").append(score);
        sb.append(", noticeUrl=").append(noticeUrl);
        sb.append(", feeStatus=").append(feeStatus);
        sb.append(", enrollStatus=").append(enrollStatus);
        sb.append(", creator=").append(creator);
        sb.append(", ctime=").append(ctime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", year=").append(year);
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
        EnrollStudent other = (EnrollStudent) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getNation() == null ? other.getNation() == null : this.getNation().equals(other.getNation()))
            && (this.getOrigin() == null ? other.getOrigin() == null : this.getOrigin().equals(other.getOrigin()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getPolitics() == null ? other.getPolitics() == null : this.getPolitics().equals(other.getPolitics()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getDomicile() == null ? other.getDomicile() == null : this.getDomicile().equals(other.getDomicile()))
            && (this.getDomicileType() == null ? other.getDomicileType() == null : this.getDomicileType().equals(other.getDomicileType()))
            && (this.getFromplace() == null ? other.getFromplace() == null : this.getFromplace().equals(other.getFromplace()))
            && (this.getIsPoor() == null ? other.getIsPoor() == null : this.getIsPoor().equals(other.getIsPoor()))
            && (this.getIsLiveschool() == null ? other.getIsLiveschool() == null : this.getIsLiveschool().equals(other.getIsLiveschool()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getFamilyPhone() == null ? other.getFamilyPhone() == null : this.getFamilyPhone().equals(other.getFamilyPhone()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getPostalCode() == null ? other.getPostalCode() == null : this.getPostalCode().equals(other.getPostalCode()))
            && (this.getGradSchool() == null ? other.getGradSchool() == null : this.getGradSchool().equals(other.getGradSchool()))
            && (this.getStudentType() == null ? other.getStudentType() == null : this.getStudentType().equals(other.getStudentType()))
            && (this.getGradTime() == null ? other.getGradTime() == null : this.getGradTime().equals(other.getGradTime()))
            && (this.getBatchId() == null ? other.getBatchId() == null : this.getBatchId().equals(other.getBatchId()))
            && (this.getBatchName() == null ? other.getBatchName() == null : this.getBatchName().equals(other.getBatchName()))
            && (this.getEnrollteacherId() == null ? other.getEnrollteacherId() == null : this.getEnrollteacherId().equals(other.getEnrollteacherId()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getGradHeadteacher() == null ? other.getGradHeadteacher() == null : this.getGradHeadteacher().equals(other.getGradHeadteacher()))
            && (this.getExamnum() == null ? other.getExamnum() == null : this.getExamnum().equals(other.getExamnum()))
            && (this.getExamineeNumber() == null ? other.getExamineeNumber() == null : this.getExamineeNumber().equals(other.getExamineeNumber()))
            && (this.getSpecialtyCode() == null ? other.getSpecialtyCode() == null : this.getSpecialtyCode().equals(other.getSpecialtyCode()))
            && (this.getSpecialtyName() == null ? other.getSpecialtyName() == null : this.getSpecialtyName().equals(other.getSpecialtyName()))
            && (this.getTrdrName() == null ? other.getTrdrName() == null : this.getTrdrName().equals(other.getTrdrName()))
            && (this.getSchoolSystem() == null ? other.getSchoolSystem() == null : this.getSchoolSystem().equals(other.getSchoolSystem()))
            && (this.getFacultyCode() == null ? other.getFacultyCode() == null : this.getFacultyCode().equals(other.getFacultyCode()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getFirstVolcode() == null ? other.getFirstVolcode() == null : this.getFirstVolcode().equals(other.getFirstVolcode()))
            && (this.getFirstVol() == null ? other.getFirstVol() == null : this.getFirstVol().equals(other.getFirstVol()))
            && (this.getFirstTrdrname() == null ? other.getFirstTrdrname() == null : this.getFirstTrdrname().equals(other.getFirstTrdrname()))
            && (this.getFirstSchsys() == null ? other.getFirstSchsys() == null : this.getFirstSchsys().equals(other.getFirstSchsys()))
            && (this.getFirstFacultycode() == null ? other.getFirstFacultycode() == null : this.getFirstFacultycode().equals(other.getFirstFacultycode()))
            && (this.getFirstFacultyname() == null ? other.getFirstFacultyname() == null : this.getFirstFacultyname().equals(other.getFirstFacultyname()))
            && (this.getSecondVolcode() == null ? other.getSecondVolcode() == null : this.getSecondVolcode().equals(other.getSecondVolcode()))
            && (this.getSecondVol() == null ? other.getSecondVol() == null : this.getSecondVol().equals(other.getSecondVol()))
            && (this.getSecondTrdrname() == null ? other.getSecondTrdrname() == null : this.getSecondTrdrname().equals(other.getSecondTrdrname()))
            && (this.getSecondSchsys() == null ? other.getSecondSchsys() == null : this.getSecondSchsys().equals(other.getSecondSchsys()))
            && (this.getSecondFacultycode() == null ? other.getSecondFacultycode() == null : this.getSecondFacultycode().equals(other.getSecondFacultycode()))
            && (this.getSecondFacultname() == null ? other.getSecondFacultname() == null : this.getSecondFacultname().equals(other.getSecondFacultname()))
            && (this.getEnrollVol() == null ? other.getEnrollVol() == null : this.getEnrollVol().equals(other.getEnrollVol()))
            && (this.getBatchYear() == null ? other.getBatchYear() == null : this.getBatchYear().equals(other.getBatchYear()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getNoticeUrl() == null ? other.getNoticeUrl() == null : this.getNoticeUrl().equals(other.getNoticeUrl()))
            && (this.getFeeStatus() == null ? other.getFeeStatus() == null : this.getFeeStatus().equals(other.getFeeStatus()))
            && (this.getEnrollStatus() == null ? other.getEnrollStatus() == null : this.getEnrollStatus().equals(other.getEnrollStatus()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCtime() == null ? other.getCtime() == null : this.getCtime().equals(other.getCtime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getSchoolCode() == null ? other.getSchoolCode() == null : this.getSchoolCode().equals(other.getSchoolCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getNation() == null) ? 0 : getNation().hashCode());
        result = prime * result + ((getOrigin() == null) ? 0 : getOrigin().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getPolitics() == null) ? 0 : getPolitics().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getDomicile() == null) ? 0 : getDomicile().hashCode());
        result = prime * result + ((getDomicileType() == null) ? 0 : getDomicileType().hashCode());
        result = prime * result + ((getFromplace() == null) ? 0 : getFromplace().hashCode());
        result = prime * result + ((getIsPoor() == null) ? 0 : getIsPoor().hashCode());
        result = prime * result + ((getIsLiveschool() == null) ? 0 : getIsLiveschool().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getFamilyPhone() == null) ? 0 : getFamilyPhone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPostalCode() == null) ? 0 : getPostalCode().hashCode());
        result = prime * result + ((getGradSchool() == null) ? 0 : getGradSchool().hashCode());
        result = prime * result + ((getStudentType() == null) ? 0 : getStudentType().hashCode());
        result = prime * result + ((getGradTime() == null) ? 0 : getGradTime().hashCode());
        result = prime * result + ((getBatchId() == null) ? 0 : getBatchId().hashCode());
        result = prime * result + ((getBatchName() == null) ? 0 : getBatchName().hashCode());
        result = prime * result + ((getEnrollteacherId() == null) ? 0 : getEnrollteacherId().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getGradHeadteacher() == null) ? 0 : getGradHeadteacher().hashCode());
        result = prime * result + ((getExamnum() == null) ? 0 : getExamnum().hashCode());
        result = prime * result + ((getExamineeNumber() == null) ? 0 : getExamineeNumber().hashCode());
        result = prime * result + ((getSpecialtyCode() == null) ? 0 : getSpecialtyCode().hashCode());
        result = prime * result + ((getSpecialtyName() == null) ? 0 : getSpecialtyName().hashCode());
        result = prime * result + ((getTrdrName() == null) ? 0 : getTrdrName().hashCode());
        result = prime * result + ((getSchoolSystem() == null) ? 0 : getSchoolSystem().hashCode());
        result = prime * result + ((getFacultyCode() == null) ? 0 : getFacultyCode().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getFirstVolcode() == null) ? 0 : getFirstVolcode().hashCode());
        result = prime * result + ((getFirstVol() == null) ? 0 : getFirstVol().hashCode());
        result = prime * result + ((getFirstTrdrname() == null) ? 0 : getFirstTrdrname().hashCode());
        result = prime * result + ((getFirstSchsys() == null) ? 0 : getFirstSchsys().hashCode());
        result = prime * result + ((getFirstFacultycode() == null) ? 0 : getFirstFacultycode().hashCode());
        result = prime * result + ((getFirstFacultyname() == null) ? 0 : getFirstFacultyname().hashCode());
        result = prime * result + ((getSecondVolcode() == null) ? 0 : getSecondVolcode().hashCode());
        result = prime * result + ((getSecondVol() == null) ? 0 : getSecondVol().hashCode());
        result = prime * result + ((getSecondTrdrname() == null) ? 0 : getSecondTrdrname().hashCode());
        result = prime * result + ((getSecondSchsys() == null) ? 0 : getSecondSchsys().hashCode());
        result = prime * result + ((getSecondFacultycode() == null) ? 0 : getSecondFacultycode().hashCode());
        result = prime * result + ((getSecondFacultname() == null) ? 0 : getSecondFacultname().hashCode());
        result = prime * result + ((getEnrollVol() == null) ? 0 : getEnrollVol().hashCode());
        result = prime * result + ((getBatchYear() == null) ? 0 : getBatchYear().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getNoticeUrl() == null) ? 0 : getNoticeUrl().hashCode());
        result = prime * result + ((getFeeStatus() == null) ? 0 : getFeeStatus().hashCode());
        result = prime * result + ((getEnrollStatus() == null) ? 0 : getEnrollStatus().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCtime() == null) ? 0 : getCtime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getSchoolCode() == null) ? 0 : getSchoolCode().hashCode());
        return result;
    }
}