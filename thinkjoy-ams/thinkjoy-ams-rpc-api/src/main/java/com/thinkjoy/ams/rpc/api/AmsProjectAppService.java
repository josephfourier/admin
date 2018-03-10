package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsProjectApp;
import com.thinkjoy.ams.dao.model.AmsProjectAppExample;

/**
* AmsProjectAppService接口
* Created by shuzheng on 2017/7/27.
*/
public interface AmsProjectAppService extends BaseService<AmsProjectApp, AmsProjectAppExample> {
    int addProjectApp(String[] appids, String pid);
}