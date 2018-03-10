package com.thinkjoy.ucenter.dao.model.ucenterDto;

import java.io.Serializable;
import java.util.List;

public class UcenterTeacherDto implements Serializable {

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
    private String teacherName;

    private List<DepartmentInfo> departmentInfos;

    public List<DepartmentInfo> getDepartmentInfos() {
        return departmentInfos;
    }

    public void setDepartmentInfos(List<DepartmentInfo> departmentInfos) {
        this.departmentInfos = departmentInfos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}