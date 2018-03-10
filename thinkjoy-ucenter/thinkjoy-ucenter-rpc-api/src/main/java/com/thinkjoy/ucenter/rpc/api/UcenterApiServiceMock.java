package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;

import java.util.List;

/**
 * Created by xufei on 2017/8/14.
 */
public class UcenterApiServiceMock implements UcenterApiService {
    @Override
    public List<UcenterUser> selectUcenterUsersByExample(UcenterUserExample ucenterUserExample) {
        return null;
    }

    @Override
    public UcenterUser selectUenterUserByUsername(String username) {
        return null;
    }
}
