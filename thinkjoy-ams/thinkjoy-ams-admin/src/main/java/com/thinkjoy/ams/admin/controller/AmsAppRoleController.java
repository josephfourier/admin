package com.thinkjoy.ams.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.*;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserIdentityService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * 角色控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "应用角色管理", description = "应用角色管理")
@Controller
@RequestMapping("/manage/approle")
public class AmsAppRoleController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(AmsAppRoleController.class);

    @Autowired
    private AmsApproleService amsApproleService;

    @Autowired
    private UcenterAgencyService ucenterAgencyService;

    @Autowired
    private AmsApppermissionService amsApppermissionService;

    @Autowired
    private AmsApprolePermissionService amsApprolePermissionService;

    @Autowired
    private UpmsApiService upmsApiService;

    @Autowired
    private UcenterUserService ucenterUserService;

    @Autowired
    private UcenterUserIdentityService ucenterUserIdentityService;

    @Autowired
    private AmsAppService amsAppService;

    @Autowired
    private AmsPerprojectAppService amsPerprojectAppService;

    @Autowired
    private AmsUserApproleService amsUserApproleService;

    @ApiOperation(value = "角色首页")
    @RequiresPermissions("ams:approle:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("upmsUser", getCurrentUpmsUser());
        return "/manage/approle/index.jsp";
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("ams:approle:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        return "/manage/approle/create.jsp";
    }

    @ApiOperation(value = "新增角色")
    @RequiresPermissions("ams:approle:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@Valid AmsApprole amsApprole, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria()
                .andApproleNameEqualTo(amsApprole.getApproleName())
                .andRelationCodeEqualTo(amsApprole.getRelationCode());
        int existCount = amsApproleService.countByExample(amsApproleExample);
        if (existCount > 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用角色名称重复!"));
        }

        amsApprole.setCreator(UserUtil.getCurrentUser());
        amsApprole.setCtime(System.currentTimeMillis());
        int count = amsApproleService.insertSelective(amsApprole);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除角色")
    @RequiresPermissions("ams:approle:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = amsApproleService.deleteByIds(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("ams:approle:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") String id, Model model) {
        AmsApprole amsApprole = amsApproleService.selectByPrimaryKey(Integer.parseInt(id));
        model.addAttribute("amsApprole", amsApprole);
        return "/manage/approle/update.jsp";
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("ams:approle:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") String id, @Valid AmsApprole amsApprole, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //当没有选择应用的时候,或者取消应用选择,则将appName也置空
        if (amsApprole.getAppId() == null) {
            amsApprole.setAppName(null);
        }

        //字段重复校验
        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria()
                .andApproleNameEqualTo(amsApprole.getApproleName())
                .andRelationCodeEqualTo(amsApprole.getRelationCode())
                .andApproleIdNotEqualTo(Integer.parseInt(id));
        int existCount = amsApproleService.countByExample(amsApproleExample);
        if (existCount > 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用角色名称重复!"));
        }

        amsApprole.setApproleId(Integer.parseInt(id));
        int count = amsApproleService.updateByPrimaryKeySelective(amsApprole);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "用户基本信息首页")
    @RequiresPermissions("ams:user:read")
    @RequestMapping(value = "/ucenteruser/index", method = RequestMethod.GET)
    public String index(ModelMap model) {
        //获取当前登录用户
        UpmsUser upmsUser = getCurrentUpmsUser();
        model.addAttribute("upmsUser", upmsUser);
        return "/manage/approle/user/index.jsp";
    }


    @ApiOperation(value = "用户基本信息列表")
    @RequiresPermissions("ams:user:read")
    @RequestMapping(value = "/ucenteruser/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "", value = "serarch_fullname") String serarch_fullname,
            @RequestParam(required = false, defaultValue = "", value = "search_usertype") String search_usertype,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order,
            @RequestParam(required = false, defaultValue = "", value = "relationCode") String relationCode) {

        UpmsUser currentUpmsUser = getCurrentUpmsUser();

        Map<String, Object> params = new HashMap();
        List<String> schoolAgencyAndChildsCodes = new ArrayList<>();
        List<UcenterAgency> ucenterAgencies = new ArrayList<>();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            params.put("orderByClause", sort + " " + order);
        }

        if (!StringUtils.isBlank(search)) {
            params.put("search", search);
        }
        if (StringUtils.isNotBlank(serarch_fullname)) {
            params.put("search", "'%" + serarch_fullname + "%'");
        }
        if (StringUtils.isNotBlank(search_usertype)) {
            params.put("usertype", search_usertype);
        }
        params.put("offset", offset + "");
        params.put("limit", limit + "");

        //为空表示还没有选择节点的初始化过程
        if (StringUtils.isBlank(relationCode)) {
            if (currentUpmsUser.getManagerType().equals(BaseConstants.ManagerType.SCHMANAGER)) {
                schoolAgencyAndChildsCodes.add(currentUpmsUser.getRelationCode());
            } else if (currentUpmsUser.getManagerType().equals(BaseConstants.ManagerType.AREAMANAGER)) {
                UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
                ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(currentUpmsUser.getRelationCode());
                ucenterAgencies = ucenterAgencyService.selectByExample(ucenterAgencyExample);
                schoolAgencyAndChildsCodes = ucenterAgencyService.getSchoolAgencyAndChilds(ucenterAgencies);
            } else {
                schoolAgencyAndChildsCodes = null;
            }
            params.put("codes", schoolAgencyAndChildsCodes);
        } else {
            UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
            ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(relationCode);
            ucenterAgencies = ucenterAgencyService.selectByExample(ucenterAgencyExample);
            //没查询到则证明是学校code
            if (CollectionUtils.isEmpty(ucenterAgencies)) {
                schoolAgencyAndChildsCodes.add(relationCode);
            } else {
                schoolAgencyAndChildsCodes = ucenterAgencyService.getSchoolAgencyAndChilds(ucenterAgencies);
            }
            params.put("codes", schoolAgencyAndChildsCodes);
        }

        List<UcenterUser> rows = ucenterUserService.selectByIdentityRelationCodeForOffsetPage(params);
        long total = ucenterUserService.countByIdentityRelationCodeForOffsetPage(params);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "个性化设置回显")
    @RequestMapping(value = "/ucenteruser/getuserpersonal", method = RequestMethod.GET)
    @ResponseBody
    public Object get(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "-1", value = "userId") int userId,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") int usertypeId) {

        //获取用户个性化关联的应用角色
        AmsUserApproleExample amsUserApproleExample = new AmsUserApproleExample();
        amsUserApproleExample.createCriteria()
                .andRelationCodeEqualTo(relationCode)
                .andUsertypeIdEqualTo(usertypeId)
                .andUserIdEqualTo(userId);

        StringBuilder appids = new StringBuilder();
        StringBuilder approles = new StringBuilder();
        List<AmsUserApprole> amsUserApproles = amsUserApproleService.selectByExample(amsUserApproleExample);
        if (CollectionUtils.isNotEmpty(amsUserApproles)) {
            for (AmsUserApprole amsUserApprole : amsUserApproles) {
                approles.append(amsUserApprole.getApproleId());
                approles.append(",");
            }
        }
        //获取用户个性化关联的应用
        AmsPerprojectAppExample amsPerprojectAppExample = new AmsPerprojectAppExample();
        amsPerprojectAppExample.createCriteria()
                .andRelationCodeEqualTo(relationCode)
                .andUserIdEqualTo(userId)
                .andUsertypeIdEqualTo(usertypeId);
        List<AmsPerprojectApp> amsPerprojectApps = amsPerprojectAppService.selectByExample(amsPerprojectAppExample);
        if (CollectionUtils.isNotEmpty(amsPerprojectApps)) {
            for (AmsPerprojectApp amsPerprojectApp : amsPerprojectApps) {
                appids.append(amsPerprojectApp.getAppId());
                appids.append(",");
            }
        }

        List<String> res = new ArrayList<>();
        res.add(appids.toString());
        res.add(approles.toString());
        return new AmsResult(AmsResultConstant.SUCCESS, res);
    }


    @ApiOperation(value = "获取该用户在指定学校下的用户类型")
    @RequestMapping(value = "/ucenteruser/usertypelist", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserTypeByCodeAndUid(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "-1", value = "userId") int userId) {

        UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
        ucenterUserIdentityExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andRelationCodeEqualTo(relationCode);
        List<UcenterUserIdentity> ucenterUserIdentities = ucenterUserIdentityService.selectByExample(ucenterUserIdentityExample);

        return new AmsResult(AmsResultConstant.SUCCESS, ucenterUserIdentities);
    }

    @ApiOperation(value = "具体用户可配置的角色列表")
    @RequestMapping(value = "/ucenteruser/approle/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getAmsApproleByUserTypeAndCode(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
            @RequestParam(required = true, defaultValue = "", value = "appIds") String appIds) {

        if (StringUtils.isBlank(usertypeId)
                || StringUtils.isBlank(relationCode)
                || StringUtils.isBlank(appIds)
                || "null".equals(appIds)) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "参数为空");
        }
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(appIds)) {
                String[] split = appIds.split(",");

                if (split != null && split.length > 0) {
                    for (String id : split) {
                        ids.add(Integer.parseInt(id));
                    }
                }
            }
        } catch (NumberFormatException e) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "应用id格式不正确");
        }


        //根据用户所选身份的relationCode和userType查询角色中相应的学校或组织下的相应用户类型的角色
        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria()
                .andRelationCodeEqualTo(relationCode)
                .andUsertypeIdEqualTo(Integer.parseInt(usertypeId))
                .andStatusEqualTo(BaseConstants.Status.NORMAL)
                .andPerPersonalizationEqualTo(BaseConstants.PerPersonal.YES)
                .andAppIdIn(ids);
        amsApproleExample.setOrderByClause("app_id asc");
        List<AmsApprole> amsroles = amsApproleService.selectByExample(amsApproleExample);

        return new AmsResult(AmsResultConstant.SUCCESS, amsroles);
    }

    @ApiOperation(value = "分配角色")
    @RequiresPermissions("ams:user:read")
    @RequestMapping(value = "/ucenteruser/approle/{id}", method = RequestMethod.GET)
    public String role(@PathVariable("id") int id, ModelMap modelMap) {

        //该用户拥有的身份
        List<UcenterUserIdentity> ucenterUserIdentities = ucenterUserIdentityService.getUserIdentityRelationByUserId(id);
        modelMap.put("uid", id);
        modelMap.put("identities", ucenterUserIdentities);

        return "/manage/approle/user/approlelist.jsp";
    }

