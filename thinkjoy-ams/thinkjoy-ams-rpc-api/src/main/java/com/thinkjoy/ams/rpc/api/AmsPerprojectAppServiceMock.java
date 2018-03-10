package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsPerprojectAppMapper;
import com.thinkjoy.ams.dao.model.AmsPerprojectApp;
import com.thinkjoy.ams.dao.model.AmsPerprojectAppExample;

import java.util.Map;

/**
* 降级实现AmsPerprojectAppService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsPerprojectAppServiceMock extends BaseServiceMock<AmsPerprojectAppMapper, AmsPerprojectApp, AmsPerprojectAppExample> implements AmsPerprojectAppService {

    @Override
    public int updatePersonal(Map map) {
        return 0;
    }
}
