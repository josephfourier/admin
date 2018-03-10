package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsProjectSchoolagencyMapper;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagencyExample;

/**
* 降级实现AmsProjectSchoolagencyService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsProjectSchoolagencyServiceMock extends BaseServiceMock<AmsProjectSchoolagencyMapper, AmsProjectSchoolagency, AmsProjectSchoolagencyExample> implements AmsProjectSchoolagencyService {

    @Override
    public int addAmsProjectSchoolagency(JSONArray datas, Integer pid) {
        return 0;
    }
}
