package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.enrollment.dao.mapper.EnrollStudentMapper;
import com.thinkjoy.enrollment.dao.model.EnrollFamily;
import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLog;
import com.thinkjoy.enrollment.dao.model.EnrollStudent;
import com.thinkjoy.enrollment.dao.model.EnrollStudentExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.EnrollStudentDto;

import java.util.List;
import java.util.Map;

/**
* 降级实现EnrollStudentService接口
* Created by user on 2017/11/2.
*/
public class EnrollStudentServiceMock extends BaseServiceMock<EnrollStudentMapper, EnrollStudent, EnrollStudentExample> implements EnrollStudentService

{
	@Override
	public int updateByPrimaryKeySelective(EnrollStudent enrollStudent, EnrollSpecialtychangeLog spechangelog) {
		return 0;
	}

	@Override
	public int luquAndcreateNotice(EnrollStudent enrollStudent) {
		return 0;
	}

	@Override
	public int yluquAndcreateNotice(EnrollStudent enrollStudent) {
		return 0;
	}

	@Override
	public int insertSelective(EnrollStudent enrollStudent, EnrollFamily enrollFamily) {
		return 0;
	}

	@Override
	public List<EnrollStudentDto> selectfacultyMap(String schoolCode, String batchYear,String enrollStatus) {
		return null;
	}

	@Override
	public List<EnrollStudentDto> selectspecialtyMap(String schoolCode, String batchYear, String facultyCode,String enrollStatus) {
		return null;
	}

	@Override
	public List<EnrollStudentDto> selectteacherMap(String schoolCode, String batchYear, String batchId,String enrollStatus) {
		return null;
	}

	@Override
	public List<EnrollStudentDto> selectfromplaceMap(String schoolCode, String batchYear,String enrollStatus) {
		return null;
	}

	@Override
	public List<EnrollStudentDto> selectMbatchMap(String schoolCode, String batchYear, String sex) {
		return null;
	}

	@Override
	public EnrollStudentDto selectWbatchMap(String schoolCode, String batchYear, Integer batchId, String sex, String enrollStatus) {
		return null;
	}

	@Override
	public int deleteStudent(String ids) {
		return 0;
	}

	@Override
	public Map<String, String> importEnrollStudent(EnrollStudent enrollStudent, Map<String, String> errorMap, Table tableValid, int rowNum, long errorNum) {
		return null;
	}
}
