package com.thinkjoy.enrollment.dao.model.enrollDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangcheng on 17/11/16.
 */
public class SpecialtyInfoDto implements Serializable {

    private Double price;
    private Integer planNum;
    private List<PlanDto> planDtos;

    /**
     * 校内专业编码,每个学校专业不同，学校会创建自己的专业编码
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
     * 培养方向名称
     *
     * @mbg.generated
     */
    private String trdrName;

    /**
     * 专业类别（(1)哲学;(2)经济学;(3)法学;(4)教育学;(5)文学;(6)历史学;(7)理学;(8)工学;(9)农学;(10)医学;(11)军事学;(12)管理学）
     *
     * @mbg.generated
     */
    private String specialtyType;

    /**
     * 所属院系名称
     *
     * @mbg.generated
     */
    private String facultyName;

    /**
     * 学制（五年，三年制专科）
     *
     * @mbg.generated
     */
    private String schoolSystem;

    /**
     * 专业性质
     *
     * @mbg.generated
     */
    private String specialtyNature;

    /**
     * 招生对象
     *
     * @mbg.generated
     */
    private String enrollmentTarget;


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<PlanDto> getPlanDtos() {
        return planDtos;
    }

    public void setPlanDtos(List<PlanDto> planDtos) {
        this.planDtos = planDtos;
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

    public String getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(String specialtyType) {
        this.specialtyType = specialtyType;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSchoolSystem() {
        return schoolSystem;
    }

    public void setSchoolSystem(String schoolSystem) {
        this.schoolSystem = schoolSystem;
    }

    public String getSpecialtyNature() {
        return specialtyNature;
    }

    public void setSpecialtyNature(String specialtyNature) {
        this.specialtyNature = specialtyNature;
    }

    public String getEnrollmentTarget() {
        return enrollmentTarget;
    }

    public void setEnrollmentTarget(String enrollmentTarget) {
        this.enrollmentTarget = enrollmentTarget;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }
}
