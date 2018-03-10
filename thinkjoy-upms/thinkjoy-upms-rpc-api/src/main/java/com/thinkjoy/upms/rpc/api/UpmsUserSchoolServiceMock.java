package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.upms.dao.mapper.UpmsUserSchoolMapper;
import com.thinkjoy.upms.dao.model.UpmsUserSchool;
import com.thinkjoy.upms.dao.model.UpmsUserSchoolExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 降级实现UpmsUserSchoolService接口
* Created by user on 2017/9/26.
*/
public class UpmsUserSchoolServiceMock extends BaseServiceMock<UpmsUserSchoolMapper, UpmsUserSchool, UpmsUserSchoolExample> implements UpmsUserSchoolService {

	private static Logger _log = LoggerFactory.getLogger(UpmsUserSchoolServiceMock.class);

	@Override
	public int school(String[] schoolCodes, int id) {
		//_log.info("UpmsUserSchoolServiceMock => organization");
		return 0;
	}
}
