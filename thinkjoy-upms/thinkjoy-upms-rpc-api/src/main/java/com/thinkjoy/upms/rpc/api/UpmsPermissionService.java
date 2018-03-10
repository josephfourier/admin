package com.thinkjoy.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsPermission;
import com.thinkjoy.upms.dao.model.UpmsPermissionExample;

/**
* UpmsPermissionService接口
* Created by  on 2017/3/20.
*/
public interface UpmsPermissionService extends BaseService<UpmsPermission, UpmsPermissionExample> {

    JSONArray getTreeByRoleId(Integer roleId);

    JSONArray getTreeByUserId(Integer usereId, Byte type);

}