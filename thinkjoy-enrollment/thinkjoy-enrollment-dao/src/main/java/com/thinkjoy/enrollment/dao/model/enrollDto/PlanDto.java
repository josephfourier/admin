package com.thinkjoy.enrollment.dao.model.enrollDto;

import java.io.Serializable;

/**
 * Created by wangcheng on 17/11/16.
 */
public class PlanDto implements Serializable {
    private String specialtyCode;

    private Integer planNum;

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }
}
