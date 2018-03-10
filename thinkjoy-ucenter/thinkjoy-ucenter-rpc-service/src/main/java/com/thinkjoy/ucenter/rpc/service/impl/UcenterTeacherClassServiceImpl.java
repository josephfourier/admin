package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherClassMapper;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherClass;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherClassExample;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterTeacherClassService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterTeacherClassServiceImpl extends BaseServiceImpl<UcenterTeacherClassMapper, UcenterTeacherClass, UcenterTeacherClassExample> implements UcenterTeacherClassService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTeacherClassServiceImpl.class);

    @Autowired
    UcenterTeacherClassMapper ucenterTeacherClassMapper;

}