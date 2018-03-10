package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsProjectMapper;
import com.thinkjoy.ams.dao.model.AmsProject;
import com.thinkjoy.ams.dao.model.AmsProjectExample;

import java.util.List;

/**
* 降级实现AmsProjectService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsProjectServiceMock extends BaseServiceMock<AmsProjectMapper, AmsProject, AmsProjectExample> implements AmsProjectService {
    @Override
    public List<String> getAmsProjectRelatedCodes(Integer pid) {
        return null;
    }

    @Override
    public int relateAgencyAndApp(JSONArray datas, String[] appId, Integer pid) {
        return 0;
    }

}
