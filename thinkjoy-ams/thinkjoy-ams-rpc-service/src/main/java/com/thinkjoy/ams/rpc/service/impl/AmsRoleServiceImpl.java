package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsRoleMapper;
import com.thinkjoy.ams.dao.model.AmsRole;
import com.thinkjoy.ams.dao.model.AmsRoleExample;
import com.thinkjoy.ams.rpc.api.AmsRoleService;
import com.thinkjoy.common.db.DataSourceEnum;
import com.thinkjoy.common.db.DynamicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* AmsRoleService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class AmsRoleServiceImpl extends BaseServiceImpl<AmsRoleMapper, AmsRole, AmsRoleExample> implements AmsRoleService {

    private static Logger _log = LoggerFactory.getLogger(AmsRoleServiceImpl.class);

    @Autowired
    AmsRoleMapper amsRoleMapper;

}