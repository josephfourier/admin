package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialty;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialtyExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.ChargedetailSpeciaaltyDto;

/**
* EnrollChargedetailSpecialtyService接口
* Created by user on 2017/11/10.
*/
public interface EnrollChargedetailSpecialtyService extends BaseService<EnrollChargedetailSpecialty, EnrollChargedetailSpecialtyExample> {
	ChargedetailSpeciaaltyDto selectchargespecialtyBean(String schoolCode,int year,int itemId,String specialtyCode,int detailId);
	ChargedetailSpeciaaltyDto selectchargeBean(String schoolCode,int year,int itemId,String specialtyCode);
}