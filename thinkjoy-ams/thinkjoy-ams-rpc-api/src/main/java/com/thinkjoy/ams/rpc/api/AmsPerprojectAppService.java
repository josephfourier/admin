package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsPerprojectApp;
import com.thinkjoy.ams.dao.model.AmsPerprojectAppExample;

import java.util.Map;

/**
* AmsPerprojectAppService接口
* Created by shuzheng on 2017/7/27.
*/
public interface AmsPerprojectAppService extends BaseService<AmsPerprojectApp, AmsPerprojectAppExample> {
    int updatePersonal(Map map);
}