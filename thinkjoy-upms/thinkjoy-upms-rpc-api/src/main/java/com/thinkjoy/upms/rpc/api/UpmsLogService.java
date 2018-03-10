package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsLog;
import com.thinkjoy.upms.dao.model.UpmsLogExample;

/**
* UpmsLogService接口
* Created by  on 2017/3/20.
*/
public interface UpmsLogService extends BaseService<UpmsLog, UpmsLogExample> {
	long selectpeopleNum(String schoolCode,long startTime,long endTime);
}