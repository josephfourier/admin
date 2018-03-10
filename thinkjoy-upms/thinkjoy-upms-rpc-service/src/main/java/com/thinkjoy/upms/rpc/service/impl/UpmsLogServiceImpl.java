package com.thinkjoy.upms.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.upms.dao.mapper.UpmsLogMapper;
import com.thinkjoy.upms.dao.model.UpmsLog;
import com.thinkjoy.upms.dao.model.UpmsLogExample;
import com.thinkjoy.upms.rpc.api.UpmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
* UpmsLogService实现
* Created by  on 2017/3/20.
*/
@Service
@Transactional
@BaseService
public class UpmsLogServiceImpl extends BaseServiceImpl<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

    private static Logger _log = LoggerFactory.getLogger(UpmsLogServiceImpl.class);

    @Autowired
    UpmsLogMapper upmsLogMapper;

	@Override
	public long selectpeopleNum(String schoolCode, long startTime, long endTime) {
		Map map=new HashMap<String,Object>();
		map.put("schoolCode", schoolCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		long peopleNum=upmsLogMapper.selectpeopleNum(map);
		return peopleNum;
	}
}