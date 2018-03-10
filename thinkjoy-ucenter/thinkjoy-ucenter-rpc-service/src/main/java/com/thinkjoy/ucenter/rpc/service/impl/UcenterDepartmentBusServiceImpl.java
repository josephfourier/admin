package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentBusMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBusExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.BusInfo;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* UcenterDepartmentBusService实现
* Created by user on 2018/2/13.
*/
@Service
@Transactional
@BaseService
public class UcenterDepartmentBusServiceImpl extends BaseServiceImpl<UcenterDepartmentBusMapper, UcenterDepartmentBus, UcenterDepartmentBusExample> implements UcenterDepartmentBusService {

    private static Logger _log = LoggerFactory.getLogger(UcenterDepartmentBusServiceImpl.class);

    @Autowired
    UcenterDepartmentBusMapper ucenterDepartmentBusMapper;

    @Override
    public List<BusInfo> selectBusInfoByBusIdAndTeacherLevel(Map<String, String> map) {
        return ucenterDepartmentBusMapper.selectBusInfoByBusIdAndTeacherLevel(map);
    }
}