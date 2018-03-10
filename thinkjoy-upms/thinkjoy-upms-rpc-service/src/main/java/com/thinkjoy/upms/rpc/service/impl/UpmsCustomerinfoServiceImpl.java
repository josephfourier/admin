package com.thinkjoy.upms.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.upms.dao.mapper.UpmsCustomerinfoMapper;
import com.thinkjoy.upms.dao.model.UpmsCustomerinfo;
import com.thinkjoy.upms.dao.model.UpmsCustomerinfoExample;
import com.thinkjoy.upms.rpc.api.UpmsCustomerinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsCustomerinfoService实现
* Created by user on 2017/10/31.
*/
@Service
@Transactional
@BaseService
public class UpmsCustomerinfoServiceImpl extends BaseServiceImpl<UpmsCustomerinfoMapper, UpmsCustomerinfo, UpmsCustomerinfoExample> implements UpmsCustomerinfoService {

    private static Logger _log = LoggerFactory.getLogger(UpmsCustomerinfoServiceImpl.class);

    @Autowired
    UpmsCustomerinfoMapper upmsCustomerinfoMapper;

}