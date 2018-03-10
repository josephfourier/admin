package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.upms.dao.mapper.UpmsLogMapper;
import com.thinkjoy.upms.dao.model.UpmsLog;
import com.thinkjoy.upms.dao.model.UpmsLogExample;

/**
* 降级实现UpmsLogService接口
* Created by  on 2017/3/20.
*/
public class UpmsLogServiceMock extends BaseServiceMock<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

	@Override
	public long selectpeopleNum(String schoolCode, long startTime, long endTime) {return 0;}
}
