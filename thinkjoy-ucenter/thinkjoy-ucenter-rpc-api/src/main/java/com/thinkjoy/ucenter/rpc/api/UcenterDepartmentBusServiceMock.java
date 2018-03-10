package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentBusMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBusExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.BusInfo;

import java.util.List;
import java.util.Map;

/**
* 降级实现UcenterDepartmentBusService接口
* Created by user on 2018/2/13.
*/
public class UcenterDepartmentBusServiceMock extends BaseServiceMock<UcenterDepartmentBusMapper, UcenterDepartmentBus, UcenterDepartmentBusExample> implements UcenterDepartmentBusService {

    @Override
    public List<BusInfo> selectBusInfoByBusIdAndTeacherLevel(Map<String, String> map) {
        return null;
    }
}
