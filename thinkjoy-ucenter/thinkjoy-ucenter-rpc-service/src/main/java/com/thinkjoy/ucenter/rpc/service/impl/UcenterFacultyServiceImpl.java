package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.db.DataSourceEnum;
import com.thinkjoy.common.db.DynamicDataSource;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentBusMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterFacultyMapper;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterFacultyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
* UcenterFacultyService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
class UcenterFacultyServiceImpl extends BaseServiceImpl<UcenterFacultyMapper, UcenterFaculty, UcenterFacultyExample> implements UcenterFacultyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterFacultyServiceImpl.class);

    @Autowired
	UcenterFacultyMapper ucenterFacultyMapper;
	@Autowired
	UcenterDepartmentMapper ucenterDepartmentMapper;
	@Autowired
	UcenterDepartmentBusMapper ucenterDepartmentBusMapper;

	@Override
	public String getNextCodeByCode(String facultyCode,String schoolCode) {
		Map map=new HashMap<String,Object>();
		map.put("facultyCode", facultyCode);
		map.put("schoolCode", schoolCode);
		String tempagencyCode=ucenterFacultyMapper.selectMaxCodeByMap(map);
		if(null==tempagencyCode||tempagencyCode.equals("0")){
			return "1100";
		}
		return String.valueOf((Long.parseLong((tempagencyCode))/100+1)*100);
	}

	@Override
	public UcenterFaculty getfacultybean(String facultyCode, String schoolCode, Integer year) {
		Map map=new HashMap<String,Object>();
		map.put("facultyCode", facultyCode);
		map.put("schoolCode", schoolCode);
		map.put("year", year);
		UcenterFaculty facultybean=ucenterFacultyMapper.selectfacultybeanMap(map);
		return facultybean;
	}

	@Override
	public int insertSelective(UcenterFaculty ucenterFaculty, UcenterDepartment ucenterDepartment) {
		UcenterDepartmentBus ucenterDepartmentBus=new UcenterDepartmentBus();
		int scount=ucenterFacultyMapper.insertSelective(ucenterFaculty);
		int mcount=ucenterDepartmentMapper.insertSelective(ucenterDepartment);
		ucenterDepartmentBus.setBusId(ucenterFaculty.getFacultyId());
		ucenterDepartmentBus.setDepartmentId(ucenterDepartment.getDepartmentId());
		int count=ucenterDepartmentBusMapper.insertSelective(ucenterDepartmentBus);
		return count;
	}

	@Override
	public int updateByPrimaryKeySelective(UcenterFaculty ucenterFaculty,UcenterDepartmentBus departmentBus, UcenterDepartment ucenterDepartment) {
		UcenterDepartmentBusExample ucenterDepartmentBusExample=new UcenterDepartmentBusExample();
		UcenterDepartmentBusExample.Criteria criteria=ucenterDepartmentBusExample.createCriteria();
		UcenterDepartmentBus ucenterDepartmentBus=new UcenterDepartmentBus();
		int scount=ucenterFacultyMapper.updateByPrimaryKeySelective(ucenterFaculty);
		Integer departmentId=departmentBus.getDepartmentId();
		Integer facultyId=ucenterFaculty.getFacultyId();
		if (departmentId!=null) {
			int mcount=ucenterDepartmentMapper.updateByPrimaryKeySelective(ucenterDepartment);
		}
		if (facultyId!=null) {
			criteria.andBusIdEqualTo(facultyId);
			ucenterDepartmentBusMapper.deleteByExample(ucenterDepartmentBusExample);
		}
		ucenterDepartmentBus.setBusId(facultyId);
		ucenterDepartmentBus.setDepartmentId(departmentId);
		int count=this.ucenterDepartmentBusMapper.insertSelective(ucenterDepartmentBus);
		return count;
	}

	@Override
	public int deletePrimaryKeys(Integer facultyId,UcenterDepartmentBus departmentBus) {
		try {
			UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
			UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();

			UcenterDepartmentBusExample ucenterDepartmentBusExample=new UcenterDepartmentBusExample();
			UcenterDepartmentBusExample.Criteria criteria1=ucenterDepartmentBusExample.createCriteria();


			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			int count = 0;
			Integer departmentId=departmentBus.getDepartmentId();
			criteria1.andBusIdEqualTo(facultyId);
			int mcount=ucenterDepartmentBusMapper.deleteByExample(ucenterDepartmentBusExample);

			criteria.andDepartmentIdEqualTo(departmentId);
			int scount=ucenterDepartmentMapper.deleteByExample(ucenterDepartmentExample);

			Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", facultyId.getClass());

			Object result = deleteByPrimaryKey.invoke(mapper, facultyId);
			count += Integer.parseInt(String.valueOf(result));
			return count;
		} catch (Exception e) {

			e.printStackTrace();
			if (e instanceof InvocationTargetException) {
				if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {

					String res = handleException(e);
					throw new RuntimeException(res);
				}
			}
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}
}