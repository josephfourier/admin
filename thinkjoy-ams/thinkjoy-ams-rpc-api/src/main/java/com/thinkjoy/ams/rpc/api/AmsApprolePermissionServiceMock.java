package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsApprolePermissionMapper;
import com.thinkjoy.ams.dao.model.AmsApprolePermission;
import com.thinkjoy.ams.dao.model.AmsApprolePermissionExample;

import java.util.List;

/**
* 降级实现AmsApprolePermissionService接口
* Created by user on 2017/9/18.
*/
public class AmsApprolePermissionServiceMock extends BaseServiceMock<AmsApprolePermissionMapper, AmsApprolePermission, AmsApprolePermissionExample> implements AmsApprolePermissionService {

    @Override
    public int rolePermission(JSONArray datas, int id) {
        return 0;
    }
}
