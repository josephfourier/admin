package com.thinkjoy.ucenter.dao.model.ucenterDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangcheng on 18/3/7.
 */
public class DepartmentInfo implements Serializable{

    private Integer departmentId;
    private String departmentName;
    private String postName;
    private String teacherLevel;
    private String teacherLevelName;
    private List<BusInfo> busInfoes;

    public String getTeacherLevelName() {
        return teacherLevelName;
    }

    public void setTeacherLevelName(String teacherLevelName) {
        this.teacherLevelName = teacherLevelName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(String teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public List<BusInfo> getBusInfoes() {
        return busInfoes;
    }

    public void setBusInfoes(List<BusInfo> busInfoes) {
        this.busInfoes = busInfoes;
    }
}
