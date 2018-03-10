package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterDatasyncLogMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLog;
import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLogExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDatasyncLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterDatasyncLogService实现
* Created by user on 2017/10/19.
*/
@Service
@Transactional
@BaseService
public class UcenterDatasyncLogServiceImpl extends BaseServiceImpl<UcenterDatasyncLogMapper, UcenterDatasyncLog, UcenterDatasyncLogExample> implements UcenterDatasyncLogService {

    private static Logger _log = LoggerFactory.getLogger(UcenterDatasyncLogServiceImpl.class);

    @Autowired
    UcenterDatasyncLogMapper ucenterDatasyncLogMapper;

}