package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterUserIdentityDto;

import java.util.List;

/**
* 降级实现UcenterUserIdentityService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterUserIdentityServiceMock extends BaseServiceMock<UcenterUserIdentityMapper, UcenterUserIdentity, UcenterUserIdentityExample> implements UcenterUserIdentityService {

    @Override
    public List<UcenterUserIdentityDto> getUserIdentitesByUserLoginName(String userLoginName) {
        return null;
    }

    @Override
    public List<UcenterUserIdentity> getUserIdentityRelationByUserId(Integer userId) {
        return null;
    }
}
