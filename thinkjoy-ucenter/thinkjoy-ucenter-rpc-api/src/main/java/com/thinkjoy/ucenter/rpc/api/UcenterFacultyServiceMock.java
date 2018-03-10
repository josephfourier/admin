package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterFacultyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterFaculty;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;

/**
* 降级实现UcenterFacultyService接口
* Created by user on 2017/10/13.
*/
public class UcenterFacultyServiceMock extends BaseServiceMock<UcenterFacultyMapper, UcenterFaculty, UcenterFacultyExample> implements UcenterFacultyService {

	@Override
	public String getNextCodeByCode(String facultyCode,String schoolCode) {return null;}

	@Override
	public UcenterFaculty getfacultybean(String facultyCode, String schoolCode, Integer year) {return null;}

	@Override
	public int insertSelective(UcenterFaculty ucenterFaculty, UcenterDepartment ucenterDepartment) {return 0;}

	@Override
	public int updateByPrimaryKeySelective(UcenterFaculty ucenterFaculty, UcenterDepartmentBus departmentBus,UcenterDepartment ucenterDepartment) {return 0;}

	@Override
	public int deletePrimaryKeys(Integer facultyId, UcenterDepartmentBus departmentBus) {return 0;}
}
