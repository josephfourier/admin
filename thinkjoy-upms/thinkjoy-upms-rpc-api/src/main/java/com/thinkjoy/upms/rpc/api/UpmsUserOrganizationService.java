package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.upms.dao.model.UpmsUserOrganization;
import com.thinkjoy.upms.dao.model.UpmsUserOrganizationExample;

/**
* UpmsUserOrganizationService接口
* Created by  on 2017/3/20.
*/
public interface UpmsUserOrganizationService extends BaseService<UpmsUserOrganization, UpmsUserOrganizationExample> {

    /**
     * 用户组织
     * @param organizationIds 组织ids
     * @param id 用户id
     * @return
     */
    int organization(String[] organizationIds, int id);

}