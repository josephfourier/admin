package com.thinkjoy.ams.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.AmsResourcesService;
import com.thinkjoy.ams.rpc.api.AmsRoleResourceService;
import com.thinkjoy.ams.rpc.api.AmsRoleService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.NotNullValidator;
import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import com.thinkjoy.ucenter.dao.model.UcenterDictValuesExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "角色管理", description = "角色管理")
@Controller
@RequestMapping("/manage/role")
public class AmsRoleController extends BaseController{

    private static Logger _log = LoggerFactory.getLogger(AmsRoleController.class);

    @Autowired
    private AmsRoleService amsRoleService;

    @Autowired
    private AmsResourcesService amsResourcesService;

    @Autowired
    private AmsRoleResourceService amsRoleResourceService;

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;


    @ApiOperation(value = "角色首页")
    @RequiresPermissions("ams:role:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "/manage/role/index.jsp";
    }

    @ApiOperation(value = "角色列表")
    @RequiresPermissions("ams:role:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order){

        AmsRoleExample amsRoleExample = new AmsRoleExample();
        if (!StringUtils.isBlank(sort) && !org.apache.commons.lang.StringUtils.isBlank(order)){
            amsRoleExample.setOrderByClause(sort + " " + order);
        }

        List<AmsRole> amsroles = amsRoleService.selectByExampleForOffsetPage(amsRoleExample, offset, limit);
        int total = amsRoleService.countByExample(amsRoleExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", amsroles);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增角色")
    @RequiresPermissions("ams:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "/manage/role/create.jsp";
    }


    @ApiOperation(value = "新增角色")
    @RequiresPermissions("ams:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@Valid AmsRole amsrole, BindingResult bindingResult){
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsRoleExample amsRoleExample = new AmsRoleExample();
        amsRoleExample.createCriteria().andRoleNameEqualTo(amsrole.getRoleName());
        int existCount = amsRoleService.countByExample(amsRoleExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("资源名称重复!"));
        }

        amsrole.setCreator(UserUtil.getCurrentUser());
        amsrole.setCtime(System.currentTimeMillis());
        int count = amsRoleService.insertSelective(amsrole);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除角色")
    @RequiresPermissions("ams:role:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids){
        int count = amsRoleService.deleteByIds(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("ams:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") String id, Model model){
        AmsRole amsrole = amsRoleService.selectByPrimaryKey(Integer.parseInt(id));
        model.addAttribute("amsRole", amsrole);
        return "/manage/role/update.jsp";
    }

    @ApiOperation(value = "修改角色")
    @RequiresPermissions("ams:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") String id, @Valid AmsRole amsrole, BindingResult bindingResult){
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsRoleExample amsRoleExample = new AmsRoleExample();
        amsRoleExample.createCriteria().andRoleNameEqualTo(amsrole.getRoleName()).andRoleIdNotEqualTo(Integer.parseInt(id));
        int existCount = amsRoleService.countByExample(amsRoleExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("资源名称重复!"));
        }

        amsrole.setRoleId(Integer.parseInt(id));
        int count = amsRoleService.updateByPrimaryKeySelective(amsrole);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "分配资源")
    @RequiresPermissions("ams:role:read")
    @RequestMapping(value = "/resoures/{id}", method = RequestMethod.GET)
    public Object project(@PathVariable("id") String id){
        return "/manage/role/resources.jsp";
    }

    @ApiOperation(value = "分配资源")
    @RequiresPermissions("ams:role:update")
    @RequestMapping(value = "/resoures/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public Object project1(@PathVariable("roleId") String id, HttpServletRequest request){
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int count = amsRoleResourceService.addRoleResource(datas, id);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "关联资源列表")
    @RequiresPermissions("ams:role:update")
    @RequestMapping(value = "/resourcetree/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object agency(@PathVariable("id") String id){

        //在字典表中获取所有资源分类信息
        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.RESOURCECLASS);
        List<UcenterDictValues> allRecourceClass = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);

        Map<String, String> allResourceClassName  = new HashMap<>();
        for (UcenterDictValues u : allRecourceClass) {
            allResourceClassName.put(u.getValueKey(), u.getValueName());
        }

        //获取该角色已分配资源
        List<AmsResources> resourcesByRoleId = amsResourcesService.getResourcesByRoleId(Integer.parseInt(id));

        JSONArray resourceTree = amsResourcesService.getResourceTree(allResourceClassName, id, resourcesByRoleId);

        return resourceTree;
    }

}
