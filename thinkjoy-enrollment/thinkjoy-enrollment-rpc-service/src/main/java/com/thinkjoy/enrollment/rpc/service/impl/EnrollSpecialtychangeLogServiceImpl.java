package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollSpecialtychangeLogMapper;
import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLog;
import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLogExample;
import com.thinkjoy.enrollment.rpc.api.EnrollSpecialtychangeLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollSpecialtychangeLogService实现
* Created by user on 2017/11/16.
*/
@Service
@Transactional
@BaseService
public class EnrollSpecialtychangeLogServiceImpl extends BaseServiceImpl<EnrollSpecialtychangeLogMapper, EnrollSpecialtychangeLog, EnrollSpecialtychangeLogExample> implements EnrollSpecialtychangeLogService {

    private static Logger _log = LoggerFactory.getLogger(EnrollSpecialtychangeLogServiceImpl.class);

    @Autowired
    EnrollSpecialtychangeLogMapper enrollSpecialtychangeLogMapper;

}