package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.ams.dao.model.AmsRoleResourceExample;

/**
* AmsRoleResourceService接口
* Created by shuzheng on 2017/7/27.
*/
public interface AmsRoleResourceService extends BaseService<AmsRoleResource, AmsRoleResourceExample> {

    int addRoleResource(JSONArray array, String roleId);
}