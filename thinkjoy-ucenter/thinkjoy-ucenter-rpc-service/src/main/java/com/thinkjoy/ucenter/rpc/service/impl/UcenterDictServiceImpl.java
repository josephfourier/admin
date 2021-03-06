package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterDictMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDict;
import com.thinkjoy.ucenter.dao.model.UcenterDictExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterDictService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterDictServiceImpl extends BaseServiceImpl<UcenterDictMapper, UcenterDict, UcenterDictExample> implements UcenterDictService {

    private static Logger _log = LoggerFactory.getLogger(UcenterDictServiceImpl.class);

    @Autowired
    UcenterDictMapper ucenterDictMapper;

}