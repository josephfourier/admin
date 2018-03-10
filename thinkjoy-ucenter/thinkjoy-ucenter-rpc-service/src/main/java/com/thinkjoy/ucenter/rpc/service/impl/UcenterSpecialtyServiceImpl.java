package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterSpecialtyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialtyExample;
import com.thinkjoy.ucenter.rpc.api.UcenterSpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
* UcenterSpecialtyService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterSpecialtyServiceImpl extends BaseServiceImpl<UcenterSpecialtyMapper, UcenterSpecialty, UcenterSpecialtyExample> implements UcenterSpecialtyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterSpecialtyServiceImpl.class);

    @Autowired
    UcenterSpecialtyMapper ucenterSpecialtyMapper;


	@Override
	public UcenterSpecialty getspecialtybeanExt(String specialtyCode, String schoolCode) {
		Map map=new HashMap<String,Object>();
		map.put("specialtyCode", specialtyCode);
		map.put("schoolCode", schoolCode);
		UcenterSpecialty specialtybean=ucenterSpecialtyMapper.selectspecialtybeanMap(map);
		return specialtybean;
	}

	@Override
	public UcenterSpecialty getspecialtyNobeanExt(String specialtyNo, String schoolCode) {
		Map map=new HashMap<String,Object>();
		map.put("specialtyNo", specialtyNo);
		map.put("schoolCode", schoolCode);
		UcenterSpecialty specialtyNobean=ucenterSpecialtyMapper.selectspecialtyNobeanMap(map);
		return specialtyNobean;
	}



}