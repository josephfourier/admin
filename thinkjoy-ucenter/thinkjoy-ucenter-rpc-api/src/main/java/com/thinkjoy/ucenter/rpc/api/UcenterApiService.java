package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;

import java.util.List;

/**
 * 用户中心操作接口
 * Created by xufei on 2017/8/14.
 */
public interface UcenterApiService {

    /**
     * 根据条件获取用户数据
     * @param ucenterUserExample
     * @return
     */
    List<UcenterUser> selectUcenterUsersByExample(UcenterUserExample ucenterUserExample);

    UcenterUser  selectUenterUserByUsername(String username);
}
