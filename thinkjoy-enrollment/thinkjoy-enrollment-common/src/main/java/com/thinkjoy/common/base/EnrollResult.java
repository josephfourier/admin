package com.thinkjoy.common.base;

/**
 * Created by wangcheng on 17/7/24.
 */
public class EnrollResult extends BaseResult{

    public EnrollResult(EnrollResultConstant enrollResultConstant, Object data) {
        super(enrollResultConstant.getCode(), enrollResultConstant.getMessage(), data);
    }
}
