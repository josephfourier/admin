package com.thinkjoy.enrollment.dao.model.enrollDto;

import java.io.Serializable;

/**
 * Created by gengsongbo on 2017/11/22.
 */
public class EnrollStudentDto implements Serializable {

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
	 * 招生年度
	 *
	 * @mbg.generated
	 */
	private Integer batchYear;

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
	 * 生源地
	 *
	 * @mbg.generated
	 */
	private String fromplace;

	/**
	 * 城市名称
	 *
	 * @mbg.generated
	 */
	private String cityName;

	/**
	 * 学校编码,学校数据隔离
	 *
	 * @mbg.generated

	 */
	private String schoolCode;

	/**
	 * 录取状态（0-院校在审阅，1-未录取，2-预录取，3-已录取，4-已录取被删除)
	 *
	 * @mbg.generated
	 */
	private String enrollStatus;

	/**
	 *  状态：    0.锁定1.正常(默认)
	 *
	 * @mbg.generated
	 */
	private String status;

	/**
	 * 百分比
	 */
	private String num;
	/**
	 * 分组统计数
	 */
	private Integer fnum;

	/**
	 * 分组统计总数
	 */
	private Integer znum;

	/**
	 * 统计人数数
	 */
	private Integer rnum;

	/**
	 * 批次招生计划人数
	 */
	private Integer plannum;

	/**
	 * 性别
	 *
	 * @mbg.generated
	 */
	private String sex;

	/**
	 * 报名男生人数
	 */
	private Integer bmnum;

	/**
	 * 报名女生人数
	 */
	private Integer bwnum;
	/**
	 * 报名总人数
	 */
	private Integer bzrnum;

	/**
	 * 实际录取男生人数
	 */
	private Integer lmnum;

	/**
	 * 实际录取女生人数
	 */
	private Integer lwnum;

	/**
	 * 实际录取总人数
	 */
	private Integer lzrnum;

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

	public Integer getBatchYear() {
		return batchYear;
	}

	public void setBatchYear(Integer batchYear) {
		this.batchYear = batchYear;
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

	public String getFromplace() {
		return fromplace;
	}

	public void setFromplace(String fromplace) {
		this.fromplace = fromplace;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getEnrollStatus() {
		return enrollStatus;
	}

	public void setEnrollStatus(String enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Integer getFnum() {
		return fnum;
	}

	public void setFnum(Integer fnum) {
		this.fnum = fnum;
	}

	public Integer getZnum() {
		return znum;
	}

	public void setZnum(Integer znum) {
		this.znum = znum;
	}

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

	public Integer getPlannum() {
		return plannum;
	}

	public void setPlannum(Integer plannum) {
		this.plannum = plannum;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getBmnum() {
		return bmnum;
	}

	public void setBmnum(Integer bmnum) {
		this.bmnum = bmnum;
	}

	public Integer getBwnum() {
		return bwnum;
	}

	public void setBwnum(Integer bwnum) {
		this.bwnum = bwnum;
	}

	public Integer getBzrnum() {
		return bzrnum;
	}

	public void setBzrnum(Integer bzrnum) {
		this.bzrnum = bzrnum;
	}

	public Integer getLmnum() {
		return lmnum;
	}

	public void setLmnum(Integer lmnum) {
		this.lmnum = lmnum;
	}

	public Integer getLwnum() {
		return lwnum;
	}

	public void setLwnum(Integer lwnum) {
		this.lwnum = lwnum;
	}

	public Integer getLzrnum() {
		return lzrnum;
	}

	public void setLzrnum(Integer lzrnum) {
		this.lzrnum = lzrnum;
	}
}
