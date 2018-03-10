package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterSchoolMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;

/**
* 降级实现UcenterSchoolService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterSchoolServiceMock extends BaseServiceMock<UcenterSchoolMapper, UcenterSchool, UcenterSchoolExample> implements UcenterSchoolService {

    @Override
    public String getNextCodeByAreaCode(String agencyCode, String areaCode) {
        return null;
    }
}
