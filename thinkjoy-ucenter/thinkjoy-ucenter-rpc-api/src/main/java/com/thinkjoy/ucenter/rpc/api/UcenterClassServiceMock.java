package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterClassMapper;
import com.thinkjoy.ucenter.dao.model.UcenterClass;
import com.thinkjoy.ucenter.dao.model.UcenterClassExample;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;

/**
* 降级实现UcenterClassService接口
* Created by user on 2017/10/13.
*/
public class UcenterClassServiceMock extends BaseServiceMock<UcenterClassMapper, UcenterClass, UcenterClassExample> implements UcenterClassService {

    @Override
    public int insertSelective(UcenterClass ucenterClass, UcenterDepartment ucenterDepartment) {return 0;}

    @Override
    public int updateByPrimaryKeySelective(UcenterClass ucenterClass, UcenterDepartmentBus departmentBus, UcenterDepartment ucenterDepartment) {return 0;}

    @Override
    public int deletePrimaryKey(Integer classId, UcenterDepartmentBus departmentBus) {return 0;}
}
