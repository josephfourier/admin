package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollPlanMapper;
import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;
import com.thinkjoy.enrollment.rpc.api.EnrollPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
* EnrollPlanService实现
* Created by user on 2017/11/10.
*/
@Service
@Transactional
@BaseService
public class EnrollPlanServiceImpl extends BaseServiceImpl<EnrollPlanMapper, EnrollPlan, EnrollPlanExample> implements EnrollPlanService {

    private static Logger _log = LoggerFactory.getLogger(EnrollPlanServiceImpl.class);

    @Autowired
    EnrollPlanMapper enrollPlanMapper;

	@Override
	public Integer selectcountMap(Integer batchId, Integer year, String schoolCode) {
		Map map=new HashMap<String,Object>();
		map.put("batchId", batchId);
		map.put("year", year);
		map.put("schoolCode", schoolCode);
		Integer planbean=enrollPlanMapper.selectcountMap(map);
		return planbean;
	}
}