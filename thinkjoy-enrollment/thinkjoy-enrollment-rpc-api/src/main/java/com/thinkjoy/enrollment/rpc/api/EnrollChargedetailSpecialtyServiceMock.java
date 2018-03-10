package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargedetailSpecialtyMapper;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialty;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialtyExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.ChargedetailSpeciaaltyDto;

/**
* 降级实现EnrollChargedetailSpecialtyService接口
* Created by user on 2017/11/10.
*/
public class EnrollChargedetailSpecialtyServiceMock extends BaseServiceMock<EnrollChargedetailSpecialtyMapper, EnrollChargedetailSpecialty, EnrollChargedetailSpecialtyExample> implements EnrollChargedetailSpecialtyService {

	@Override
	public ChargedetailSpeciaaltyDto selectchargespecialtyBean(String schoolCode, int year, int itemId, String specialtyCode,int detailId) {
		return null;
	}

	@Override
	public ChargedetailSpeciaaltyDto selectchargeBean(String schoolCode, int year, int itemId, String specialtyCode) {
		return null;
	}
}
