package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsApprolePermission;
import com.thinkjoy.ams.dao.model.AmsApprolePermissionExample;
import io.swagger.models.auth.In;

import java.util.List;

/**
* AmsApprolePermissionService接口
* Created by user on 2017/9/18.
*/
public interface AmsApprolePermissionService extends BaseService<AmsApprolePermission, AmsApprolePermissionExample> {
    /**
     * 应用角色权限
     * @param datas 权限数据
     * @param id 角色id
     * @return
     */
    int rolePermission(JSONArray datas, int id);
}