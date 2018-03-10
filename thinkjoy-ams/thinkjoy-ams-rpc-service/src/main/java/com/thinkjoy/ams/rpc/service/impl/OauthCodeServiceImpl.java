package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.OauthCodeMapper;
import com.thinkjoy.ams.dao.model.OauthCode;
import com.thinkjoy.ams.dao.model.OauthCodeExample;
import com.thinkjoy.ams.rpc.api.OauthCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* OauthCodeService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class OauthCodeServiceImpl extends BaseServiceImpl<OauthCodeMapper, OauthCode, OauthCodeExample> implements OauthCodeService {

    private static Logger _log = LoggerFactory.getLogger(OauthCodeServiceImpl.class);

    @Autowired
    OauthCodeMapper oauthCodeMapper;

}