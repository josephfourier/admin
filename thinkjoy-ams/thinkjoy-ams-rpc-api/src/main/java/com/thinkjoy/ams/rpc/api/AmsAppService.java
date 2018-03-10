package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsAppExample;

import java.util.List;
import java.util.Map;

/**
 * AmsAppService接口
 * Created by shuzheng on 2017/7/27.
 */
public interface AmsAppService extends BaseService<AmsApp, AmsAppExample> {
    /**
     * 根据relationCode/usertypeId/isPersonal获取该学校所属项目下的非个性化应用
     *
     * @param map
     * @return
     */
    List<AmsApp> getAppsByAgencyOrSchoolCode(Map<String, String> map);

    /**
     * 根据relationCode/usertypeId/isPersonal/userId
     * 获取该学校所属项目下的非个性化应用以及分给制定用户的个性化应用
     *
     * @param map
     * @return
     */
    List<AmsApp> getUserAppByAgencyOrSchoolCode(Map<String, String> map);

    int userapp(List<String> appIds, Map<String, Object> userIdentiry);

    /**
     * 根据appId获取对应应用是否是个性化应用
     * @param appId
     * @return
     */
    AmsApp getIsPersonalByClientId(String appId);
}