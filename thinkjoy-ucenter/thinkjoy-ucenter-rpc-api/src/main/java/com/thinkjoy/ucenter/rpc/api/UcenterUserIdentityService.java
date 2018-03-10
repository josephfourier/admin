package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterUserIdentityDto;

import java.util.List;

/**
* UcenterUserIdentityService接口
* Created by xufei on 2017/7/26.
*/
public interface UcenterUserIdentityService extends BaseService<UcenterUserIdentity, UcenterUserIdentityExample> {

    List<UcenterUserIdentityDto> getUserIdentitesByUserLoginName(String userLoginName);

    List<UcenterUserIdentity> getUserIdentityRelationByUserId(Integer userId);
}