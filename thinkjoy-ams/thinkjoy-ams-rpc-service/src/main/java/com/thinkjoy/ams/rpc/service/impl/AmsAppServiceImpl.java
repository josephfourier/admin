package com.thinkjoy.ams.rpc.service.impl;

import com.thinkjoy.ams.dao.mapper.AmsPerprojectAppMapper;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsAppMapper;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import com.thinkjoy.ucenter.rpc.api.UcenterUserIdentityService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * AmsAppService实现
 * Created by shuzheng on 2017/7/27.
 */
@Service
@Transactional
@BaseService
public class AmsAppServiceImpl extends BaseServiceImpl<AmsAppMapper, AmsApp, AmsAppExample> implements AmsAppService {

    private static Logger _log = LoggerFactory.getLogger(AmsAppServiceImpl.class);

    @Autowired
    private AmsAppMapper amsAppMapper;

    @Autowired
    private AmsPerprojectAppMapper amsPerprojectAppMapper;

    @Override
    public List<AmsApp> getAppsByAgencyOrSchoolCode(Map<String, String> map) {
        return amsAppMapper.getAppsByAgencyOrSchoolCode(map);
    }

    @Override
    public List<AmsApp> getUserAppByAgencyOrSchoolCode(Map<String, String> map) {
        return amsAppMapper.getUserAppByAgencyOrSchoolCode(map);
    }

    @Override
    public int userapp(List<String> appIds, Map<String, Object> userIdentiry) {
        //应用id为空则表示用户没有进行应用个性化,或者取消应用个性化
        if (CollectionUtils.isEmpty(appIds)) {
            userIdentiry.put("personalization", BaseConstants.PerPersonal.NO);

        } else {
            //将身份表中的用户应用个性化标识置为1
            userIdentiry.put("personalization", BaseConstants.PerPersonal.YES);
        }
        amsPerprojectAppMapper.updatePersonal(userIdentiry);

        //删除旧记录
        AmsPerprojectAppExample amsPerprojectAppExample = new AmsPerprojectAppExample();
        amsPerprojectAppExample.createCriteria()
                .andUserIdEqualTo((Integer) userIdentiry.get("userId"))
                .andUsertypeIdEqualTo((Integer) userIdentiry.get("usertypeId"))
                .andRelationCodeEqualTo((String) userIdentiry.get("relationCode"));
        amsPerprojectAppMapper.deleteByExample(amsPerprojectAppExample);

        //新增新纪录
        for (String appId : appIds) {
            AmsPerprojectApp amsPerprojectApp = new AmsPerprojectApp();
            amsPerprojectApp.setUserId((Integer) userIdentiry.get("userId"));
            amsPerprojectApp.setUsertypeId((Integer) userIdentiry.get("usertypeId"));
            amsPerprojectApp.setRelationCode((String) userIdentiry.get("relationCode"));
            amsPerprojectApp.setAppId(Integer.parseInt(appId));

            amsPerprojectAppMapper.insert(amsPerprojectApp);
        }
        return appIds.size();
    }

    @Override
    public AmsApp getIsPersonalByClientId(String appId) {
        return amsAppMapper.getIsPersonalByClientId(appId);
    }


}