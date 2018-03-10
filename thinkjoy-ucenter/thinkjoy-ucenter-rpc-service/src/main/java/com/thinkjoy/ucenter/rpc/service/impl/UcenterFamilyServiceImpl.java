package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterFamilyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterFamily;
import com.thinkjoy.ucenter.dao.model.UcenterFamilyExample;
import com.thinkjoy.ucenter.rpc.api.UcenterFamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterFamilyService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterFamilyServiceImpl extends BaseServiceImpl<UcenterFamilyMapper, UcenterFamily, UcenterFamilyExample> implements UcenterFamilyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterFamilyServiceImpl.class);

    @Autowired
    UcenterFamilyMapper ucenterFamilyMapper;

}