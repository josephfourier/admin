package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterUsertypeMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUsertype;
import com.thinkjoy.ucenter.dao.model.UcenterUsertypeExample;
import com.thinkjoy.ucenter.rpc.api.UcenterUsertypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterUsertypeService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterUsertypeServiceImpl extends BaseServiceImpl<UcenterUsertypeMapper, UcenterUsertype, UcenterUsertypeExample> implements UcenterUsertypeService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUsertypeServiceImpl.class);

    @Autowired
    UcenterUsertypeMapper ucenterUsertypeMapper;

    @Override
    public List<UcenterUsertype> getall() {
        return ucenterUsertypeMapper.getall();
    }
}