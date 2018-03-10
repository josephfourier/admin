package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherSpecialtyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherSpecialtyExample;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherSpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterTeacherSpecialtyService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterTeacherSpecialtyServiceImpl extends BaseServiceImpl<UcenterTeacherSpecialtyMapper, UcenterTeacherSpecialty, UcenterTeacherSpecialtyExample> implements UcenterTeacherSpecialtyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTeacherSpecialtyServiceImpl.class);

    @Autowired
    UcenterTeacherSpecialtyMapper ucenterTeacherSpecialtyMapper;

}