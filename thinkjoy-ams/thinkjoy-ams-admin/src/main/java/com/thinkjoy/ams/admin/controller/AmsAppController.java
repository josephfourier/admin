package com.thinkjoy.ams.admin.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.ams.dao.model.AmsAppExample;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 应用控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "应用管理", description = "应用管理")
@Controller
@RequestMapping("/manage/app")
public class AmsAppController extends BaseController{

    private static Logger _log = LoggerFactory.getLogger(AmsAppController.class);

    @Autowired
    private AmsAppService amsAppService;

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;

    @ApiOperation(value = "应用首页")
    @RequiresPermissions("ams:app:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "/manage/app/index.jsp";
    }

    @ApiOperation(value = "应用列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("ams:app:read")
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "0", value = "appClass") String appClass,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order){

        AmsAppExample amsAppExample = new AmsAppExample();
        AmsAppExample.Criteria criteria=amsAppExample.createCriteria();
        if(!"0".equals(appClass)){
            criteria.andAppClassEqualTo(appClass);
        }
        if (!StringUtils.isBlank(sort) && !org.apache.commons.lang.StringUtils.isBlank(order)){
            amsAppExample.setOrderByClause(sort + " " + order);
        }

        List<AmsApp> amsApps = amsAppService.selectByExampleForOffsetPage(amsAppExample, offset, limit);
        long total = amsAppService.countByExample(amsAppExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", amsApps);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增应用")
    @RequiresPermissions("ams:app:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "/manage/app/create.jsp";
    }


    @ApiOperation(value = "新增应用")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("ams:app:create")
    @ResponseBody
    public Object create(@Valid AmsApp amsApp, BindingResult bindingResult){

        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria().andAppCodeEqualTo(amsApp.getAppCode());
        amsAppExample.or().andAppNameEqualTo(amsApp.getAppName());
        int existCount = amsAppService.countByExample(amsAppExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用编码或应用名称重复!"));
        }

        amsApp.setCreator(UserUtil.getCurrentUser());
        amsApp.setCtime(System.currentTimeMillis());
        int count = amsAppService.insertSelective(amsApp);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除应用")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @RequiresPermissions("ams:app:delete")
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids){

        int count = amsAppService.deleteByIds(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改应用")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("ams:app:update")
    public Object update(@PathVariable("id") String id, Model model){
        AmsApp amsApp = amsAppService.selectByPrimaryKey(Integer.parseInt(id));
        String[] applicableIdentitys = amsApp.getApplicableIdentity().split(",");

        model.addAttribute("amsApp", amsApp);
        model.addAttribute("applicableIdentitys", applicableIdentitys.length>0?applicableIdentitys:null);
        return "/manage/app/update.jsp";
    }

    @ApiOperation(value = "修改应用")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("ams:app:update")
    @ResponseBody
    public Object update(@PathVariable("id") String id, @Valid AmsApp amsApp, BindingResult bindingResult){

        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria().andAppCodeEqualTo(amsApp.getAppCode()).andAppIdNotEqualTo(Integer.parseInt(id));
        amsAppExample.or().andAppNameEqualTo(amsApp.getAppName()).andAppIdNotEqualTo(Integer.parseInt(id));;
        int existCount = amsAppService.countByExample(amsAppExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用编码或应用名称重复!"));
        }

        amsApp.setAppId(Integer.parseInt(id));
        int count = amsAppService.updateByPrimaryKeySelective(amsApp);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

}
