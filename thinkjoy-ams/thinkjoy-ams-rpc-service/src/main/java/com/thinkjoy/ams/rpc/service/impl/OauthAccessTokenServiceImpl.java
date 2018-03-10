package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.OauthAccessTokenMapper;
import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.dao.model.OauthAccessTokenExample;
import com.thinkjoy.ams.rpc.api.OauthAccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* OauthAccessTokenService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class OauthAccessTokenServiceImpl extends BaseServiceImpl<OauthAccessTokenMapper, OauthAccessToken, OauthAccessTokenExample> implements OauthAccessTokenService {

    private static Logger _log = LoggerFactory.getLogger(OauthAccessTokenServiceImpl.class);

    @Autowired
    OauthAccessTokenMapper oauthAccessTokenMapper;

}