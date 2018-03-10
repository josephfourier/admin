package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsProjectAppMapper;
import com.thinkjoy.ams.dao.model.AmsProjectApp;
import com.thinkjoy.ams.dao.model.AmsProjectAppExample;

/**
* 降级实现AmsProjectAppService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsProjectAppServiceMock extends BaseServiceMock<AmsProjectAppMapper, AmsProjectApp, AmsProjectAppExample> implements AmsProjectAppService {

    @Override
    public int addProjectApp(String[] appids, String pid) {
        return 0;
    }
}
