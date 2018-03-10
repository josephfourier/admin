package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterPaySettingMapper;
import com.thinkjoy.ucenter.dao.model.UcenterPaySetting;
import com.thinkjoy.ucenter.dao.model.UcenterPaySettingExample;

/**
* 降级实现UcenterPaySettingService接口
* Created by user on 2018/1/22.
*/
public class UcenterPaySettingServiceMock extends BaseServiceMock<UcenterPaySettingMapper, UcenterPaySetting, UcenterPaySettingExample> implements UcenterPaySettingService {

    @Override
    public UcenterPaySetting getFirstPaySettingListByOrderId(String order_id) {
        return null;
    }
}
