package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollOrderMapper;
import com.thinkjoy.enrollment.dao.model.EnrollOrder;
import com.thinkjoy.enrollment.dao.model.EnrollOrderExample;
import com.thinkjoy.enrollment.rpc.api.EnrollOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollOrderService实现
* Created by user on 2017/12/27.
*/
@Service
@Transactional
@BaseService
public class EnrollOrderServiceImpl extends BaseServiceImpl<EnrollOrderMapper, EnrollOrder, EnrollOrderExample> implements EnrollOrderService {

    private static Logger _log = LoggerFactory.getLogger(EnrollOrderServiceImpl.class);

    @Autowired
    EnrollOrderMapper enrollOrderMapper;

}