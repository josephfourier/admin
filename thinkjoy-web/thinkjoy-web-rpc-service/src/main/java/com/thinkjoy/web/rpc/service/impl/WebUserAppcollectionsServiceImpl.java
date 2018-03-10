package com.thinkjoy.web.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.web.dao.mapper.WebUserAppcollectionsMapper;
import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.dao.model.WebUserAppcollectionsExample;
import com.thinkjoy.web.rpc.api.WebUserAppcollectionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* WebUserAppcollectionsService实现
* Created by user on 2017/8/23.
*/
@Service
@Transactional
@BaseService
public class WebUserAppcollectionsServiceImpl extends BaseServiceImpl<WebUserAppcollectionsMapper, WebUserAppcollections, WebUserAppcollectionsExample> implements WebUserAppcollectionsService {

    private static Logger _log = LoggerFactory.getLogger(WebUserAppcollectionsServiceImpl.class);

    @Autowired
    WebUserAppcollectionsMapper webUserAppcollectionsMapper;

    @Override
    public int batchDeleteByPrimarykey(Map<String, String> map) {
        return webUserAppcollectionsMapper.batchDeleteByPrimarykey(map);
    }

    @Override
    public int batchInsert(List list) {
        return webUserAppcollectionsMapper.batchInsert(list);
    }
}