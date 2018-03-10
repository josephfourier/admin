package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsPerprojectAppMapper;
import com.thinkjoy.ams.dao.model.AmsPerprojectApp;
import com.thinkjoy.ams.dao.model.AmsPerprojectAppExample;
import com.thinkjoy.ams.rpc.api.AmsPerprojectAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * AmsPerprojectAppService实现
 * Created by shuzheng on 2017/7/27.
 */
@Service
@Transactional
@BaseService
public class AmsPerprojectAppServiceImpl extends BaseServiceImpl<AmsPerprojectAppMapper, AmsPerprojectApp, AmsPerprojectAppExample> implements AmsPerprojectAppService {

    private static Logger _log = LoggerFactory.getLogger(AmsPerprojectAppServiceImpl.class);

    @Autowired
    AmsPerprojectAppMapper amsPerprojectAppMapper;

    @Override
    public int updatePersonal(Map map) {
        return amsPerprojectAppMapper.updatePersonal(map);
    }
}