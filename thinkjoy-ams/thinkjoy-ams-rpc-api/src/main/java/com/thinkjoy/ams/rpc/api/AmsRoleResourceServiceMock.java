package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsRoleResourceMapper;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.ams.dao.model.AmsRoleResourceExample;

/**
* 降级实现AmsRoleResourceService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsRoleResourceServiceMock extends BaseServiceMock<AmsRoleResourceMapper, AmsRoleResource, AmsRoleResourceExample> implements AmsRoleResourceService {

    @Override
    public int addRoleResource(JSONArray array, String roleId) {
        return 0;
    }
}
