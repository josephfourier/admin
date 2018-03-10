package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsApprolePermissionMapper;
import com.thinkjoy.ams.dao.model.AmsApprolePermission;
import com.thinkjoy.ams.dao.model.AmsApprolePermissionExample;
import com.thinkjoy.ams.rpc.api.AmsApprolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* AmsApprolePermissionService实现
* Created by user on 2017/9/18.
*/
@Service
@Transactional
@BaseService
public class AmsApprolePermissionServiceImpl extends BaseServiceImpl<AmsApprolePermissionMapper, AmsApprolePermission, AmsApprolePermissionExample> implements AmsApprolePermissionService {

    private static Logger _log = LoggerFactory.getLogger(AmsApprolePermissionServiceImpl.class);

    @Autowired
    AmsApprolePermissionMapper amsApprolePermissionMapper;

    @Override
    public int rolePermission(JSONArray datas, int id) {
        List<Integer> deleteIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (!json.getBoolean("checked")) {
                deleteIds.add(json.getIntValue("id"));
            } else {
                // 新增权限
                AmsApprolePermission amsApprolePermission = new AmsApprolePermission();
                amsApprolePermission.setApproleId(id);
                amsApprolePermission.setPermissionId(json.getIntValue("id"));
                amsApprolePermissionMapper.insertSelective(amsApprolePermission);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
            AmsApprolePermissionExample amsApprolePermissionExample = new AmsApprolePermissionExample();
            amsApprolePermissionExample.createCriteria()
                    .andPermissionIdIn(deleteIds)
                    .andApproleIdEqualTo(id);
            amsApprolePermissionMapper.deleteByExample(amsApprolePermissionExample);
        }
        return datas.size();
    }

}