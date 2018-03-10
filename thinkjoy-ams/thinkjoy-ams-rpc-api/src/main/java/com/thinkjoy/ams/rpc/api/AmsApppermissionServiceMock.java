package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsApppermissionMapper;
import com.thinkjoy.ams.dao.model.AmsApppermission;
import com.thinkjoy.ams.dao.model.AmsApppermissionExample;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 降级实现AmsApppermissionService接口
* Created by user on 2017/9/18.
*/
public class AmsApppermissionServiceMock extends BaseServiceMock<AmsApppermissionMapper, AmsApppermission, AmsApppermissionExample> implements AmsApppermissionService {

    @Override
    public JSONArray getTreeByApproleId(Integer approleId) {
        return null;
    }

    @Override
    public List<AmsApppermission> getAppPermissionByRelationCodeAndUserType(Map<String, String> map) {
        return null;
    }

    @Override
    public List<AmsApppermission> getAppPermissionsByApproleId(Set<Integer> set) {
        return null;
    }
}
