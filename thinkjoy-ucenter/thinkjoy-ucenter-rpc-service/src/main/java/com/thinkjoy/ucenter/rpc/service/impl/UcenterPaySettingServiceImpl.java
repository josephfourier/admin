package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterPaySettingMapper;
import com.thinkjoy.ucenter.dao.model.UcenterPaySetting;
import com.thinkjoy.ucenter.dao.model.UcenterPaySettingExample;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.rpc.api.UcenterPaySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterPaySettingService实现
* Created by user on 2018/1/22.
*/
@Service
@Transactional
@BaseService
public class UcenterPaySettingServiceImpl extends BaseServiceImpl<UcenterPaySettingMapper, UcenterPaySetting, UcenterPaySettingExample> implements UcenterPaySettingService {

    private static Logger _log = LoggerFactory.getLogger(UcenterPaySettingServiceImpl.class);

    @Autowired
    UcenterPaySettingMapper ucenterPaySettingMapper;

    @Override
    public UcenterPaySetting getFirstPaySettingListByOrderId(String order_id) {

        List<UcenterPaySetting> ucenterPaySetting=ucenterPaySettingMapper.getPaySettingListByOrderId(order_id);
        if(null!=ucenterPaySetting &&ucenterPaySetting.size()>0){
            return ucenterPaySetting.get(0);
        }
        return null;
    }
}