//    @ApiOperation(value = "分配角色")
//    @RequiresPermissions("ams:user:role")
//    @RequestMapping(value = "/ucenteruser/approle/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public Object role(
//            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
//            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
//            @PathVariable(required = true, value = "id") int id, HttpServletRequest request) {
//
//        String[] roleIds = request.getParameterValues("roleId");
//
//        if (StringUtils.isBlank(relationCode) || StringUtils.isBlank(usertypeId) || roleIds == null) {
//            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "");
//        }
//
//        Map<String, Object> userIdentiry = new HashMap<>();
//        userIdentiry.put("userId", id);
//        userIdentiry.put("usertypeId", Integer.parseInt(usertypeId));
//        userIdentiry.put("relationCode", relationCode);
//        int count = amsApproleService.role(roleIds, userIdentiry);
//
//        return new AmsResult(AmsResultConstant.SUCCESS, count);
//    }


    @ApiOperation(value = "分配应用")
    @RequiresPermissions("ams:user:read")
    @RequestMapping(value = "/ucenteruser/userapp/{ids}", method = RequestMethod.GET)
    public String userapp(@PathVariable("ids") String ids, ModelMap modelMap,HttpServletRequest request) {

        UpmsUser upmsUser = getCurrentUpmsUser();
        String usertype=request.getParameter("usertype");

        //该用户拥有的身份
//        List<UcenterUserIdentity> ucenterUserIdentities = ucenterUserIdentityService.getUserIdentityRelationByUserId(id);
//        modelMap.put("uid", id);
//        modelMap.put("identities", ucenterUserIdentities);

        modelMap.put("upmsUser",upmsUser );
        modelMap.put("usertype", usertype);

        modelMap.put("userIds", ids);

        return "/manage/approle/user/userapplist.jsp";
    }

    @ApiOperation(value = "分配应用")
    @RequiresPermissions("ams:user:role")
    @RequestMapping(value = "/ucenteruser/userapp/{ids}", method = RequestMethod.POST)
    @ResponseBody
    public Object userapp(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
            @PathVariable(required = true, value = "ids") String ids, HttpServletRequest request) {

        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));

        if (StringUtils.isBlank(relationCode) || StringUtils.isBlank(usertypeId)) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "");
        }

        List<String> appIds = new ArrayList<>();
        List<String> roleIds = new ArrayList<>();

        getAppIdsAndroleIds(datas, appIds, roleIds);

        Map<String, Object> userIdentiry = new HashMap<>();

        String arr[]= ids.split("-");

        int count=0;
        for(String obj:arr){
            userIdentiry.put("userId",Integer.parseInt(obj));
            userIdentiry.put("usertypeId", Integer.parseInt(usertypeId));
            userIdentiry.put("relationCode", relationCode);

            count += amsApproleService.role(appIds, roleIds, userIdentiry);

        }



        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    /**
     * 根据页面树结构,获取应用ids和应用角色ids
     * @param datas
     * @param appIds
     * @param roleIds
     */
    public void getAppIdsAndroleIds(JSONArray datas, List appIds, List roleIds){
        if (datas != null){
            for (int i = 0; i < datas.size(); i ++) {
                JSONObject json = datas.getJSONObject(i);
                if (json.getString("type").equals("1")){
                    appIds.add(json.getString("code"));
                }else {
                    roleIds.add(json.getString("code"));
                }
            }
        }
    }

    @ApiOperation(value = "恢复分配应用")
    @RequiresPermissions("ams:user:role")
    @RequestMapping(value = "/ucenteruser/recoveruserapp/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object recoveruserapp(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
            @PathVariable(required = true, value = "id") int id, HttpServletRequest request) {

        if (StringUtils.isBlank(relationCode) || StringUtils.isBlank(usertypeId)) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "");
        }

        Map<String, Object> userIdentiry = new HashMap<>();
        userIdentiry.put("userId", id);
        userIdentiry.put("usertypeId", Integer.parseInt(usertypeId));
        userIdentiry.put("relationCode", relationCode);

        //首先判断该身份是否有个性化设置
        AmsPerprojectAppExample amsPerprojectAppExample = new AmsPerprojectAppExample();
        amsPerprojectAppExample.createCriteria()
                .andUserIdEqualTo(id)
                .andUsertypeIdEqualTo(Integer.valueOf(usertypeId))
                .andRelationCodeEqualTo(relationCode);
        int i = amsPerprojectAppService.countByExample(amsPerprojectAppExample);

        if (i <= 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("该身份暂无应用个性化设置!"));
        }

        AmsUserApproleExample amsUserApproleExample = new AmsUserApproleExample();
        amsUserApproleExample.createCriteria()
                .andUserIdEqualTo(id)
                .andUsertypeIdEqualTo(Integer.valueOf(usertypeId))
                .andRelationCodeEqualTo(relationCode);
        int i1 = amsUserApproleService.countByExample(amsUserApproleExample);

        if (i1 <= 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("该身份暂无角色个性化设置!"));
        }

        userIdentiry.put("personalization", BaseConstants.PerPersonal.NO);
        //恢复身份个性化标志,包括应用个性化和权限个性化
        int count = amsPerprojectAppService.updatePersonal(userIdentiry);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

