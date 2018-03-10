package com.thinkjoy.ams.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsApprole;
import com.thinkjoy.ams.dao.model.AmsApproleExample;

import java.util.List;
import java.util.Map;

/**
* AmsApproleService接口
* Created by user on 2017/9/18.
*/
public interface AmsApproleService extends BaseService<AmsApprole, AmsApproleExample> {
    List<String> getAmsApproleRelatedCodes(Integer id);

    List<Integer> getAmsAppIdByRoleCode(Map<String, String> map);

    /**
     * 给用户身份分配个性化的应用以及应用所对应的角色
     * @param appIds
     * @param roleIds
     * @param userIdentiry
     * @return
     */
    int role(List<String> appIds, List<String> roleIds, Map<String, Object> userIdentiry);

    List<AmsApprole> getAppRolesByIdentity(Integer userId,
                                           Integer userType,
                                           String relationCode);


    int updatePerpersonal(Map map);

    /**
     * 根据用户身份与应用id获取该用户所关联的角色
     * @param map
     * @return
     */
    List<AmsApprole> getAmsApproleByIdentityAndAppId(Map map);

}