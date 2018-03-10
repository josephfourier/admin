package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.AmsRoleMapper;
import com.thinkjoy.ams.dao.model.AmsRole;
import com.thinkjoy.ams.dao.model.AmsRoleExample;

/**
* 降级实现AmsRoleService接口
* Created by shuzheng on 2017/7/27.
*/
public class AmsRoleServiceMock extends BaseServiceMock<AmsRoleMapper, AmsRole, AmsRoleExample> implements AmsRoleService {

}
