package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsProjectAppMapper;
import com.thinkjoy.ams.dao.model.AmsProjectApp;
import com.thinkjoy.ams.dao.model.AmsProjectAppExample;
import com.thinkjoy.ams.rpc.api.AmsProjectAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* AmsProjectAppService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class AmsProjectAppServiceImpl extends BaseServiceImpl<AmsProjectAppMapper, AmsProjectApp, AmsProjectAppExample> implements AmsProjectAppService {

    private static Logger _log = LoggerFactory.getLogger(AmsProjectAppServiceImpl.class);

    @Autowired
    AmsProjectAppMapper amsProjectAppMapper;

    @Override
    public int addProjectApp(String[] appids, String pid) {

        //删除已选的应用
        AmsProjectAppExample amsProjectAppExample = new AmsProjectAppExample();

        amsProjectAppExample.createCriteria().andProjectIdEqualTo(Integer.parseInt(pid));
        int i = amsProjectAppMapper.deleteByExample(amsProjectAppExample);

        if (appids == null){
            return 0;
        }

        //新增
        for (String aid : appids){

            AmsProjectApp amsProjectApp = new AmsProjectApp();
            amsProjectApp.setProjectId(Integer.parseInt(pid));
            amsProjectApp.setAppId(Integer.parseInt(aid));
            amsProjectAppMapper.insertSelective(amsProjectApp);
        }
        return appids.length;
    }
}