package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterFaculty;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;

/**
* UcenterFacultyService接口
* Created by user on 2017/10/13.
*/
public interface UcenterFacultyService extends BaseService<UcenterFaculty, UcenterFacultyExample> {
	String getNextCodeByCode(String facultyCode,String schoolCode);
	UcenterFaculty getfacultybean(String facultyCode,String schoolCode,Integer year);
	int insertSelective(UcenterFaculty ucenterFaculty, UcenterDepartment ucenterDepartment);
	int updateByPrimaryKeySelective(UcenterFaculty ucenterFaculty, UcenterDepartmentBus departmentBus,UcenterDepartment ucenterDepartment);
	int deletePrimaryKeys(Integer facultyId,UcenterDepartmentBus departmentBus);
}