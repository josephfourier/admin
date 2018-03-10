package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsResources;
import com.thinkjoy.ams.dao.model.AmsResourcesExample;

import java.util.List;
import java.util.Map;

/**
* AmsResourcesService接口
* Created by shuzheng on 2017/8/1.
*/
public interface AmsResourcesService extends BaseService<AmsResources, AmsResourcesExample> {

    JSONArray getResourceTree(Map<String, String> allResourceClass, String roleId, List<AmsResources> amsResources);

    List<AmsResources> getResourcesByRoleId(Integer roleId);
}