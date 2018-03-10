package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagencyExample;

/**
* AmsProjectSchoolagencyService接口
* Created by shuzheng on 2017/7/27.
*/
public interface AmsProjectSchoolagencyService extends BaseService<AmsProjectSchoolagency, AmsProjectSchoolagencyExample> {
    int addAmsProjectSchoolagency(JSONArray datas, Integer pid);
}