package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;
import com.thinkjoy.ucenter.rpc.api.UcenterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xufei on 2017/8/14.
 */
@Service
@Transactional
public class UcenterApiServiceImpl implements UcenterApiService{
    @Autowired
    private UcenterUserMapper ucenterUserMapper;

    @Override
    public List<UcenterUser> selectUcenterUsersByExample(UcenterUserExample ucenterUserExample) {
        return ucenterUserMapper.selectByExample(ucenterUserExample);
    }

    @Override
    public UcenterUser selectUenterUserByUsername(String username) {
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        ucenterUserExample.createCriteria()
                .andUsernameEqualTo(username)
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<UcenterUser> ucenterUsers=ucenterUserMapper.selectByExample(ucenterUserExample);
        if(null!=ucenterUsers &&ucenterUsers.size()>0){
            return ucenterUsers.get(0);
        }
        return null;
    }
}
