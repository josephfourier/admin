package com.thinkjoy.ams.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.ams.dao.model.*;
import com.thinkjoy.ams.rpc.api.AmsAppService;
import com.thinkjoy.ams.rpc.api.AmsProjectAppService;
import com.thinkjoy.ams.rpc.api.AmsProjectSchoolagencyService;
import com.thinkjoy.ams.rpc.api.AmsProjectService;
import com.thinkjoy.common.base.AmsResult;
import com.thinkjoy.common.base.AmsResultConstant;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "项目管理", description = "项目管理")
@Controller
@RequestMapping("/manage/project")
public class AmsProjectController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(AmsProjectController.class);

    @Autowired
    private AmsProjectService amsProjectService;

    @Autowired
    private AmsProjectSchoolagencyService amsProjectSchoolagencyService;

    @Autowired
    private UcenterAgencyService ucenterAgencyService;

    @Autowired
    private AmsAppService amsAppService;

    @Autowired
    private AmsProjectAppService amsProjectAppService;

    @ApiOperation(value = "项目首页")
    @RequiresPermissions("ams:project:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/project/index.jsp";
    }

    @ApiOperation(value = "项目列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("ams:project:read")
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {

        AmsProjectExample amsProjectExample = new AmsProjectExample();
        if (!StringUtils.isBlank(sort) && !org.apache.commons.lang.StringUtils.isBlank(order)) {
            amsProjectExample.setOrderByClause(sort + " " + order);
        }

        //amsProjectExample.createCriteria().andStatusEqualTo("1");

        List<AmsProject> amsProjects = amsProjectService.selectByExampleForOffsetPage(amsProjectExample, offset, limit);
        int total = amsProjectService.countByExample(amsProjectExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", amsProjects);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增项目")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @RequiresPermissions("ams:project:create")
    public String create(Model model) {
        return "/manage/project/create.jsp";
    }


    @ApiOperation(value = "新增项目")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("ams:project:create")
    @ResponseBody
    public Object create(@Valid AmsProject amsProject, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsProjectExample amsProjectExample = new AmsProjectExample();
        amsProjectExample.createCriteria().andProjectNameEqualTo(amsProject.getProjectName());
        int existCount = amsProjectService.countByExample(amsProjectExample);
        if (existCount > 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("项目名称重复!"));
        }


        amsProject.setCreator(UserUtil.getCurrentUser());
        amsProject.setCtime(System.currentTimeMillis());
        int count = amsProjectService.insertSelective(amsProject);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除项目")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @RequiresPermissions("ams:project:delete")
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = amsProjectService.deleteByIds(ids);
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改项目")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("ams:project:update")
    public Object update(@PathVariable("id") String id, Model model) {
        AmsProject amsProject = amsProjectService.selectByPrimaryKey(Integer.parseInt(id));
        model.addAttribute("amsProject", amsProject);
        return "/manage/project/update.jsp";
    }

    @ApiOperation(value = "修改项目")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("ams:project:update")
    @ResponseBody
    public Object update(@PathVariable("id") String id, @Valid AmsProject amsProject, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        AmsProjectExample amsProjectExample = new AmsProjectExample();
        amsProjectExample.createCriteria().andProjectNameEqualTo(amsProject.getProjectName()).andProjectIdNotEqualTo(Integer.parseInt(id));
        int existCount = amsProjectService.countByExample(amsProjectExample);
        if (existCount > 0) {
            return new AmsResult(AmsResultConstant.FAILED, getErrorMsg("项目名称重复!"));
        }

        amsProject.setProjectId(Integer.parseInt(id));
        int count = amsProjectService.updateByPrimaryKeySelective(amsProject);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "关联组织与应用")
    @RequestMapping(value = "/agencyschool/{id}", method = RequestMethod.GET)
    @RequiresPermissions("ams:project:read")
    public Object project(@PathVariable("id") String id, Model model) {

        AmsProjectAppExample amsProjectAppExample = new AmsProjectAppExample();
        amsProjectAppExample.createCriteria().andProjectIdEqualTo(Integer.parseInt(id));
        //已关联的应用
        List<AmsProjectApp> relatedApps = amsProjectAppService.selectByExample(amsProjectAppExample);

        //所有应用列表
        AmsAppExample amsAppExample = new AmsAppExample();
        List<AmsApp> amsApps = amsAppService.selectByExample(amsAppExample);


        Map<Integer, Object> map = new HashMap<Integer, Object>();
        for (AmsProjectApp apa : relatedApps) {
            map.put(apa.getAppId(), apa);
        }

        model.addAttribute("pid", id);

        model.addAttribute("allApps", amsApps);

        model.addAttribute("relatedApps", map);

        return "/manage/project/agency.jsp";
    }

    @ApiOperation(value = "关联组织与应用")
    @RequestMapping(value = "/agencyschool/{projectId}", method = RequestMethod.POST)
    @RequiresPermissions("ams:project:update")
    @ResponseBody
    public Object project1(@PathVariable("projectId") String id,
                           @RequestParam(value = "appId", required = false) String[] appId,
                           HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int count = amsProjectService.relateAgencyAndApp(datas, appId, Integer.valueOf(id));
        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "关联组织树")
    @RequestMapping(value = "/agencytree/{id}", method = RequestMethod.POST)
    @RequiresPermissions("ams:project:read")
    @ResponseBody
    public Object agency(@PathVariable("id") String id) {

        List<String> codes = amsProjectService.getAmsProjectRelatedCodes(Integer.parseInt(id));

        return ucenterAgencyService.getSchoolAgencyByProjectId(codes);
    }

    @ApiOperation(value = "关联应用")
    @RequiresPermissions("ams:project:update")
    @RequestMapping(value = "/relateapp/{id}", method = RequestMethod.GET)
    public String school(@PathVariable("id") String id, Model model) {

        AmsProjectAppExample amsProjectAppExample = new AmsProjectAppExample();
        amsProjectAppExample.createCriteria().andProjectIdEqualTo(Integer.parseInt(id));
        //已关联的应用
        List<AmsProjectApp> relatedApps = amsProjectAppService.selectByExample(amsProjectAppExample);

        //所有应用列表
        AmsAppExample amsAppExample = new AmsAppExample();
        List<AmsApp> amsApps = amsAppService.selectByExample(amsAppExample);


        Map<Integer, Object> map = new HashMap<Integer, Object>();
        for (AmsProjectApp apa : relatedApps) {
            map.put(apa.getAppId(), apa);
        }

        model.addAttribute("pid", id);

        model.addAttribute("allApps", amsApps);

        model.addAttribute("relatedApps", map);

        return "/manage/project/relatedapp.jsp";
    }

    @ApiOperation(value = "关联应用")
    @RequiresPermissions("ams:project:update")
    @RequestMapping(value = "/relateapp/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object school1(@PathVariable("id") String id, String[] appId) {

        int count = amsProjectAppService.addProjectApp(appId, id);

        return new AmsResult(AmsResultConstant.SUCCESS, count);
    }

}
