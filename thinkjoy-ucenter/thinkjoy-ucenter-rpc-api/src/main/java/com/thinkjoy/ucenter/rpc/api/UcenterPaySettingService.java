package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterPaySetting;
import com.thinkjoy.ucenter.dao.model.UcenterPaySettingExample;
import com.thinkjoy.ucenter.dao.model.UcenterUser;

/**
* UcenterPaySettingService接口
* Created by user on 2018/1/22.
*/
public interface UcenterPaySettingService extends BaseService<UcenterPaySetting, UcenterPaySettingExample> {


    UcenterPaySetting getFirstPaySettingListByOrderId(String order_id);



}