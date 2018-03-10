package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollFamilyMapper;
import com.thinkjoy.enrollment.dao.model.EnrollFamily;
import com.thinkjoy.enrollment.dao.model.EnrollFamilyExample;
import com.thinkjoy.enrollment.rpc.api.EnrollFamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollFamilyService实现
* Created by user on 2017/12/25.
*/
@Service
@Transactional
@BaseService
public class EnrollFamilyServiceImpl extends BaseServiceImpl<EnrollFamilyMapper, EnrollFamily, EnrollFamilyExample> implements EnrollFamilyService {

    private static Logger _log = LoggerFactory.getLogger(EnrollFamilyServiceImpl.class);

    @Autowired
    EnrollFamilyMapper enrollFamilyMapper;

}