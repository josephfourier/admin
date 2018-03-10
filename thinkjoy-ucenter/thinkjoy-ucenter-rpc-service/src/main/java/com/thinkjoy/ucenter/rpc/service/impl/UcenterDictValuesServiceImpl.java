package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterDictValuesMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import com.thinkjoy.ucenter.dao.model.UcenterDictValuesExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterDictValuesService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterDictValuesServiceImpl extends BaseServiceImpl<UcenterDictValuesMapper, UcenterDictValues, UcenterDictValuesExample> implements UcenterDictValuesService {

    private static Logger _log = LoggerFactory.getLogger(UcenterDictValuesServiceImpl.class);

    @Autowired
    UcenterDictValuesMapper ucenterDictValuesMapper;

}