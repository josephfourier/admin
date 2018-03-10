package com.thinkjoy.admin.controller.manage;
import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.ContainBlankValidator;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import com.thinkjoy.ucenter.rpc.api.UcenterPostService;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherPostService;
import com.thinkjoy.ucenter.rpc.api.UcenterTrainingdirectionService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.dao.model.UpmsUserExample;
import com.thinkjoy.upms.rpc.api.UpmsUserService;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gengsongbo on 2018/02/18.
 */
@Controller
@RequestMapping("/manage/post")
@Api(value = "职务管理", description = "职务管理")
public class UcenterPostController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterPostController.class);

    @Autowired
    private UcenterPostService ucenterPostService;

    @Autowired
    private UpmsUserService upmsUserService;

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;

    @Autowired
    private UcenterTeacherPostService ucenterTeacherPostService;


    @ApiOperation("职务管理首页")
    @RequiresPermissions("ucenter:post:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
        modelMap.put("upmsUser",upmsUser);
        return "/manage/post/index.jsp";
    }

    @ApiOperation("职务管理列表")
    @RequiresPermissions("ucenter:post:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
                       @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, defaultValue = "", value = "serarch_postName") String serarch_postName,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order, ModelMap modelMap) {
        UcenterPostExample ucenterPostExample = new UcenterPostExample();
        UcenterPostExample.Criteria criteria = ucenterPostExample.createCriteria();
        //传入-1表示查询所有数据
        if (limit == -1) {
            limit = 0;
        }
        if (!"".equals(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterPostExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(serarch_postName)) {
            criteria.andPostNameLike("%" + serarch_postName + "%");
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }

        List<UcenterPost> rows = ucenterPostService.selectByExampleForOffsetPage(ucenterPostExample, offset, limit);
        long total = ucenterPostService.countByExample(ucenterPostExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "职务表单页面")
    @RequiresPermissions("ucenter:post:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(required = false, defaultValue = "", value = "userType") String userType,
                         @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                         ModelMap modelMap) {
        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        //职务
        ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.TEACHERLEVEl);
        List<UcenterDictValues> nationDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
        modelMap.put("nationDicts", nationDicts);
        modelMap.put("userType", userType);
        modelMap.put("schoolCode", schoolCode);
        return "/manage/post/create.jsp";
    }

    @ApiOperation(value = "新增职务")
    @RequiresPermissions("ucenter:post:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterPost ucenterPost,
                         HttpServletRequest request) {
        ComplexResult result = FluentValidator.checkAll().on(ucenterPost.getPostName(), new LengthValidator(1, 128, "名称")).on(ucenterPost.getPostName(), new ContainBlankValidator("名称")).doValidate().result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterPost.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterPost.setCtime(time);
        int count = ucenterPostService.insertSelective(ucenterPost);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "添加人员")
    @RequiresPermissions("ucenter:post:addteacher")
    @RequestMapping(value = "/addteacher/{id}/{userType}", method = RequestMethod.GET)
    public String addteacher(@PathVariable("id") int id, @PathVariable("userType") String userType, ModelMap modelMap) {
        UcenterPost ucenterPost = ucenterPostService.selectByPrimaryKey(id);

        modelMap.put("userType", userType);
        modelMap.put("ucenterPost", ucenterPost);
        return "/manage/post/teachertree.jsp";
    }

    @ApiOperation(value = "添加人员职务信息")
    @RequiresPermissions("ucenter:post:addteacher")
    @RequestMapping(value = "/addteacher/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object permission(@PathVariable("id") int id, HttpServletRequest request) {
        JSONArray datas = JSONArray.parseArray(request.getParameter("datas"));
        int result = ucenterTeacherPostService.addteacher(datas, id);
        return new UcenterResult(UcenterResultConstant.SUCCESS, result);
    }

    @ApiOperation(value = "删除职务")
    @RequiresPermissions("ucenter:post:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = ucenterPostService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "打开修改职务页面")
    @RequiresPermissions("ucenter:post:update")
    @RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, @PathVariable("userType") String userType, ModelMap modelMap) {
        UcenterPost ucenterPost = ucenterPostService.selectByPrimaryKey(id);

        modelMap.put("userType", userType);
        modelMap.put("ucenterPost", ucenterPost);
        return "/manage/post/update.jsp";
    }

    @ApiOperation("修改职务")
    @RequiresPermissions("ucenter:post:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UcenterPost ucenterPost) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterPost.getPostName(), new LengthValidator(1, 128, "名称"))
                .on(ucenterPost.getPostName(), new ContainBlankValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        ucenterPost.setPostId(id);


        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterPost.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterPost.setCtime(time);
        int count = ucenterPostService.updateByPrimaryKeySelective(ucenterPost);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }
}
