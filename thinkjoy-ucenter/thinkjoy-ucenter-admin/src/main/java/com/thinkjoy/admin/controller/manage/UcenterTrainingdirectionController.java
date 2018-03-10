package com.thinkjoy.admin.controller.manage;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.ContainBlankValidator;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;
import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirection;
import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirectionExample;
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
 * Created by gengsongbo on 2017/10/19.
 */
@Controller
@RequestMapping("/manage/traind")
@Api(value = "学校专业培养方向管理", description = "学校专业培养方向管理")
public class UcenterTrainingdirectionController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterTrainingdirectionController.class);

    @Autowired
    private UpmsUserService upmsUserService;

    @Autowired
    private UcenterTrainingdirectionService TradService;

    @ApiOperation("专业培养方向管理首页")
    @RequiresPermissions("ucenter:traind:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser = upmsUserService.selectFirstByExample(upmsUserExample);
        modelMap.put("upmsUser", upmsUser);
        return "/manage/traind/index.jsp";
    }

    @ApiOperation("专业培养方向管理列表")
    @RequiresPermissions("ucenter:traind:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
                       @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, defaultValue = "", value = "serarch_trdrName") String serarch_trdrName,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order, ModelMap modelMap) {
        UcenterTrainingdirectionExample ucenterTrainingdirectionExample = new UcenterTrainingdirectionExample();
        UcenterTrainingdirectionExample.Criteria criteria = ucenterTrainingdirectionExample.createCriteria();
        //传入-1表示查询所有数据
        if (limit == -1) {
            limit = 0;
        }
        if (!"".equals(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterTrainingdirectionExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(serarch_trdrName)) {
            criteria.andTrdrNameLike("%" + serarch_trdrName + "%");
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }

        List<UcenterTrainingdirection> rows = TradService.selectByExampleForOffsetPage(ucenterTrainingdirectionExample, offset, limit);
        long total = TradService.countByExample(ucenterTrainingdirectionExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "打开新增专业培养方向页面")
    @RequiresPermissions("ucenter:traind:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(required = false, defaultValue = "", value = "userType") String userType,
                         @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                         ModelMap modelMap) {
        modelMap.put("userType", userType);
        modelMap.put("schoolCode", schoolCode);
        return "/manage/traind/create.jsp";
    }

    @ApiOperation(value = "新增专业培养方向")
    @RequiresPermissions("ucenter:traind:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterTrainingdirection trainingdirection,
                         HttpServletRequest request) {
        ComplexResult result = FluentValidator.checkAll().on(trainingdirection.getTrdrName(), new LengthValidator(1, 128, "名称")).on(trainingdirection.getTrdrName(), new ContainBlankValidator("名称")).doValidate().result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        //名称重复校验
        String msg = checkExist(trainingdirection);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        trainingdirection.setCreator(username);
        long time = System.currentTimeMillis();
        trainingdirection.setCtime(time);
        int count = TradService.insertSelective(trainingdirection);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }


    @ApiOperation("查询名称是否重复")
    @ResponseBody
    @RequestMapping(value="/checkName",method = RequestMethod.POST)
    public boolean checkName(String trdrName,String schoolCode) {
        boolean isExit = false;
        UcenterTrainingdirectionExample ucenterTrainingdirectionExample = new UcenterTrainingdirectionExample();
        UcenterTrainingdirectionExample.Criteria criteria = ucenterTrainingdirectionExample.createCriteria();

        if (StringUtils.isNotBlank(trdrName)) {
            criteria.andTrdrNameEqualTo(trdrName).andSchoolCodeEqualTo(schoolCode);
        }


        int count= TradService.countByExample(ucenterTrainingdirectionExample);
        if (count>0) {
            isExit = true;
        }
        return  isExit;
    }

    public String checkExist(UcenterTrainingdirection u) {
        UcenterTrainingdirectionExample ut = new UcenterTrainingdirectionExample();
        UcenterTrainingdirectionExample.Criteria criteria = ut.createCriteria().andTrdrNameEqualTo(u.getTrdrName()).andSchoolCodeEqualTo(u.getSchoolCode());
        if (u.getTrdrId() != null) {
            criteria.andTrdrIdNotEqualTo(u.getTrdrId());
        }
        int i = TradService.countByExample(ut);
        if (i > 0) {
            return "培养方向名称重复!";
        }
        return null;
    }

    @ApiOperation(value = "删除专业培养方向")
    @RequiresPermissions("ucenter:traind:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = TradService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "打开修改专业培养方向页面")
    @RequiresPermissions("ucenter:traind:update")
    @RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, @PathVariable("userType") String userType, ModelMap modelMap) {
        UcenterTrainingdirection trainingdirection = TradService.selectByPrimaryKey(id);

        modelMap.put("userType", userType);
        modelMap.put("trainingdirection", trainingdirection);
        return "/manage/traind/update.jsp";
    }

    @ApiOperation("修改专业培养方向")
    @RequiresPermissions("ucenter:traind:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UcenterTrainingdirection trainingdirection) {
        ComplexResult result = FluentValidator.checkAll()
                .on(trainingdirection.getTrdrName(), new LengthValidator(1, 128, "名称"))
                .on(trainingdirection.getTrdrName(), new ContainBlankValidator("名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        trainingdirection.setTrdrId(id);
        //名称重复校验
        String msg = checkExist(trainingdirection);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        trainingdirection.setCreator(username);
        long time = System.currentTimeMillis();
        trainingdirection.setCtime(time);
        trainingdirection.setTrdrId(id);
        int count = TradService.updateByPrimaryKeySelective(trainingdirection);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }
}
