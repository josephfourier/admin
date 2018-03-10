package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterUsertypeMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUsertype;
import com.thinkjoy.ucenter.dao.model.UcenterUsertypeExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* 降级实现UcenterUsertypeService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterUsertypeServiceMock extends BaseServiceMock<UcenterUsertypeMapper, UcenterUsertype, UcenterUsertypeExample> implements UcenterUsertypeService {

    @Autowired
    UcenterUsertypeMapper ucenterUsertypeMapper;
    @Override
    public List<UcenterUsertype> getall() {
        return ucenterUsertypeMapper.getall();
    }
}
