package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentMapper;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentExample;

/**
* 降级实现UcenterDepartmentService接口
* Created by user on 2017/10/13.
*/
public class UcenterDepartmentServiceMock extends BaseServiceMock<UcenterDepartmentMapper, UcenterDepartment, UcenterDepartmentExample> implements UcenterDepartmentService {

    @Override
    public com.alibaba.fastjson.JSONArray getDepTree(String shcoolCode) {
        return null;
    }

    @Override
    public JSONArray getDepTreeByTeacherId(String schoolCode, String teacherId) {
        return null;
    }

    @Override
    public JSONArray getDepteacherTree(String schoolCode, Integer postId) {return null;}

    @Override
    public JSONArray getDepteacherTree(String schoolCode) {return null;}
}
