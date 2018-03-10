package com.thinkjoy.ams.admin.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.baidu.unbiz.fluentvalidator.*;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.ams.rpc.api.AmsRoleService;
import com.thinkjoy.ams.rpc.api.OauthClientDetailsService;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.LengthValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/8/3.
 */
@Api(value = "认证配置管理", description = "认证配置管理")
@Controller
@RequestMapping("/manage/oauthclient")
public class OauthClientDetailsController extends BaseController{

    private static Logger  _log= LoggerFactory.getLogger(OauthClientDetailsController.class);

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private AmsAppService amsAppService;
    @Autowired
    private AmsRoleService amsRoleService;

    @ApiOperation("认证配置首页")
    @RequiresPermissions("ams:oauthclient:read")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(ModelMap modelMap){

        List<AmsRole> amsRoles=amsRoleService.selectByExample(new AmsRoleExample());
        modelMap.put("amsRoles", amsRoles);
        List<AmsApp> amsApps=amsAppService.selectByExample(new AmsAppExample());
        modelMap.put("amsApps",amsApps);
        return "/manage/oauthclient/index.jsp";
    }

    @ApiOperation("认证配置列表")
    @RequiresPermissions("ams:oauthclient:read")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list( @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                        @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                        @RequestParam(required = false, defaultValue = "", value = "search") String search,
                        @RequestParam(required = false, defaultValue = "", value = "status") String status,
                        @RequestParam(required = false, value = "sort") String sort,
                        @RequestParam(required = false, value = "order") String order){
        OauthClientDetailsExample oauthClientDetailsExample=new OauthClientDetailsExample();
        OauthClientDetailsExample.Criteria  criteria=oauthClientDetailsExample.createCriteria();
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            oauthClientDetailsExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            oauthClientDetailsExample.or().andClientNameLike("%" + search + "%");
        }

        List<OauthClientDetails> rows=oauthClientDetailsService.selectByExampleForOffsetPage(oauthClientDetailsExample, offset, limit);
        int total=oauthClientDetailsService.countByExample(oauthClientDetailsExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增认证配置")
    @RequiresPermissions("ams:oauthclient:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        List<AmsRole> amsRoles=amsRoleService.selectByExample(new AmsRoleExample());
        modelMap.put("amsRoles",amsRoles);
        return "/manage/oauthclient/create.jsp";
    }

    @ApiOperation("新增认证配置")
    @RequiresPermissions("ams:oauthclient:create")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(@Valid OauthClientDetails oauthClientDetails, BindingResult bindingResult){
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        OauthClientDetailsExample oauthClientDetailsExample = new OauthClientDetailsExample();
        oauthClientDetailsExample.createCriteria().andClientIdEqualTo(oauthClientDetails.getClientId());
        oauthClientDetailsExample.or().andClientNameEqualTo(oauthClientDetails.getClientName());
        int existCount = oauthClientDetailsService.countByExample(oauthClientDetailsExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("客户端ID或名称重复!"));
        }

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        oauthClientDetails.setCreator(username);
        long time = System.currentTimeMillis();
        oauthClientDetails.setCtime(time);

        int count=oauthClientDetailsService.insertSelective(oauthClientDetails);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除认证配置")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids){
        int count = oauthClientDetailsService.deleteByPrimaryKeys(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改认证配置")
    @RequiresPermissions("ams:oauthclient:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") int id, Model model){
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.selectByPrimaryKey(id);
        model.addAttribute("oauthClientDetails", oauthClientDetails);

        AmsApp amsApp = amsAppService.selectByPrimaryKey(oauthClientDetails.getAppId());
        model.addAttribute("appClass", amsApp==null?0:amsApp.getAppClass());

        List<AmsRole> amsRoles=amsRoleService.selectByExample(new AmsRoleExample());
        model.addAttribute("amsRoles", amsRoles.size()>0?amsRoles:0);

        String grantTypes[]=oauthClientDetails.getGrantTypes().split(",");
        model.addAttribute("grantTypes",grantTypes.length>0?grantTypes:0);

        return "/manage/oauthclient/update.jsp";
    }

    @ApiOperation(value = "修改认证配置")
    @RequiresPermissions("ams:oauthclient:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,
                         @Valid OauthClientDetails oauthClientDetails, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        OauthClientDetailsExample oauthClientDetailsExample = new OauthClientDetailsExample();
        oauthClientDetailsExample.createCriteria().andClientNameEqualTo(oauthClientDetails.getClientName()).andIdNotEqualTo(id);
        int existCount = oauthClientDetailsService.countByExample(oauthClientDetailsExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("客户端名称重复!"));
        }

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        oauthClientDetails.setCreator(username);
        long time = System.currentTimeMillis();
        oauthClientDetails.setCtime(time);

        oauthClientDetails.setId(id);
        int count = oauthClientDetailsService.updateByPrimaryKeySelective(oauthClientDetails);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }
}
