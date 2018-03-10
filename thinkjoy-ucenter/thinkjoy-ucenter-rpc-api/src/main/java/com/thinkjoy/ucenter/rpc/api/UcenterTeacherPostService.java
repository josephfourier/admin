package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPost;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPostExample;

/**
* UcenterTeacherPostService接口
* Created by user on 2018/2/13.
*/
public interface UcenterTeacherPostService extends BaseService<UcenterTeacherPost, UcenterTeacherPostExample> {
    int addteacher(JSONArray datas, int id);
}