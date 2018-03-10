package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterPaySetting;
import com.thinkjoy.ucenter.dao.model.UcenterPaySettingExample;
import com.thinkjoy.ucenter.rpc.api.UcenterPaySettingService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jingqinghua on 17/9/19.
 */
@Controller
@RequestMapping("/manage/paysetting")
@Api(value = "行政区划管理", description = "行政区划管理")
public class UcenterPaySettingController extends BaseController {

    @Autowired
    private UcenterPaySettingService ucenterPaySettingService;

    @Autowired
    private UpmsApiService upmsApiService;

    @ApiOperation(value = "首页")
    @RequiresPermissions("ucenter:paysetting:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/paysetting/index.jsp";
    }

    @ApiOperation(value = "列表")
    @RequiresPermissions("ucenter:paysetting:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "", value = "areaCode") String areaCode,
            @RequestParam(required = false, defaultValue = "", value = "areaType") String areaType,
            @RequestParam(required = false, defaultValue = "", value = "status") String status,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {

        UpmsUser upmsUser = getCurrentUpmsUser();
        UcenterPaySettingExample ucenterPaySettingExample = new UcenterPaySettingExample();
        UcenterPaySettingExample.Criteria criteria=ucenterPaySettingExample.createCriteria();
        criteria.andSchoolCodeEqualTo(upmsUser.getRelationCode());

        List<UcenterPaySetting> rows=ucenterPaySettingService.selectByExampleForOffsetPage(ucenterPaySettingExample,offset,limit);
        long total = ucenterPaySettingService.countByExample(ucenterPaySettingExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增商户")
    @RequiresPermissions("ucenter:paysetting:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/paysetting/create.jsp";
    }

    @ApiOperation(value = "新增商户")
    @RequiresPermissions("ucenter:paysetting:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(UcenterPaySetting ucenterPaySetting) {


        UpmsUser upmsUser = getCurrentUpmsUser();
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterPaySetting.getMchName(), new LengthValidator(1, 60, "商户名称"))
                .on(ucenterPaySetting.getMchId(), new LengthValidator(1, 60, "商户号"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        ucenterPaySetting.setCtime(time);
        ucenterPaySetting.setCreator(upmsUser.getUsername());
        ucenterPaySetting.setStatus(BaseConstants.Status.NORMAL);
        ucenterPaySetting.setSchoolCode(upmsUser.getRelationCode());
        int count = ucenterPaySettingService.insertSelective(ucenterPaySetting);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }



    @ApiOperation(value = "修改商户")
    @RequiresPermissions("ucenter:paysetting:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UcenterPaySetting ucenterPaySetting = ucenterPaySettingService.selectByPrimaryKey(id);
        modelMap.put("ucenterPaySetting", ucenterPaySetting);
        return "/manage/paysetting/update.jsp";
    }


    @ApiOperation(value = "修改系统")
    @RequiresPermissions("ucenter:paysetting:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UcenterPaySetting ucenterPaySetting) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterPaySetting.getMchName(), new LengthValidator(1, 60, "商户名称"))
                .on(ucenterPaySetting.getMchId(), new LengthValidator(1, 60, "商户号"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        int count = ucenterPaySettingService.updateByPrimaryKeySelective(ucenterPaySetting);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }




    @ApiOperation(value = "删除商户")
    @RequiresPermissions("ucenter:paysetting:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = ucenterPaySettingService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
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