//    @ApiOperation(value = "恢复分配角色")
//    @RequiresPermissions("ams:user:role")
//    @RequestMapping(value = "/ucenteruser/recoveruserrole/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public Object recoveruserrole(
//            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
//            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
//            @PathVariable(required = true, value = "id") int id, HttpServletRequest request) {
//
//        if (StringUtils.isBlank(relationCode) || StringUtils.isBlank(usertypeId)) {
//            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, "");
//        }
//
//        Map<String, Object> userIdentiry = new HashMap<>();
//        userIdentiry.put("userId", id);
//        userIdentiry.put("usertypeId", Integer.parseInt(usertypeId));
//        userIdentiry.put("relationCode", relationCode);
//
//        AmsUserApproleExample amsUserApproleExample = new AmsUserApproleExample();
//        amsUserApproleExample.createCriteria()
//                .andUserIdEqualTo(id)
//                .andUsertypeIdEqualTo(Integer.valueOf(usertypeId))
//                .andRelationCodeEqualTo(relationCode);
//        int i = amsUserApproleService.countByExample(amsUserApproleExample);
//
//        if (i <= 0) {
//            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("该身份暂无角色个性化设置!"));
//        }
//
//        userIdentiry.put("perPersonalization", BaseConstants.PerPersonal.NO);
//        int count = amsApproleService.updatePerpersonal(userIdentiry);
//
//        return new AmsResult(AmsResultConstant.SUCCESS, count);
//    }

    @ApiOperation(value = "具体用户可配置的应用列表")
    @RequestMapping(value = "/ucenteruser/userapp/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getAmsAppByUserTypeAndCode(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
            @RequestParam(required = true, defaultValue = "", value = "perPer") String perPer) {

        if (StringUtils.isBlank(usertypeId)
                || StringUtils.isBlank(relationCode)
                || StringUtils.isBlank(perPer)) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, getErrorMsg("获取应用下拉列表的参数为空!"));
        }
        //根据用户所选身份的relationCode和userType查询该学校可用的个性化应用列表
        HashMap<String, String> param = new HashMap<>();
        param.put("relationCode", relationCode);
        param.put("usertypeId", usertypeId);
        param.put("isPersonalization", perPer);
        List<AmsApp> apps = amsAppService.getAppsByAgencyOrSchoolCode(param);

        return new AmsResult(AmsResultConstant.SUCCESS, apps);
    }

    @ApiOperation(value = "应用与角色树")
    @RequestMapping(value = "/getAppAndRoleTree", method = RequestMethod.POST)
    @ResponseBody
    public Object appAndRoleTree(
            @RequestParam(required = true, defaultValue = "", value = "relationCode") String relationCode,
            @RequestParam(required = true, defaultValue = "", value = "usertypeId") String usertypeId,
            @RequestParam(required = true, defaultValue = "", value = "userIds") String userIds) {

        if (StringUtils.isBlank(usertypeId)
                || StringUtils.isBlank(relationCode)
                ) {
            return new AmsResult(AmsResultConstant.INVALID_PARAMETER, getErrorMsg("获取应用与角色树的参数为空!"));
        }
        //userIdArr.length>1 表示批量身份分配角色,应用不能回填,userIdArr.length==1 单个身份角色查看功能

        //根据用户所选身份的relationCode和userType查询该学校可用的个性化应用列表
        HashMap<String, String> param = new HashMap<>();
        param.put("relationCode", relationCode);
        param.put("usertypeId", usertypeId);
        param.put("isPersonalization", BaseConstants.PerPersonal.YES);
        List<AmsApp> apps = amsAppService.getAppsByAgencyOrSchoolCode(param);

        //根据用户所选身份的relationCode和userType查询角色中相应的学校或组织下的相应用户类型的角色
        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria()
                .andRelationCodeEqualTo(relationCode)
                .andUsertypeIdEqualTo(Integer.parseInt(usertypeId))
                .andStatusEqualTo(BaseConstants.Status.NORMAL)
                .andPerPersonalizationEqualTo(BaseConstants.PerPersonal.YES);
        amsApproleExample.setOrderByClause("app_id asc");
        List<AmsApprole> amsroles = amsApproleService.selectByExample(amsApproleExample);


        List<AmsPerprojectApp> relatedApps = null;
        List<AmsUserApprole> relatedroles = null;
        if(userIds.indexOf("-")!=-1){
            //因为是多个身份所以,app应用角色不回填(批量角色分配)
        }else{
            //因为是单个个身份所以,app应用角色不回填(角色查询)
            //获取用户已经关联的应用列表
            AmsPerprojectAppExample amsPerprojectAppExample = new AmsPerprojectAppExample();
            amsPerprojectAppExample.createCriteria()
                    .andUserIdEqualTo(Integer.valueOf(userIds))
                    .andUsertypeIdEqualTo(Integer.valueOf(usertypeId))
                    .andRelationCodeEqualTo(relationCode);
            relatedApps = amsPerprojectAppService.selectByExample(amsPerprojectAppExample);
            //获取用户已经关联的角色列表
            AmsUserApproleExample amsUserApproleExample = new AmsUserApproleExample();
            amsUserApproleExample.createCriteria()
                    .andRelationCodeEqualTo(relationCode)
                    .andUserIdEqualTo(Integer.valueOf(userIds))
                    .andUsertypeIdEqualTo(Integer.valueOf(usertypeId));
            relatedroles = amsUserApproleService.selectByExample(amsUserApproleExample);
        }
        //拼接树
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(apps)) {
            for (AmsApp amsApp : apps) {
                JSONObject nodeApp = new JSONObject();
                nodeApp.put("code", amsApp.getAppId());
                nodeApp.put("name", amsApp.getAppName());
                //标记此节点为应用
                nodeApp.put("type", "1");
                nodeApp.put("open", false);


                if(relatedApps!=null){
                    for (AmsPerprojectApp ap : relatedApps){
                        if (ap.getAppId().equals(amsApp.getAppId())){
                            nodeApp.put("checked", true);
                        }
                    }
                }
                jsonArray.add(nodeApp);

                JSONArray childs = new JSONArray();
                for (AmsApprole amsApprole : amsroles) {
                    //相等说明是该应用下的角色
                    if (amsApp.getAppId().equals(amsApprole.getAppId())) {
                        JSONObject nodeRole = new JSONObject();
                        nodeRole.put("code", amsApprole.getApproleId());
                        nodeRole.put("name", amsApprole.getApproleName());
                        //标记此节点为角色
                        nodeRole.put("type", "2");
                        nodeRole.put("open", false);

                        if(relatedroles!=null) {
                            for (AmsUserApprole ap : relatedroles) {
                                if (ap.getApproleId().equals(amsApprole.getApproleId())) {
                                    nodeRole.put("checked", true);
                                }
                            }
                        }
                        childs.add(nodeRole);
                    }
                }
                //将角色几点添加到对应的应用节点下
                ((JSONObject) jsonArray.get(jsonArray.size() - 1)).put("children", childs);
            }
        }

        if (CollectionUtils.isEmpty(jsonArray)){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("暂无个性化应用与角色可分配!"));
        }
        return jsonArray;
    }


    @ApiOperation(value = "角色列表")
    @RequiresPermissions("ams:approle:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "schoolCode") String schoolCode,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {

        //查询
        AmsApproleExample amsApproleExample = new AmsApproleExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            amsApproleExample.setOrderByClause(sort + " " + order);
        }

        if (StringUtils.isNotBlank(schoolCode)){
            amsApproleExample.createCriteria()
                    .andRelationCodeEqualTo(schoolCode);
        }

        List<AmsApprole> amsroles = amsApproleService.selectByExampleForOffsetPage(amsApproleExample, offset, limit);

        int total = amsApproleService.countByExample(amsApproleExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", amsroles);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "关联组织列表")
    @RequestMapping(value = "/agencytree/{id}", method = RequestMethod.POST)
    @RequiresPermissions("ams:approle:read")
    @ResponseBody
    public Object agency(@PathVariable("id") String id) {

        List<String> codes = amsApproleService.getAmsApproleRelatedCodes(Integer.parseInt(id));
        return ucenterAgencyService.getSchoolAgencyByProjectId(codes);
    }

    @ApiOperation(value = "区域组织及学校列表")
    @RequestMapping(value = "/ucenteruser/agencytree", method = RequestMethod.POST)
    @RequiresPermissions("ams:approle:read")
    @ResponseBody
    public Object agencyAndChilds() {

        UpmsUser upmsUser = getCurrentUpmsUser();

        if (upmsUser.getManagerType().equals(BaseConstants.ManagerType.SUPMANAGER)) {
            return ucenterAgencyService.getAgencyschoolByProjectId(Collections.EMPTY_LIST);
        } else if (upmsUser.getManagerType().equals(BaseConstants.ManagerType.AREAMANAGER)) {

            UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
            ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(upmsUser.getRelationCode());
            List<UcenterAgency> ucenterAgencies = ucenterAgencyService.selectByExample(ucenterAgencyExample);

            return ucenterAgencyService.getAgencyschoolAndChilds(ucenterAgencies);
        }

        return null;
    }


    @ApiOperation(value = "分配权限")
    @RequiresPermissions("ams:approle:read")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public Object project(@PathVariable("id") String id) {
        return "/manage/approle/permission.jsp";
    }

    @ApiOperation(value = "角色权限")
    @RequiresPermissions("ams:approle:update")
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object permission(@PathVariable("id") int id, HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int result = amsApprolePermissionService.rolePermission(datas, id);
        return new AmsResult(AmsResultConstant.SUCCESS, result);
    }


    @ApiOperation(value = "角色权限树")
    @RequiresPermissions("ams:approle:read")
    @RequestMapping(value = "/permissiontree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id,
                       @RequestParam(required = false, defaultValue = "", value = "appId") String appId,
                       @RequestParam(required = true, defaultValue = "", value = "perPer") String perPer) {

        AmsApproleExample amsApproleExample = new AmsApproleExample();
        amsApproleExample.createCriteria()
                .andApproleIdEqualTo(id);
        AmsApprole amsApprole = amsApproleService.selectByPrimaryKey(id);

        HashMap<String, String> map = new HashMap<>();

        //获取当前学校所在项目中分配给指定用户类型的应用ID
        map.put("code", amsApprole.getRelationCode());
        map.put("perPer", perPer);
        map.put("userTypeId", amsApprole.getUsertypeId().toString());


        List<Integer> amsAppIds = new ArrayList<>();
        if (StringUtils.isBlank(appId)) {
            //为空表示这个角色可以分配多个应用的角色
            amsAppIds = amsApproleService.getAmsAppIdByRoleCode(map);
        } else {
            //不为空表示这个角色已经关联了一个应用
            amsAppIds.add(Integer.parseInt(appId));
        }

        JSONArray treeByApproleId = amsApppermissionService.getTreeByApproleId(id);

        dataFiltering(treeByApproleId, amsAppIds);

        return treeByApproleId;
    }

    public void dataFiltering(JSONArray jsonArray, List<Integer> amsAppIds) {
        Iterator<Object> iterator = jsonArray.iterator();

        List<JSONObject> removed = new ArrayList<>();

        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String systemid = next.getString("systemid");
            if (!amsAppIds.contains(Integer.parseInt(systemid))) {
                removed.add(next);
            }
        }

        jsonArray.removeAll(removed);
    }

    /**
     * 获取当前登录用户
     */
    private UpmsUser getCurrentUpmsUser() {
        //获取当前登录的管理员用户
        UpmsUser upmsUser = (UpmsUser) SecurityUtils.getSubject().getSession().getAttribute("upmsUser");
        if (upmsUser == null) {
            upmsUser = upmsApiService.selectUpmsUserByUsername(UserUtil.getCurrentUser());
            SecurityUtils.getSubject().getSession().setAttribute("upmsUser", upmsUser);
        }

        return upmsUser;
    }

}
