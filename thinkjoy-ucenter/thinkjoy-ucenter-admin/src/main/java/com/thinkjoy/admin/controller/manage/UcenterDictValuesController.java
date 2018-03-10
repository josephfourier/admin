package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.NotNullValidator;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import com.thinkjoy.ucenter.dao.model.UcenterDictValuesExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/7/25.
 */
@Controller
@RequestMapping("/manage/dictValues")
@Api(value = "字典管理", description = "字典管理")
public class UcenterDictValuesController  extends BaseController {

    @Autowired
    private UcenterDictValuesService ucenterDictValuesService;

    @ApiOperation(value = "字典管理首页")
    @RequiresPermissions("ucenter:dictValues:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/dictValues/index.jsp";
    }

    @ApiOperation(value = "字典管理列表")
    @RequiresPermissions("ucenter:dictValues:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterDictValuesExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            ucenterDictValuesExample.or().andDictCodeLike("%" + search + "%");
        }

        List<UcenterDictValues> rows=ucenterDictValuesService.selectByExampleForOffsetPage(ucenterDictValuesExample,offset,limit);
        long total = ucenterDictValuesService.countByExample(ucenterDictValuesExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增字典")
    @RequiresPermissions("ucenter:dictValues:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        return "/manage/dictValues/create.jsp";
    }

    @ApiOperation(value = "新增字典")
    @RequiresPermissions("ucenter:dictValues:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(UcenterDictValues ucenterDictValues){
        //校验
        ComplexResult result = FluentValidator.checkAll().on(ucenterDictValues.getDictCode(), new NotNullValidator("字典类型")).doValidate().result(ResultCollectors.toComplex());
        if (!result.isSuccess()){
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }
        ucenterDictValues.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        ucenterDictValues.setCtime(System.currentTimeMillis());

        UcenterDictValuesExample ucenterDictValuesExample=new UcenterDictValuesExample();
        ucenterDictValuesExample.or().andDictCodeEqualTo(ucenterDictValues.getDictCode()).andValueKeyEqualTo(ucenterDictValues.getValueKey());
        ucenterDictValuesExample.or().andDictCodeEqualTo(ucenterDictValues.getDictCode()).andValueNameEqualTo(ucenterDictValues.getValueName());
        int dictcount=ucenterDictValuesService.countByExample(ucenterDictValuesExample);
        if(dictcount>0){
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("字典值重复!"));
        }
        int count = ucenterDictValuesService.insertSelective(ucenterDictValues);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }



    @ApiOperation(value = "修改字典")
    @RequiresPermissions("ucenter:dictValues:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public Object update(@PathVariable("id") int id, Model model){
        UcenterDictValues ucenterDictValues = ucenterDictValuesService.selectByPrimaryKey(id);
        model.addAttribute("ucenterDictValues", ucenterDictValues);
        return "/manage/dictValues/update.jsp";
    }

    @ApiOperation(value = "修改字典")
    @RequiresPermissions("ucenter:dictValues:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") String id, UcenterDictValues ucenterDictValues){
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterDictValues.getDictCode(), new NotNullValidator("字典类型"))
                .doValidate()
                .result(ResultCollectors.toComplex());

        if (!result.isSuccess()){
            return new UcenterResult(UcenterResultConstant.FAILED, result.getErrors());
        }

        ucenterDictValues.setValueId(Integer.parseInt(id));
        ucenterDictValues.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        ucenterDictValues.setCtime(new Date().getTime());

        UcenterDictValuesExample ucenterDictValuesExample=new UcenterDictValuesExample();
        ucenterDictValuesExample.or().andDictCodeEqualTo(ucenterDictValues.getDictCode()).andValueKeyEqualTo(ucenterDictValues.getValueKey());
        ucenterDictValuesExample.or().andDictCodeEqualTo(ucenterDictValues.getDictCode()).andValueNameEqualTo(ucenterDictValues.getValueName());
        UcenterDictValues ucenterDictValues1=ucenterDictValuesService.selectFirstByExample(ucenterDictValuesExample);
        if(ucenterDictValues1!=null&&ucenterDictValues1.getValueId().intValue()!=ucenterDictValues.getValueId().intValue()){
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("字典值重复!"));
        }

        int count = ucenterDictValuesService.updateByPrimaryKeySelective(ucenterDictValues);

        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "删除字典")
    @RequiresPermissions("ucenter:dictValues:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = ucenterDictValuesService.deleteByPrimaryKeys(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }
}
