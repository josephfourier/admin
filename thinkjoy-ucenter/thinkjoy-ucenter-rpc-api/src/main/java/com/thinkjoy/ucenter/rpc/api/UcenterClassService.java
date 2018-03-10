package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.*;

/**
* UcenterClassService接口
* Created by user on 2017/10/13.
*/
public interface UcenterClassService extends BaseService<UcenterClass, UcenterClassExample> {
    int insertSelective(UcenterClass ucenterClass,  UcenterDepartment ucenterDepartment);
    int updateByPrimaryKeySelective(UcenterClass ucenterClass, UcenterDepartmentBus departmentBus,UcenterDepartment ucenterDepartment);
    int deletePrimaryKey(Integer classId,UcenterDepartmentBus departmentBus);
}