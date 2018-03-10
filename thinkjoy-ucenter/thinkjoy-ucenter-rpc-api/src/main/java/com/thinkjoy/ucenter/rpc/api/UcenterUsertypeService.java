package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterUsertype;
import com.thinkjoy.ucenter.dao.model.UcenterUsertypeExample;

import java.util.List;

/**
* UcenterUsertypeService接口
* Created by xufei on 2017/7/26.
*/
public interface UcenterUsertypeService extends BaseService<UcenterUsertype, UcenterUsertypeExample> {


    List<UcenterUsertype> getall();

}