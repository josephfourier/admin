package com.thinkjoy.upms.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.upms.dao.mapper.UpmsUserSchoolMapper;
import com.thinkjoy.upms.dao.model.UpmsUserSchool;
import com.thinkjoy.upms.dao.model.UpmsUserSchoolExample;
import com.thinkjoy.upms.rpc.api.UpmsUserSchoolService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserSchoolService实现
* Created by user on 2017/9/26.
*/
@Service
@Transactional
@BaseService
public class UpmsUserSchoolServiceImpl extends BaseServiceImpl<UpmsUserSchoolMapper, UpmsUserSchool, UpmsUserSchoolExample> implements UpmsUserSchoolService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserSchoolServiceImpl.class);

    @Autowired
    UpmsUserSchoolMapper upmsUserSchoolMapper;

	@Override
	public int school(String[] schoolCodes, int id) {
		int result = 0;
		// 删除旧记录
		UpmsUserSchoolExample upmsUserSchoolExample = new UpmsUserSchoolExample();
		upmsUserSchoolExample.createCriteria()
				.andUserIdEqualTo(id);
		upmsUserSchoolMapper.deleteByExample(upmsUserSchoolExample);
		// 增加新记录
		if (null != schoolCodes) {
			for (String schoolCode : schoolCodes) {
				if (StringUtils.isNotBlank(schoolCode)) {
					UpmsUserSchool upmsUserSchool = new UpmsUserSchool();
					upmsUserSchool.setUserId(id);
					upmsUserSchool.setSchoolCode(schoolCode);
					result = upmsUserSchoolMapper.insertSelective(upmsUserSchool);
				}
			}
		}
		return result;
	}
}