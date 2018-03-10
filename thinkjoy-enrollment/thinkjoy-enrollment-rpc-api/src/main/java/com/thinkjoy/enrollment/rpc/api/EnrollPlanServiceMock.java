package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollPlanMapper;
import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;

/**
* 降级实现EnrollPlanService接口
* Created by user on 2017/11/10.
*/
public class EnrollPlanServiceMock extends BaseServiceMock<EnrollPlanMapper, EnrollPlan, EnrollPlanExample> implements EnrollPlanService {

	@Override
	public Integer selectcountMap(Integer batchId, Integer year, String schoolCode) {
		return null;
	}
}
