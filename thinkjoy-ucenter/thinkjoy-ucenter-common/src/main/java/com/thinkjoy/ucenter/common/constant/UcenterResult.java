package com.thinkjoy.ucenter.common.constant;

import com.thinkjoy.common.base.BaseResult;

/**
 * Created by xufei on 2017/7/26.
 */
public class UcenterResult extends BaseResult {
    public UcenterResult(UcenterResultConstant ucenterResultConstant, Object data) {
        super(ucenterResultConstant.getCode(), ucenterResultConstant.getMessage(), data);
    }

    public UcenterResult(UcenterResultConstant ucenterResultConstant,String message,Object data) {
        super(ucenterResultConstant.getCode(), message, data);
    }
}
