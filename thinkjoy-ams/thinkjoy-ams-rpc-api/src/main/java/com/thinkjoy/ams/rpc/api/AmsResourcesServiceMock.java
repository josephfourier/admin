package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsResourcesMapper;
import com.thinkjoy.ams.dao.model.AmsResources;
import com.thinkjoy.ams.dao.model.AmsResourcesExample;

import java.util.List;
import java.util.Map;

/**
* 降级实现AmsResourcesService接口
* Created by shuzheng on 2017/8/1.
*/
public class AmsResourcesServiceMock extends BaseServiceMock<AmsResourcesMapper, AmsResources, AmsResourcesExample> implements AmsResourcesService {

    @Override
    public JSONArray getResourceTree(Map<String, String> allResourceClass, String roleId, List<AmsResources> amsResources) {
        return null;
    }

    @Override
    public List<AmsResources> getResourcesByRoleId(Integer roleId) {
        return null;
    }

}
