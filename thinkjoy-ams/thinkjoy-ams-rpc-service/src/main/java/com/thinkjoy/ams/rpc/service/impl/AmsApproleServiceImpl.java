package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.ams.dao.mapper.AmsPerprojectAppMapper;
import com.thinkjoy.ams.dao.mapper.AmsUserApproleMapper;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsApproleMapper;
import com.thinkjoy.ams.rpc.api.AmsApproleService;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AmsApproleService实现
 * Created by user on 2017/9/18.
 */
@Service
@Transactional
@BaseService
public class AmsApproleServiceImpl extends BaseServiceImpl<AmsApproleMapper, AmsApprole, AmsApproleExample> implements AmsApproleService {

    private static Logger _log = LoggerFactory.getLogger(AmsApproleServiceImpl.class);

    @Autowired
    AmsApproleMapper amsApproleMapper;

    @Autowired
    AmsUserApproleMapper amsUserApproleMapper;

    @Autowired
    AmsPerprojectAppMapper amsPerprojectAppMapper;

    @Autowired
    AmsAppService amsAppService;

    @Override
    public List<String> getAmsApproleRelatedCodes(Integer id) {

        List<String> codes = new ArrayList<>();

        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria().andApproleIdEqualTo(id);

        List<AmsApprole> amsApproles = amsApproleMapper.selectByExample(amsApproleExample);

        for (AmsApprole r : amsApproles) {
            codes.add(r.getRelationCode());
        }

        return codes;
    }

    @Override
    public List<Integer> getAmsAppIdByRoleCode(Map<String, String> map) {
        return amsApproleMapper.getAmsAppIdByRoleCode(map);
    }

    @Override
    public int role(List<String> appIds, List<String> roleIds, Map<String, Object> userIdentiry) {

        int count1 = amsAppService.userapp(appIds, userIdentiry);

        //角色id为空则表示用户没有进行应用权限个性化,或者取消应用权限个性化
        if (CollectionUtils.isEmpty(roleIds)) {
            userIdentiry.put("perPersonalization", BaseConstants.PerPersonal.NO);
        } else {
            //将身份表中的用户应用权限个性化标识置为1
            userIdentiry.put("perPersonalization", BaseConstants.PerPersonal.YES);
        }
        //将身份表中的用户应用权限个性化标识置为1
        amsApproleMapper.updatePerpersonal(userIdentiry);

        //删除旧记录
        AmsUserApproleExample amsUserApproleExample = new AmsUserApproleExample();
        amsUserApproleExample.createCriteria()
                .andUserIdEqualTo((Integer) userIdentiry.get("userId"))
                .andUsertypeIdEqualTo((Integer) userIdentiry.get("usertypeId"))
                .andRelationCodeEqualTo((String) userIdentiry.get("relationCode"));
        amsUserApproleMapper.deleteByExample(amsUserApproleExample);
        //新增新纪录
        for (String roleId : roleIds) {
            AmsUserApprole amsUserApprole = new AmsUserApprole();
            amsUserApprole.setUserId((Integer) userIdentiry.get("userId"));
            amsUserApprole.setUsertypeId((Integer) userIdentiry.get("usertypeId"));
            amsUserApprole.setRelationCode((String) userIdentiry.get("relationCode"));
            amsUserApprole.setApproleId(Integer.parseInt(roleId));
            amsUserApproleMapper.insertSelective(amsUserApprole);
        }
        return roleIds.size();
    }

    @Override
    public List<AmsApprole> getAppRolesByIdentity(Integer userId, Integer userType, String relationCode) {
        return amsApproleMapper.getAppRolesByIdentity(userId, userType, relationCode);
    }

    @Override
    public int updatePerpersonal(Map map) {
        return amsApproleMapper.updatePerpersonal(map);
    }

    @Override
    public List<AmsApprole> getAmsApproleByIdentityAndAppId(Map map) {
        return amsApproleMapper.getAmsApproleByIdentityAndAppId(map);
    }


}