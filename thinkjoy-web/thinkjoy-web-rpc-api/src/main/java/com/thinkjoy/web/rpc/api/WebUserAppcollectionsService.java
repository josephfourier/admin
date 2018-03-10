package com.thinkjoy.web.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.dao.model.WebUserAppcollectionsExample;

import java.util.List;
import java.util.Map;

/**
* WebUserAppcollectionsService接口
* Created by user on 2017/8/23.
*/
public interface WebUserAppcollectionsService extends BaseService<WebUserAppcollections, WebUserAppcollectionsExample> {

    int batchDeleteByPrimarykey(Map<String, String> map);

    int batchInsert(List list);

}