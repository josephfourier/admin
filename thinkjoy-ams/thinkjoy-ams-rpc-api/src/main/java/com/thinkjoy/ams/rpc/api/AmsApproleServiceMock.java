package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsApproleMapper;
import com.thinkjoy.ams.dao.model.AmsApprole;
import com.thinkjoy.ams.dao.model.AmsApproleExample;

import java.util.List;
import java.util.Map;

/**
* 降级实现AmsApproleService接口
* Created by user on 2017/9/18.
*/
public class AmsApproleServiceMock extends BaseServiceMock<AmsApproleMapper, AmsApprole, AmsApproleExample> implements AmsApproleService {

    @Override
    public List<String> getAmsApproleRelatedCodes(Integer id) {
        return null;
    }

    @Override
    public List<Integer> getAmsAppIdByRoleCode(Map<String, String> map) {
        return null;
    }

    @Override
    public int role(List<String> appIds, List<String> roleIds, Map<String, Object> userIdentiry) {
        return 0;
    }

    @Override
    public List<AmsApprole> getAppRolesByIdentity(Integer userId, Integer userType, String relationCode) {
        return null;
    }

    @Override
    public int updatePerpersonal(Map map) {
        return 0;
    }

    @Override
    public List<AmsApprole> getAmsApproleByIdentityAndAppId(Map map) {
        return null;
    }

}
