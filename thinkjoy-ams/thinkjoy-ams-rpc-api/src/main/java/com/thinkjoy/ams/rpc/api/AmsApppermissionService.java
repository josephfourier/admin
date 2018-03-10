package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsApppermission;
import com.thinkjoy.ams.dao.model.AmsApppermissionExample;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AmsApppermissionService接口
 * Created by user on 2017/9/18.
 */
public interface AmsApppermissionService extends BaseService<AmsApppermission, AmsApppermissionExample> {
    JSONArray getTreeByApproleId(Integer approleId);

    List<AmsApppermission> getAppPermissionByRelationCodeAndUserType(Map<String, String> map);

    List<AmsApppermission> getAppPermissionsByApproleId(Set<Integer> set);


}