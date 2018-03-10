package com.thinkjoy.web.common.constant;

import com.thinkjoy.common.base.BaseResult;

/**
 * upms系统常量枚举类
 * Created by shuzheng on 2017/2/18.
 */
public class WebResult extends BaseResult {

    public WebResult(WebResultConstant webResultConstant, Object data) {
        super(webResultConstant.getCode(), webResultConstant.getMessage(), data);
    }

}
