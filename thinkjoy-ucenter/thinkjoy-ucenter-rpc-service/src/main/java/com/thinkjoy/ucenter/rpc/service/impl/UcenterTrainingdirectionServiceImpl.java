package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterTrainingdirectionMapper;
import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirection;
import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirectionExample;
import com.thinkjoy.ucenter.rpc.api.UcenterTrainingdirectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterTrainingdirectionService实现
* Created by user on 2017/10/19.
*/
@Service
@Transactional
@BaseService
public class UcenterTrainingdirectionServiceImpl extends BaseServiceImpl<UcenterTrainingdirectionMapper, UcenterTrainingdirection, UcenterTrainingdirectionExample> implements UcenterTrainingdirectionService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTrainingdirectionServiceImpl.class);

    @Autowired
    UcenterTrainingdirectionMapper ucenterTrainingdirectionMapper;

}