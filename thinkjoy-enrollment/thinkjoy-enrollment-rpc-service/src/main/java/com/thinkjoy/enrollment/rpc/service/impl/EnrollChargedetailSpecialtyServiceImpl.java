package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargedetailSpecialtyMapper;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialty;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialtyExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.ChargedetailSpeciaaltyDto;
import com.thinkjoy.enrollment.rpc.api.EnrollChargedetailSpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
* EnrollChargedetailSpecialtyService实现
* Created by user on 2017/11/10.
*/
@Service
@Transactional
@BaseService
public class EnrollChargedetailSpecialtyServiceImpl extends BaseServiceImpl<EnrollChargedetailSpecialtyMapper, EnrollChargedetailSpecialty, EnrollChargedetailSpecialtyExample> implements EnrollChargedetailSpecialtyService
{

    private static Logger _log = LoggerFactory.getLogger(EnrollChargedetailSpecialtyServiceImpl.class);

    @Autowired
    EnrollChargedetailSpecialtyMapper enrollChargedetailSpecialtyMapper;

	@Override
	public ChargedetailSpeciaaltyDto selectchargespecialtyBean(String schoolCode, int year, int itemId, String specialtyCode,int detailId) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("year", year);
		map.put("itemId", itemId);
		map.put("specialtyCode", specialtyCode);
		map.put("detailId", detailId);
		ChargedetailSpeciaaltyDto chargespecialtyBean=enrollChargedetailSpecialtyMapper.selectchargespecialtyBean(map);

		return chargespecialtyBean;
	}

	@Override
	public ChargedetailSpeciaaltyDto selectchargeBean(String schoolCode, int year, int itemId, String specialtyCode) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("year", year);
		map.put("itemId", itemId);
		map.put("specialtyCode", specialtyCode);
		ChargedetailSpeciaaltyDto chargeBean=enrollChargedetailSpecialtyMapper.selectchargeBean(map);
		return chargeBean;
	}
}