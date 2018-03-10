package com.thinkjoy.ams.admin.controller;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.ams.dao.model.*;

import com.thinkjoy.ams.rpc.api.AmsResourcesService;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.NotNullValidator;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "资源管理", description = "资源管理")
@Controller
@RequestMapping("/manage/resources")
public class AmsResourcesController extends BaseController{

    private static Logger _log = LoggerFactory.getLogger(AmsResourcesController.class);

    @Autowired
    private AmsResourcesService amsResourcesService;


    @ApiOperation(value = "资源首页")
    @RequiresPermissions("ams:resources:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "/manage/resources/index.jsp";
    }

    @ApiOperation(value = "资源列表")
    @RequiresPermissions("ams:resources:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order
){

        AmsResourcesExample amsResourcesExample = new AmsResourcesExample();
        if (!StringUtils.isBlank(sort) && !org.apache.commons.lang.StringUtils.isBlank(order)){
            amsResourcesExample.setOrderByClause(sort + " " + order);
        }

        amsResourcesExample.createCriteria().andStatusEqualTo("1");
        List<AmsResources> amsResourcess = amsResourcesService.selectByExampleForOffsetPage(amsResourcesExample, offset, limit);
        int total = amsResourcesService.countByExample(amsResourcesExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", amsResourcess);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增资源")
    @RequiresPermissions("ams:resources:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "/manage/resources/create.jsp";
    }


    @ApiOperation(value = "新增资源")
    @RequiresPermissions("ams:resources:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@Valid AmsResources amsResources, BindingResult bindingResult){
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsResourcesExample amsResourcesExample = new AmsResourcesExample();
        amsResourcesExample.createCriteria().andResourceNameEqualTo(amsResources.getResourceName());
        int existCount = amsResourcesService.countByExample(amsResourcesExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("资源名称重复!"));
        }

        amsResources.setCreator(UserUtil.getCurrentUser());
        amsResources.setCtime(System.currentTimeMillis());
        int count = amsResourcesService.insertSelective(amsResources);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除资源")
    @RequiresPermissions("ams:resources:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids){
        int count = amsResourcesService.deleteByIds(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改资源")
    @RequiresPermissions("ams:resources:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") String id, Model model){
        AmsResources amsResources = amsResourcesService.selectByPrimaryKey(Integer.parseInt(id));
        model.addAttribute("amsResources", amsResources);
        return "/manage/resources/update.jsp";
    }

    @ApiOperation(value = "修改资源")
    @RequiresPermissions("ams:resources:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") String id, @Valid AmsResources amsResources, BindingResult bindingResult){
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsResourcesExample amsResourcesExample = new AmsResourcesExample();
        amsResourcesExample.createCriteria().andResourceNameEqualTo(amsResources.getResourceName()).andResourceIdNotEqualTo(Integer.parseInt(id));
        int existCount = amsResourcesService.countByExample(amsResourcesExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("资源名称重复!"));
        }

        amsResources.setResourceId(Integer.parseInt(id));
        int count = amsResourcesService.updateByPrimaryKeySelective(amsResources);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

}
