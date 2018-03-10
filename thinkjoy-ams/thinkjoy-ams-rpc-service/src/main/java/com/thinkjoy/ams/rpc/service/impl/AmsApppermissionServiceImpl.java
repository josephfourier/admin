package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.ams.dao.mapper.AmsAppMapper;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.AmsApprolePermissionService;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsApppermissionMapper;
import com.thinkjoy.ams.rpc.api.AmsApppermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* AmsApppermissionService实现
*
 * @author user
 * @date 2017/9/18
 */
@Service
@Transactional
@BaseService
public class AmsApppermissionServiceImpl extends BaseServiceImpl<AmsApppermissionMapper, AmsApppermission, AmsApppermissionExample> implements AmsApppermissionService {

    private static Logger _log = LoggerFactory.getLogger(AmsApppermissionServiceImpl.class);

    @Autowired
    AmsApppermissionMapper amsApppermissionMapper;

    @Autowired
    AmsAppMapper amsAppMapper;

    @Autowired
    AmsApprolePermissionService amsApprolePermissionService;

    @Override
    public JSONArray getTreeByApproleId(Integer approleId) {
        // 根据角色id查角色对应已分配的权限
        //List<UpmsRolePermission> rolePermissions = upmsApiService.selectUpmsRolePermisstionByUpmsRoleId(roleId);


        AmsApprolePermissionExample amsApprolePermissionExample = new AmsApprolePermissionExample();
        amsApprolePermissionExample.createCriteria().andApproleIdEqualTo(approleId);
        List<AmsApprolePermission> amsApprolePermissions = amsApprolePermissionService.selectByExample(amsApprolePermissionExample);

        JSONArray systems = new JSONArray();
        // 获取系统
        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria()
                .andStatusEqualTo("1");
        amsAppExample.setOrderByClause("orders asc");
        //查询所有启用系统
        List<AmsApp> amsApps = amsAppMapper.selectByExample(amsAppExample);

        //权限-系统列表
        AmsApppermissionExample amsApppermissionExample = new AmsApppermissionExample();
        amsApppermissionExample.createCriteria()
                .andStatusEqualTo((byte) 1).andTypeEqualTo((byte)0);
        amsApppermissionExample.setOrderByClause("orders asc");
        List<AmsApppermission> amsApppermissions = amsApppermissionMapper.selectByExample(amsApppermissionExample);

        for (AmsApp amsApp : amsApps) {
            JSONObject node = new JSONObject();
            for (AmsApppermission amsApppermission: amsApppermissions) {
                if(amsApppermission.getSystemId()==amsApp.getAppId()){
                    node.put("id", amsApppermission.getPermissionId());
                    node.put("systemid", amsApp.getAppId());
                    node.put("name", amsApppermission.getName());
                    node.put("open", true);
                    for (AmsApprolePermission rolePermission : amsApprolePermissions) {
                        if (rolePermission.getPermissionId().intValue() == amsApppermission.getPermissionId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    systems.add(node);
                }
            }
        }


        if (systems.size() > 0) {
            for (Object system: systems) {
                AmsApppermissionExample amsPermissionExample = new AmsApppermissionExample();
                amsPermissionExample.createCriteria()
                        .andStatusEqualTo((byte) 1)
                        .andSystemIdEqualTo(((JSONObject) system).getIntValue("systemid"));
                amsPermissionExample.setOrderByClause("orders asc");
                List<AmsApppermission> amsPermissions = amsApppermissionMapper.selectByExample(amsPermissionExample);
                if (amsPermissions.size() == 0) continue;
                // 目录
                JSONArray folders = new JSONArray();
                for (AmsApppermission amsPermission: amsPermissions) {
                    if (amsPermission.getPid().intValue() != 0 || amsPermission.getType() != 1) continue;
                    JSONObject node = new JSONObject();
                    node.put("id", amsPermission.getPermissionId());
                    node.put("name", amsPermission.getName());
                    node.put("open", true);
                    for (AmsApprolePermission rolePermission : amsApprolePermissions) {
                        if (rolePermission.getPermissionId().intValue() == amsPermission.getPermissionId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    folders.add(node);
                    // 菜单
                    JSONArray menus = new JSONArray();
                    for (Object folder : folders) {
                        for (AmsApppermission amsPermission2: amsPermissions) {
                            if (amsPermission2.getPid().intValue() != ((JSONObject) folder).getIntValue("id") || amsPermission2.getType() != 2) continue;
                            JSONObject node2 = new JSONObject();
                            node2.put("id", amsPermission2.getPermissionId());
                            node2.put("name", amsPermission2.getName());
                            node2.put("open", true);
                            for (AmsApprolePermission rolePermission : amsApprolePermissions) {
                                if (rolePermission.getPermissionId().intValue() == amsPermission2.getPermissionId().intValue()) {
                                    node2.put("checked", true);
                                }
                            }
                            menus.add(node2);
                            // 按钮
                            JSONArray buttons = new JSONArray();
                            for (Object menu : menus) {
                                for (AmsApppermission amsPermission3: amsPermissions) {
                                    if (amsPermission3.getPid().intValue() != ((JSONObject) menu).getIntValue("id") || amsPermission3.getType() != 3) continue;
                                    JSONObject node3 = new JSONObject();
                                    node3.put("id", amsPermission3.getPermissionId());
                                    node3.put("name", amsPermission3.getName());
                                    node3.put("open", true);
                                    for (AmsApprolePermission rolePermission : amsApprolePermissions) {
                                        if (rolePermission.getPermissionId().intValue() == amsPermission3.getPermissionId().intValue()) {
                                            node3.put("checked", true);
                                        }
                                    }
                                    buttons.add(node3);
                                }
                                if (buttons.size() > 0) {
                                    ((JSONObject) menu).put("children", buttons);
                                    buttons = new JSONArray();
                                }
                            }
                        }
                        if (menus.size() > 0) {
                            ((JSONObject) folder).put("children", menus);
                            menus = new JSONArray();
                        }
                    }
                }
                if (folders.size() > 0) {
                    ((JSONObject) system).put("children", folders);
                }
            }
        }
        return systems;
    }

    @Override
    public List<AmsApppermission> getAppPermissionByRelationCodeAndUserType(Map<String, String> map) {
        return mapper.getAppPermissionByRelationCodeAndUserType(map);
    }

    @Override
    public List<AmsApppermission> getAppPermissionsByApproleId(Set<Integer> set) {
        return amsApppermissionMapper.getAppPermissionsByApproleId(set);
    }
}