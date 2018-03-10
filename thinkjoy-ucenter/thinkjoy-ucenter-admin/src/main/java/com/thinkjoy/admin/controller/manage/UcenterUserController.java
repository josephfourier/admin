package com.thinkjoy.admin.controller.manage;


import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.JSONUtils;
import com.thinkjoy.common.util.UUIDTool;
import com.thinkjoy.common.validator.NotNullValidator;
import com.thinkjoy.common.validator.StatusValidator;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
import com.thinkjoy.upms.common.constant.UpmsResult;
import com.thinkjoy.upms.common.constant.UpmsResultConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/7/25.
 */
@Controller
@RequestMapping("/manage/user")
@Api(value = "用户基本信息管理", description = "用户基本信息管理")
public class UcenterUserController extends BaseController {

    @Autowired
    private UcenterUserService ucenterUserService;

    @Autowired
    private UcenterUsertypeService ucenterUsertypeService;

    @Autowired
    private UcenterAgencyService ucenterAgencyService;


    @Autowired
    private UcenterUserIdentityService ucenterUserIdentityService;

    @Autowired
    private UcenterSchoolService ucenterSchoolService;

    @ApiOperation(value = "用户基本信息首页")
    @RequiresPermissions("ucenter:user:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/user/index.jsp";
    }

    @ApiOperation(value = "用户基本信息列表")
    @RequiresPermissions("ucenter:user:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            ucenterUserExample.or().andNicknameLike("%" + search + "%");
        }
        List<UcenterUser> rows = ucenterUserService.selectByExampleForOffsetPage(ucenterUserExample, offset, limit);
        if(rows.size()>0){
            for(UcenterUser obj:rows){
                obj.setUserIdentity(getIdentityByUserId(obj.getUserId()));
            }
        }

