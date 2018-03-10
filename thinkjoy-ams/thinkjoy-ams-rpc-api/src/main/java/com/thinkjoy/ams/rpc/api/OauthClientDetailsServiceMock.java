package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.OauthClientDetailsMapper;
import com.thinkjoy.ams.dao.model.OauthClientDetails;
import com.thinkjoy.ams.dao.model.OauthClientDetailsExample;

/**
* 降级实现OauthClientDetailsService接口
* Created by shuzheng on 2017/7/27.
*/
public class OauthClientDetailsServiceMock extends BaseServiceMock<OauthClientDetailsMapper, OauthClientDetails, OauthClientDetailsExample> implements OauthClientDetailsService {

}
