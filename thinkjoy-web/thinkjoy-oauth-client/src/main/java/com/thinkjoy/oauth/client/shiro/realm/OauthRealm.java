package com.thinkjoy.oauth.client.shiro.realm;

import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.*;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.PropertiesUtil;
import com.thinkjoy.common.util.RedisUtil;
import com.thinkjoy.common.web.ServletUtil;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.rpc.api.UcenterApiService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户认证和授权
 * Created by shuzheng on 2017/1/20.
 */
public class OauthRealm extends AuthorizingRealm {

    private static Logger _log = LoggerFactory.getLogger(OauthRealm.class);


    @Autowired
    private UcenterApiService ucenterApiService;

    @Autowired
    private AmsApproleService amsApproleService;

    @Autowired
    private AmsApppermissionService amsApppermissionService;

    @Autowired
    private AmsAppService amsAppService;

    /**
     * 授权：验证权限时调用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String webType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.type");

        Set<String> permissions = new HashSet<>();
        Set<String> roles = new HashSet<>();

        if ("client".equals(webType)) {

            //从reqest的session中获取这些信息
            HttpServletRequest httpServletRequest = WebUtils.toHttp(ServletUtil.getRequest());
            HttpSession session = httpServletRequest.getSession();

            //首先判断缓存中有没有角色与权限
            List<AmsApprole> cachedRoles = (List<AmsApprole>) session.getAttribute("roles");
            List<AmsApppermission> cachedPermissions = (List<AmsApppermission>) session.getAttribute("permissions");

            //不为空则直接抽取信息
            if (CollectionUtils.isNotEmpty(cachedRoles) && CollectionUtils.isNotEmpty(cachedPermissions)) {
                //抽取角色和权限
                extractRole(cachedRoles, roles, true);
                extractPermission(cachedPermissions, permissions);
            } else {

                String perpersonalization = (String) session.getAttribute("perpersonalization");
                String userType = (String) session.getAttribute("userType");
                String relationCode = (String) session.getAttribute("relationCode");
                String userId = (String) session.getAttribute("userId");

                if ("null".equals(userType) ||
                        "null".equals(relationCode) ||
                        "null".equals(perpersonalization) ||
                        "null".equals(userId) ||
                        StringUtils.isBlank(userType) ||
                        StringUtils.isBlank(relationCode) ||
                        StringUtils.isBlank(userId) ||
                        StringUtils.isBlank(perpersonalization)) {
                    _log.error("子系统登录参数异常");
                    throw new RuntimeException("子系统登录参数异常!");
                }

                String appId = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.appID");
                if (StringUtils.isBlank(appId)) {
                    _log.error("zk中没有配置thinkjoy.web.appID");
                    throw new BusindessException("zk中没有配置thinkjoy.web.appID!");
                }

                //①首先判断当前应用是否是个性化
                //  1,是个性化应用,则判断个性化角色是否开启
                //      a,是个性化角色,则在个性化角色表中加载权限
                //      b,不是个性化角色,则表示没有进行个性化角色配置,或者根本就没有该应用的个性化角色
                //      注:如果是个性化角色,则一定是选择了该应用对应的个性化角色,注意是对应,表示个性化角色不存在不选应用这种情况,
                //         如果存在,在分配权限页面的应用于角色树中也不会显示出来,所以个性化角色只对应单独应用,不存在默认的个性化配置
                //  2,不是个性化应用,则直接在ams_approle中查找角色
                //      a,ams_approle中有该应用的非个性化角色,表示对于非个性化应用,单独进行权限配置(表示所有有该应用的用户都有该角色)
                //      b,ams_approle中没有该应用的非个性化角色,表示没有进行单独配置,查看是否有没有配置应用的非个性化角色(所有应用的权限都在该角色中)
                //②加载角色到shiro中
                //③加载权限到shiro中


                List<AmsApprole> amsApproles = null;

                AmsApp curApp = amsAppService.getIsPersonalByClientId(appId);

                if (StringUtils.isBlank(curApp.getIsPersonalization())) {
                    _log.error("获取应用个性化标志失败");
                    throw new BusindessException("获取应用个性化标志失败!");
                } else {
                    if (BaseConstants.PerPersonal.YES.equals(curApp.getIsPersonalization())) {
                        if (BaseConstants.PerPersonal.YES.equals(perpersonalization)) {
                            HashMap<String, Object> params = new HashMap<>();
                            params.put("appId", curApp.getAppId());
                            params.put("userId", userId);
                            params.put("usertypeId", userType);
                            params.put("relationCode", relationCode);
                            amsApproles = amsApproleService.getAmsApproleByIdentityAndAppId(params);
                        } else {
                            //一定没有权限
                        }
                    } else {
                        //非个性化应用的角色权限,可以单独为每个应用配置,
                        //也可以使用一个不关联应用的角色,该角色可以关联所有可用的权限
                        AmsApproleExample amsApproleExample = new AmsApproleExample();
                        amsApproleExample.createCriteria()
                                .andRelationCodeEqualTo(relationCode)
                                .andUsertypeIdEqualTo(Integer.parseInt(userType))
                                .andStatusEqualTo(BaseConstants.Status.NORMAL)
                                .andAppIdEqualTo(curApp.getAppId())
                                .andPerPersonalizationEqualTo(curApp.getIsPersonalization());
                        amsApproles = amsApproleService.selectByExample(amsApproleExample);

                        if (CollectionUtils.isEmpty(amsApproles)) {
                            amsApproleExample.clear();
                            amsApproleExample.createCriteria()
                                    .andRelationCodeEqualTo(relationCode)
                                    .andUsertypeIdEqualTo(Integer.parseInt(userType))
                                    .andStatusEqualTo(BaseConstants.Status.NORMAL)
                                    .andPerPersonalizationEqualTo(curApp.getIsPersonalization());
                            amsApproles = amsApproleService.selectByExample(amsApproleExample);
                        }
                    }

                }

                //抽取角色和权限
                List<AmsApppermission> amsApppermissions = extractRole(amsApproles, roles, false);
                extractPermission(amsApppermissions, permissions);

                //存入session中
                session.setAttribute("roles", amsApproles);
                session.setAttribute("permissions", amsApppermissions);

            }
            simpleAuthorizationInfo.setStringPermissions(permissions);
            simpleAuthorizationInfo.setRoles(roles);
        }

        return simpleAuthorizationInfo;
    }


    public List<AmsApppermission> extractRole(List<AmsApprole> amsApproles, Set<String> roles, Boolean cached) {
        Set<Integer> ids = new HashSet<>();
        if (CollectionUtils.isNotEmpty(amsApproles)) {
            for (AmsApprole approle : amsApproles) {
                ids.add(approle.getApproleId());
                if (StringUtils.isNotBlank(approle.getApproleName())) {
                    roles.add(approle.getApproleName());
                }
            }
        }

        List<AmsApppermission> appPermissions = null;
        if (!ids.isEmpty() && !cached) {
            appPermissions = amsApppermissionService.getAppPermissionsByApproleId(ids);
        }
        return appPermissions;
    }

    public void extractPermission(List<AmsApppermission> apppermissions, Set<String> permissions) {
        if (CollectionUtils.isNotEmpty(apppermissions)) {
            for (AmsApppermission amsApppermission : apppermissions) {
                if (StringUtils.isNotBlank(amsApppermission.getPermissionValue())) {
                    permissions.add(amsApppermission.getPermissionValue());
                }
            }
        }
    }


    /**
     * 认证：登录时调用
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // client无密认证
        String webType = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.web.type");
        if ("client".equals(webType)) {
            return new SimpleAuthenticationInfo(username, password, getName());
        }

        // 查询用户信息
        UcenterUser ucenterUser = ucenterApiService.selectUenterUserByUsername(username);

        if (null == ucenterUser) {
            throw new UnknownAccountException();
        }
        if (!ucenterUser.getPassword().equals(AESUtil.AESEncode(password))) {
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }


}
