package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterPostMapper;
import com.thinkjoy.ucenter.dao.model.UcenterPost;
import com.thinkjoy.ucenter.dao.model.UcenterPostExample;

/**
* 降级实现UcenterPostService接口
* Created by user on 2018/2/13.
*/
public class UcenterPostServiceMock extends BaseServiceMock<UcenterPostMapper, UcenterPost, UcenterPostExample> implements UcenterPostService {

}
