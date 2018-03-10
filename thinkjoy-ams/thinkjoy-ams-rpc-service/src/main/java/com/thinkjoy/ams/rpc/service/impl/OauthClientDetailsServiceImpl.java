package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.OauthClientDetailsMapper;
import com.thinkjoy.ams.dao.model.OauthClientDetails;
import com.thinkjoy.ams.dao.model.OauthClientDetailsExample;
import com.thinkjoy.ams.rpc.api.OauthClientDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* OauthClientDetailsService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class OauthClientDetailsServiceImpl extends BaseServiceImpl<OauthClientDetailsMapper, OauthClientDetails, OauthClientDetailsExample> implements OauthClientDetailsService {

    private static Logger _log = LoggerFactory.getLogger(OauthClientDetailsServiceImpl.class);

    @Autowired
    OauthClientDetailsMapper oauthClientDetailsMapper;

}