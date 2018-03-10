package com.thinkjoy.web.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.web.dao.mapper.WebUserAppcollectionsMapper;
import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.dao.model.WebUserAppcollectionsExample;

import java.util.List;
import java.util.Map;

/**
* 降级实现WebUserAppcollectionsService接口
* Created by user on 2017/8/23.
*/
public class WebUserAppcollectionsServiceMock extends BaseServiceMock<WebUserAppcollectionsMapper, WebUserAppcollections, WebUserAppcollectionsExample> implements WebUserAppcollectionsService {

    @Override
    public int batchDeleteByPrimarykey(Map<String, String> map) {
        return 0;
    }

    @Override
    public int batchInsert(List list) {
        return 0;
    }
}
