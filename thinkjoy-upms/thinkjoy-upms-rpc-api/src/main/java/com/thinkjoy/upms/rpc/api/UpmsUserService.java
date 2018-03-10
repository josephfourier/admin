package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.dao.model.UpmsUserExample;

/**
* UpmsUserService接口
* Created by  on 2017/3/20.
*/
public interface UpmsUserService extends BaseService<UpmsUser, UpmsUserExample> {

    UpmsUser createUser(UpmsUser upmsUser);

}