package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsUserRole;
import com.thinkjoy.upms.dao.model.UpmsUserRoleExample;

/**
* UpmsUserRoleService接口
* Created by  on 2017/3/20.
*/
public interface UpmsUserRoleService extends BaseService<UpmsUserRole, UpmsUserRoleExample> {

    /**
     * 用户角色
     * @param roleIds 角色ids
     * @param id 用户id
     * @return
     */
    int role(String[] roleIds, int id);

}