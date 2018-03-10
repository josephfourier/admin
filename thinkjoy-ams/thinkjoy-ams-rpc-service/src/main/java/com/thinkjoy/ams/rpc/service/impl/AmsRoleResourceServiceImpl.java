package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsRoleResourceMapper;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.ams.dao.model.AmsRoleResourceExample;
import com.thinkjoy.ams.rpc.api.AmsRoleResourceService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* AmsRoleResourceService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class AmsRoleResourceServiceImpl extends BaseServiceImpl<AmsRoleResourceMapper, AmsRoleResource, AmsRoleResourceExample> implements AmsRoleResourceService {

    private static Logger _log = LoggerFactory.getLogger(AmsRoleResourceServiceImpl.class);

    @Autowired
    AmsRoleResourceMapper amsRoleResourceMapper;

    @Override
    public int addRoleResource(JSONArray array, String roleId) {

        List<Integer> deleteCodes = new ArrayList<>();

        for (int i = 0; i < array.size(); i++){
            JSONObject jsonObject = array.getJSONObject(i);
            if (!jsonObject.getBoolean("checked")){
                //过滤取消的节点
                deleteCodes.add(jsonObject.getInteger("resourceId"));
            }else {
                //新增添加的节点
                AmsRoleResource amsRoleResource = new AmsRoleResource();
                amsRoleResource.setRoleId(Integer.parseInt(roleId));
                amsRoleResource.setResourceId(jsonObject.getInteger("resourceId"));
                amsRoleResourceMapper.insertSelective(amsRoleResource);
            }

        }

        //删除取消的节点
        if (!deleteCodes.isEmpty()){
            AmsRoleResourceExample amsRoleResourceExample = new AmsRoleResourceExample();
            amsRoleResourceExample.createCriteria()
                    .andRoleIdEqualTo(Integer.parseInt(roleId))
                    .andResourceIdIn(deleteCodes);
            amsRoleResourceMapper.deleteByExample(amsRoleResourceExample);
        }


        return array.size();
    }
}