package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollTeacherMapper;
import com.thinkjoy.enrollment.dao.model.EnrollTeacher;
import com.thinkjoy.enrollment.dao.model.EnrollTeacherExample;
import com.thinkjoy.enrollment.rpc.api.EnrollTeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollTeacherService实现
* Created by user on 2017/11/2.
*/
@Service
@Transactional
@BaseService
public class EnrollTeacherServiceImpl extends BaseServiceImpl<EnrollTeacherMapper, EnrollTeacher, EnrollTeacherExample> implements EnrollTeacherService {

    private static Logger _log = LoggerFactory.getLogger(EnrollTeacherServiceImpl.class);

    @Autowired
    EnrollTeacherMapper enrollTeacherMapper;

}