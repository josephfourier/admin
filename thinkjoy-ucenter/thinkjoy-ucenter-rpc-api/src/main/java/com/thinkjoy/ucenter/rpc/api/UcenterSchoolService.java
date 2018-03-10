package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;

/**
* UcenterSchoolService接口
* Created by xufei on 2017/7/26.
*/
public interface UcenterSchoolService extends BaseService<UcenterSchool, UcenterSchoolExample> {
    String getNextCodeByAreaCode(String agencyCode,String areaCode);
}