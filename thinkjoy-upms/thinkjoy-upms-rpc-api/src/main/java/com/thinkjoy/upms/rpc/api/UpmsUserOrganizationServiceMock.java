package com.thinkjoy.upms.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.thinkjoy.upms.dao.model.UpmsUserOrganization;
import com.thinkjoy.upms.dao.model.UpmsUserOrganizationExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 降级实现UpmsUserOrganizationService接口
* Created by  on 2017/3/20.
*/
public class UpmsUserOrganizationServiceMock extends BaseServiceMock<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements UpmsUserOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserOrganizationServiceMock.class);

    @Override
    public int organization(String[] organizationIds, int id) {
        _log.info("UpmsUserOrganizationServiceMock => organization");
        return 0;
    }

}
