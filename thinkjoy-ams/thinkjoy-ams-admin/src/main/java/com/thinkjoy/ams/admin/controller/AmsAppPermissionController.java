package com.thinkjoy.ams.admin.controller;

import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.ams.dao.model.AmsAppExample;
import com.thinkjoy.ams.dao.model.AmsApppermission;
import com.thinkjoy.ams.dao.model.AmsApppermissionExample;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.ams.rpc.api.AmsApppermissionService;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用权限controller
 * Created by  on 2017/2/6.
 */
@Controller
@Api(value = "应用权限管理", description = "应用权限管理")
@RequestMapping("/manage/apppermission")
public class AmsAppPermissionController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(AmsAppPermissionController.class);

    @Autowired
    private AmsApppermissionService amsApppermissionService;

    @Autowired
    private AmsAppService amsAppService;


    @ApiOperation(value = "权限首页")
    @RequiresPermissions("ams:apppermission:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/apppermission/index.jsp";
    }

    @ApiOperation(value = "权限列表")
    @RequiresPermissions("ams:apppermission:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "0", value = "type") int type,
            @RequestParam(required = false, defaultValue = "0", value = "systemId") int systemId,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {


        AmsApppermissionExample amsApppermissionExample = new AmsApppermissionExample();
        AmsApppermissionExample.Criteria criteria = amsApppermissionExample.createCriteria();


        if (0 != type) {
            criteria.andTypeEqualTo((byte) type);
        }
        if (0 != systemId) {
            criteria.andSystemIdEqualTo(systemId);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            amsApppermissionExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            amsApppermissionExample.or()
                    .andNameLike("%" + search + "%");
        }
        List<AmsApppermission> rows = amsApppermissionService.selectByExampleForOffsetPage(amsApppermissionExample, offset, limit);
        long total = amsApppermissionService.countByExample(amsApppermissionExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增应用权限")
    @RequiresPermissions("ams:apppermission:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria()
                .andStatusEqualTo("1");
        List<AmsApp> amsApps = amsAppService.selectByExample(amsAppExample);
        modelMap.put("amsApps", amsApps);
        return "/manage/apppermission/create.jsp";
    }

    @ApiOperation(value = "新增权限")
    @RequiresPermissions("ams:apppermission:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@Valid AmsApppermission amsApppermission, BindingResult bindingResult) {

        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsApppermissionExample amsApppermissionExample = new AmsApppermissionExample();
        amsApppermissionExample.createCriteria().andNameEqualTo(amsApppermission.getName());
        int existCount = amsApppermissionService.countByExample(amsApppermissionExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用权限名称重复!"));
        }

        long time = System.currentTimeMillis();
        amsApppermission.setCtime(time);
        amsApppermission.setOrders(time);
        //如果是系统级别权限，去掉图标
        if(amsApppermission.getType()==0){
            amsApppermission.setIcon("");
        }
        int count = amsApppermissionService.insertSelective(amsApppermission);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除权限")
    @RequiresPermissions("ams:apppermission:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = amsApppermissionService.deleteByPrimaryKeys(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("ams:apppermission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        AmsAppExample amsAppExample = new AmsAppExample();
        amsAppExample.createCriteria()
                .andStatusEqualTo("1");
        List<AmsApp> amsApps = amsAppService.selectByExample(amsAppExample);
        AmsApppermission amsApppermission = amsApppermissionService.selectByPrimaryKey(id);
        List applicableIdentityList = new ArrayList();
        String applicableIdentity=amsApppermission.getApplicableIdentity();
        if (StringUtils.isNotBlank(applicableIdentity)) {
            String applicable[] = applicableIdentity.split(",");
            for (int i = 0; i < applicable.length; i++) {
                applicableIdentityList.add(applicable[i]);
            }
        }
        modelMap.put("applicableIdentityList", applicableIdentityList);
        modelMap.put("amsApps", amsApps);
        modelMap.put("permission", amsApppermission);
        return "/manage/apppermission/update.jsp";
    }

    @ApiOperation(value = "修改权限")
    @RequiresPermissions("ams:apppermission:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,
                         @Valid AmsApppermission amsApppermission,
                         BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsApppermissionExample amsApppermissionExample = new AmsApppermissionExample();
        amsApppermissionExample.createCriteria().andNameEqualTo(amsApppermission.getName()).andPermissionIdNotEqualTo(id);
        int existCount = amsApppermissionService.countByExample(amsApppermissionExample);
        if (existCount > 0){
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("应用权限名称重复!"));
        }

        amsApppermission.setPermissionId(id);
        int count = amsApppermissionService.updateByPrimaryKeySelective(amsApppermission);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "角色权限列表")
    @RequiresPermissions("upms:permission:read")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id) {
        return amsApppermissionService.getTreeByApproleId(id);
    }

}
