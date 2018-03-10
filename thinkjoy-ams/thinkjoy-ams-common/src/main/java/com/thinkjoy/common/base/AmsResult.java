package com.thinkjoy.common.base;

/**
 * Created by wangcheng on 17/7/24.
 */
public class AmsResult extends BaseResult{

    public AmsResult(AmsResultConstant cmsResultConstant, Object data) {
        super(cmsResultConstant.getCode(), cmsResultConstant.getMessage(), data);
    }
}
