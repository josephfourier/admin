package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.OauthAccessTokenMapper;
import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.dao.model.OauthAccessTokenExample;

/**
* 降级实现OauthAccessTokenService接口
* Created by shuzheng on 2017/7/27.
*/
public class OauthAccessTokenServiceMock extends BaseServiceMock<OauthAccessTokenMapper, OauthAccessToken, OauthAccessTokenExample> implements OauthAccessTokenService {

}
