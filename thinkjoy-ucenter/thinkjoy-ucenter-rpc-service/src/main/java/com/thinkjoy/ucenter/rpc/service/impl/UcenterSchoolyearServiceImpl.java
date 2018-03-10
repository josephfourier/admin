package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterSchoolyearMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolyear;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolyearExample;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolyearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterSchoolyearService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterSchoolyearServiceImpl extends BaseServiceImpl<UcenterSchoolyearMapper, UcenterSchoolyear, UcenterSchoolyearExample> implements UcenterSchoolyearService {

    private static Logger _log = LoggerFactory.getLogger(UcenterSchoolyearServiceImpl.class);

    @Autowired
    UcenterSchoolyearMapper ucenterSchoolyearMapper;

}