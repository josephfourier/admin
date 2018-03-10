package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.upms.dao.mapper.UpmsUserRoleMapper;
import com.thinkjoy.upms.dao.model.UpmsUserRole;
import com.thinkjoy.upms.dao.model.UpmsUserRoleExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 降级实现UpmsUserRoleService接口
* Created by  on 2017/3/20.
*/
public class UpmsUserRoleServiceMock extends BaseServiceMock<UpmsUserRoleMapper, UpmsUserRole, UpmsUserRoleExample> implements UpmsUserRoleService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserRoleServiceMock.class);

    @Override
    public int role(String[] roleIds, int id) {
        _log.info("UpmsUserRoleServiceMock => role");
        return 0;
    }

}
