package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.upms.dao.mapper.UpmsUserMapper;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.dao.model.UpmsUserExample;

/**
* 降级实现UpmsUserService接口
* Created by  on 2017/3/20.
*/
public class UpmsUserServiceMock extends BaseServiceMock<UpmsUserMapper, UpmsUser, UpmsUserExample> implements UpmsUserService {

    @Override
    public UpmsUser createUser(UpmsUser upmsUser) {
        return null;
    }

}
