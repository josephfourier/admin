package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterUserIdentityDto;
import com.thinkjoy.ucenter.rpc.api.UcenterUserIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterUserIdentityService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterUserIdentityServiceImpl extends BaseServiceImpl<UcenterUserIdentityMapper, UcenterUserIdentity, UcenterUserIdentityExample> implements UcenterUserIdentityService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserIdentityServiceImpl.class);

    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;

    @Override
    public List<UcenterUserIdentityDto> getUserIdentitesByUserLoginName(String userLoginName) {
        return ucenterUserIdentityMapper.getUserIdentitesByUserLoginName(userLoginName);
    }

    @Override
    public List<UcenterUserIdentity> getUserIdentityRelationByUserId(Integer userId) {
        return ucenterUserIdentityMapper.getUserIdentityRelationByUserId(userId);
    }
}