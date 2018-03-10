package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsUserSchool;
import com.thinkjoy.upms.dao.model.UpmsUserSchoolExample;

/**
* UpmsUserSchoolService接口
* Created by user on 2017/9/26.
*/
public interface UpmsUserSchoolService extends BaseService<UpmsUserSchool, UpmsUserSchoolExample> {

	/**
	 * 用户学校
	 * @param schoolCodes 学校codes
	 * @param id 用户id
	 * @return
	 */
	int school(String[] schoolCodes, int id);
}