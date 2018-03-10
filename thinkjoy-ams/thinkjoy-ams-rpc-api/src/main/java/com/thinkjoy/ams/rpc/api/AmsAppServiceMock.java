package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsAppMapper;
import com.thinkjoy.ams.dao.model.AmsAppExample;

import java.util.List;
import java.util.Map;

/**
* 降级实现AmsAppService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsAppServiceMock extends BaseServiceMock<AmsAppMapper, AmsApp, AmsAppExample> implements AmsAppService {

    @Override
    public List<AmsApp> getAppsByAgencyOrSchoolCode(Map<String, String> map) {
        return null;
    }

    @Override
    public List<AmsApp> getUserAppByAgencyOrSchoolCode(Map<String, String> map) {
        return null;
    }

    @Override
    public int userapp(List<String> appIds, Map<String, Object> userIdentiry) {
        return 0;
    }

    @Override
    public AmsApp getIsPersonalByClientId(String appId) {
        return null;
    }

}
