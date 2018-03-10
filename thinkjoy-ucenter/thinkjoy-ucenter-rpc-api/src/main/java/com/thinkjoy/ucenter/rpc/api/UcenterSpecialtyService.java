package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialtyExample;

/**
* UcenterSpecialtyService接口
* Created by user on 2017/10/13.
*/
public interface UcenterSpecialtyService extends BaseService<UcenterSpecialty, UcenterSpecialtyExample> {
	UcenterSpecialty getspecialtybeanExt(String specialtyCode,String schoolCode);
	UcenterSpecialty getspecialtyNobeanExt(String specialtyNo,String schoolCode);
}