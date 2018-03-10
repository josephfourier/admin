package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ams.dao.mapper.OauthCodeMapper;
import com.thinkjoy.ams.dao.model.OauthCode;
import com.thinkjoy.ams.dao.model.OauthCodeExample;

/**
* 降级实现OauthCodeService接口
* Created by shuzheng on 2017/7/27.
*/
public class OauthCodeServiceMock extends BaseServiceMock<OauthCodeMapper, OauthCode, OauthCodeExample> implements OauthCodeService {

}
