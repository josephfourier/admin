package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherPostMapper;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPost;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPostExample;

/**
* 降级实现UcenterTeacherPostService接口
* Created by user on 2018/2/13.
*/
public class UcenterTeacherPostServiceMock extends BaseServiceMock<UcenterTeacherPostMapper, UcenterTeacherPost, UcenterTeacherPostExample> implements UcenterTeacherPostService {

    @Override
    public int addteacher(JSONArray datas, int id) {return 0;}
}
