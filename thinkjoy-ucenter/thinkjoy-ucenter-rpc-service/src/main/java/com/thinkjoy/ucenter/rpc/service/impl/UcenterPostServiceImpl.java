package com.thinkjoy.ucenter.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterPostMapper;
import com.thinkjoy.ucenter.dao.model.UcenterPost;
import com.thinkjoy.ucenter.dao.model.UcenterPostExample;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPost;
import com.thinkjoy.ucenter.rpc.api.UcenterPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* UcenterPostService实现
* Created by user on 2018/2/13.
*/
@Service
@Transactional
@BaseService
public class UcenterPostServiceImpl extends BaseServiceImpl<UcenterPostMapper, UcenterPost, UcenterPostExample> implements UcenterPostService {

    private static Logger _log = LoggerFactory.getLogger(UcenterPostServiceImpl.class);

    @Autowired
    UcenterPostMapper ucenterPostMapper;

}