        long total = ucenterUserService.countByExample(ucenterUserExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增用户信息")
    @RequiresPermissions("ucenter:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        UcenterUsertypeExample ucenterUsertypeExample = new UcenterUsertypeExample();
        List<UcenterUsertype> ucenterUsertypeList = ucenterUsertypeService.getall();
        model.addAttribute("ucenterUsertypeList", ucenterUsertypeList);
        return "/manage/user/create.jsp";
    }


    @ApiOperation(value = "新增用户信息")
    @RequiresPermissions("ucenter:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(UcenterUser ucenterUser, HttpServletRequest request) {

        //校验
        ComplexResult result = FluentValidator.checkAll().on(ucenterUser.getUsername(), new NotNullValidator("用户账号")).doValidate().result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        if (StringUtils.isEmpty(ucenterUser.getUid())) {
            ucenterUser.setUid(UUIDTool.getUUID());
        }
        if (StringUtils.isEmpty(ucenterUser.getPassword())) {
            ucenterUser.setPassword(AESUtil.AESEncode("111111"));
        } else {
            ucenterUser.setPassword(AESUtil.AESEncode(ucenterUser.getPassword()));
        }
        ucenterUser.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        ucenterUser.setCtime(System.currentTimeMillis());
        ucenterUser.setYear(Integer.parseInt(DateUtil.getYear()));
        int count = ucenterUserService.insertSelective(ucenterUser);


        //保存用户身份
        Integer userId = ucenterUser.getUserId();
        String checkbox[] = request.getParameterValues("userType");
        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        String schoolCode = request.getParameter("schoolCode");
        String orgCode = request.getParameter("orgCode");
        String schoolName = request.getParameter("schoolName");
        String orgName = request.getParameter("orgName");

        ucenterUserExample.or().andUidEqualTo(ucenterUser.getUid());
        UcenterUser ucenterUser1 = ucenterUserService.selectFirstByExample(ucenterUserExample);

        for (int i = 0; i < checkbox.length; i++) {
            String userType = checkbox[i];
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser1.getUserId());
            ucenterUserIdentity.setUsertypeId(Integer.parseInt(userType));

            //身份类型是4的
            if (userType.equals("4")) {
                ucenterUserIdentity.setRelationCode(orgCode);
                ucenterUserIdentity.setRelationName(orgName);
            } else {
                ucenterUserIdentity.setRelationCode(schoolCode);
                ucenterUserIdentity.setRelationName(schoolName);
            }
            ucenterUserIdentityService.insertSelective(ucenterUserIdentity);
        }

        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "修改用户信息")
    @RequiresPermissions("ucenter:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") int id, Model model) {
        UcenterUser ucenterUser = ucenterUserService.selectByPrimaryKey(id);

        UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
        ucenterUserIdentityExample.or().andUserIdEqualTo(ucenterUser.getUserId());
        List<UcenterUserIdentity> list = ucenterUserIdentityService.selectByExample(ucenterUserIdentityExample);
        if (list.size() > 0) {
            for (UcenterUserIdentity obj : list) {
                Integer usertype = obj.getUsertypeId();
                if (usertype.intValue() == 1 || usertype.intValue() == 2 || usertype.intValue() == 3) {
                    UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
                    ucenterSchoolExample.or().andSchoolCodeEqualTo(obj.getRelationCode());
                    UcenterSchool ucenterSchool = ucenterSchoolService.selectFirstByExample(ucenterSchoolExample);
                    if (null != ucenterSchool) {
                        model.addAttribute("schoolName", ucenterSchool.getSchoolName());
                        model.addAttribute("schoolcode", ucenterSchool.getSchoolCode());
                    } else {
                        model.addAttribute("schoolName", null);
                        model.addAttribute("schoolcode", null);
                    }

                } else if (usertype.intValue() == 4) {
                    UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
                    ucenterAgencyExample.or().andAgencyCodeEqualTo(obj.getRelationCode());
                    UcenterAgency ucenterAgency = ucenterAgencyService.selectFirstByExample(ucenterAgencyExample);
                    if (null != ucenterAgency) {
                        model.addAttribute("agencyName", ucenterAgency.getAgencyName());
                        model.addAttribute("agencycode", ucenterAgency.getAgencyCode());
                    } else {
                        model.addAttribute("agencyName", null);
                        model.addAttribute("agencycode", null);
                    }
                }
            }
        }
        model.addAttribute("ucenterUser", ucenterUser);
        model.addAttribute("list", list);
        return "/manage/user/update.jsp";
    }

    @ApiOperation(value = "修改用户信息")
    @RequiresPermissions("ucenter:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") String id, HttpServletRequest request) {




        //根据id先获取
        Integer userId=Integer.parseInt(id);

        UcenterUserExample ucenterUserExample = new UcenterUserExample();
        UcenterUserExample.Criteria criteria=ucenterUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        UcenterUser ucenterUser = ucenterUserService.selectFirstByExample(ucenterUserExample);

        //验证
        ComplexResult result = FluentValidator.checkAll().on(ucenterUser.getStatus(), new StatusValidator("状态")).doValidate().result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }

        //先删除关系,从新在保存
        UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
        ucenterUserIdentityExample.or().andUserIdEqualTo(userId);
        ucenterUserIdentityService.deleteByExample(ucenterUserIdentityExample);


        //保存用户身份
        String checkbox[] = request.getParameterValues("userType");



        for (int i = 0; i < checkbox.length; i++) {
            String userType = checkbox[i];
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(Integer.parseInt(userType));
            ucenterUserIdentity.setRelationCode(ucenterUser.getSchoolCode());
            ucenterUserIdentity.setPersonalization("0");
            ucenterUserIdentityService.insert(ucenterUserIdentity);
        }
        //保存用户身份
        return new UcenterResult(UcenterResultConstant.SUCCESS, checkbox.length);
    }


    @ApiOperation(value = "删除用户信息")
    @RequiresPermissions("ucenter:user:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {


        String[] idArray = ids.split("-");

        for (String obj : idArray) {
            UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
            ucenterUserIdentityExample.or().andUserIdEqualTo(Integer.parseInt(obj));
            ucenterUserIdentityService.deleteByExample(ucenterUserIdentityExample);
        }

        int count = ucenterUserService.deleteByPrimaryKeys(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "获取学校树")
    @RequestMapping(value = "/getschoolTree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object getschoolTree(@PathVariable("id") String id) {

        return ucenterAgencyService.getSchoolAgencyByProjectId(new ArrayList());
    }


    @ApiOperation(value = "获取组织树")
    @RequestMapping(value = "/getTree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object getTree(@PathVariable("id") String id) {

        String treenode = ucenterAgencyService.getOrgTree();
        return JSONUtils.toJSONArray(treenode);
    }


    public String getIdentityByUserId(Integer userId){
        StringBuffer identity=new StringBuffer("");
        UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
        ucenterUserIdentityExample.or().andUserIdEqualTo(userId);
        List<UcenterUserIdentity> list = ucenterUserIdentityService.selectByExample(ucenterUserIdentityExample);
        if(list.size()>0){
            for (UcenterUserIdentity obj:list){
                if(obj.getUsertypeId().intValue()==1){
                    identity.append("学生,");
                }else if(obj.getUsertypeId().intValue()==2){
                    identity.append("家长,");
                }else if(obj.getUsertypeId().intValue()==3){
                    identity.append("老师,");
                }else if(obj.getUsertypeId().intValue()==4){
                    identity.append("机构,");
                }
            }
        }
        return identity.toString();
    }

}
