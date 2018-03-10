package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;

/**
* EnrollPlanService接口
* Created by user on 2017/11/10.
*/
public interface EnrollPlanService extends BaseService<EnrollPlan, EnrollPlanExample> {
	Integer selectcountMap(Integer batchId,Integer year,String schoolCode);
}