package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBusExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.BusInfo;

import java.util.List;
import java.util.Map;

/**
* UcenterDepartmentBusService接口
* Created by user on 2018/2/13.
*/
public interface UcenterDepartmentBusService extends BaseService<UcenterDepartmentBus, UcenterDepartmentBusExample> {
    /**
     * 主要用来获取busId对应的班级或者院系的名称
     * @param map
     * @return
     */
    List<BusInfo> selectBusInfoByBusIdAndTeacherLevel(Map<String, String> map);

}