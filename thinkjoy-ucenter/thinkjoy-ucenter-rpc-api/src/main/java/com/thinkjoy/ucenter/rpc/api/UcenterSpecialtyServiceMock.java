package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterSpecialtyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialtyExample;

/**
* 降级实现UcenterSpecialtyService接口
* Created by user on 2017/10/13.
*/
public class UcenterSpecialtyServiceMock extends BaseServiceMock<UcenterSpecialtyMapper, UcenterSpecialty, UcenterSpecialtyExample> implements UcenterSpecialtyService {


	@Override
	public UcenterSpecialty getspecialtybeanExt(String specialtyCode, String schoolCode) {
		return null;
	}

	@Override
	public UcenterSpecialty getspecialtyNobeanExt(String specialtyNo, String schoolCode) {
		return null;
	}
}
