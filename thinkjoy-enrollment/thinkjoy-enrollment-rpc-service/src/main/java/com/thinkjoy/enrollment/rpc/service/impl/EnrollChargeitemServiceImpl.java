package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargeitemMapper;
import com.thinkjoy.enrollment.dao.model.EnrollChargeitem;
import com.thinkjoy.enrollment.dao.model.EnrollChargeitemExample;
import com.thinkjoy.enrollment.rpc.api.EnrollChargeitemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollChargeitemService实现
* Created by user on 2017/11/10.
*/
@Service
@Transactional
@BaseService
public class EnrollChargeitemServiceImpl extends BaseServiceImpl<EnrollChargeitemMapper, EnrollChargeitem, EnrollChargeitemExample> implements EnrollChargeitemService {

    private static Logger _log = LoggerFactory.getLogger(EnrollChargeitemServiceImpl.class);

    @Autowired
    EnrollChargeitemMapper enrollChargeitemMapper;

}