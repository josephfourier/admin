package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentExample;

/**
* UcenterDepartmentService接口
* Created by user on 2017/10/13.
*/
public interface UcenterDepartmentService extends BaseService<UcenterDepartment, UcenterDepartmentExample> {


    JSONArray getDepTree(String schoolCode);

    JSONArray getDepTreeByTeacherId(String schoolCode,String teacherId);

    JSONArray getDepteacherTree(String schoolCode,Integer postId);

    JSONArray getDepteacherTree(String schoolCode);
}