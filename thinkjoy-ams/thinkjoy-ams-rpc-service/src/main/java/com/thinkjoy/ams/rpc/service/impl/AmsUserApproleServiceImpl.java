package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsUserApproleMapper;
import com.thinkjoy.ams.dao.model.AmsUserApprole;
import com.thinkjoy.ams.dao.model.AmsUserApproleExample;
import com.thinkjoy.ams.rpc.api.AmsUserApproleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* AmsUserApproleService实现
* Created by user on 2017/9/18.
*/
@Service
@Transactional
@BaseService
public class AmsUserApproleServiceImpl extends BaseServiceImpl<AmsUserApproleMapper, AmsUserApprole, AmsUserApproleExample> implements AmsUserApproleService {

    private static Logger _log = LoggerFactory.getLogger(AmsUserApproleServiceImpl.class);

    @Autowired
    AmsUserApproleMapper amsUserApproleMapper;

}