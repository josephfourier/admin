package com.thinkjoy.enrollment.admin.controller;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.enrollment.dao.model.EnrollChargeitem;
import com.thinkjoy.enrollment.dao.model.EnrollChargeitemExample;
import com.thinkjoy.enrollment.rpc.api.EnrollChargeitemService;
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
 * 缴费类别控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "缴费类别管理", description = "缴费类别管理")
@Controller
@RequestMapping("/manage/chargeitem")
public class EnrollChargeitemController extends BaseController<EnrollChargeitem, EnrollChargeitemService> {

    private static Logger _log = LoggerFactory.getLogger(EnrollChargeitemController.class);

    @Autowired
    private EnrollChargeitemService enrollChargeitemService;

    @Override
    protected EnrollChargeitemService getService() {
        return enrollChargeitemService;
    }

    @ApiOperation(value = "缴费类别首页")
    @RequiresPermissions("enroll:chargeitem:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/chargeitem/index.jsp";
    }

    @ApiOperation(value = "缴费类别列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("enroll:chargeitem:read")
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order,
            HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }
        EnrollChargeitemExample enrollChargeitemExample = new EnrollChargeitemExample();
        EnrollChargeitemExample.Criteria criteria = enrollChargeitemExample.createCriteria();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            enrollChargeitemExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(relationCode)) {
            criteria.andSchoolCodeEqualTo(relationCode);
        }

        //criteria.andStatusEqualTo(BaseConstants.Status.NORMAL);

        List<EnrollChargeitem> enrollChargeitems = enrollChargeitemService.selectByExampleForOffsetPage(enrollChargeitemExample, offset, limit);
        long total = enrollChargeitemService.countByExample(enrollChargeitemExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", enrollChargeitems);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "新增缴费类别")
    @RequiresPermissions("enroll:chargeitem:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        return "/manage/chargeitem/create.jsp";
    }


    @ApiOperation(value = "新增缴费类别")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("enroll:chargeitem:create")
    @ResponseBody
    public Object create(@Valid EnrollChargeitem enrollChargeitem,
                         BindingResult bindingResult,
                         HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }

        //字段重复校验
        EnrollChargeitemExample enrollChargeitemExample = new EnrollChargeitemExample();
        enrollChargeitemExample.createCriteria()
                .andItemNameEqualTo(enrollChargeitem.getItemName())
                .andSchoolCodeEqualTo(relationCode);
        int existCount = enrollChargeitemService.countByExample(enrollChargeitemExample);
        if (existCount > 0) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("缴费类别名称重复!"));
        }

        enrollChargeitem.setCreator(UserUtil.getCurrentUser());
        enrollChargeitem.setCtime(System.currentTimeMillis());
        enrollChargeitem.setYear(Integer.parseInt(DateUtil.getYear()));
        enrollChargeitem.setSchoolCode(relationCode);
        int count = enrollChargeitemService.insertSelective(enrollChargeitem);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除缴费类别")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:chargeitem:delete")
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = enrollChargeitemService.deleteByPrimaryKeys(ids);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改缴费类别")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:chargeitem:update")
    public Object update(@PathVariable("id") String id,
                         Model model,
                         @ModelAttribute("model") EnrollChargeitem enrollChargeitem) {
        model.addAttribute("enrollChargeitem", enrollChargeitem);
        return "/manage/chargeitem/update.jsp";
    }

    @ApiOperation(value = "修改缴费类别")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("enroll:chargeitem:update")
    @ResponseBody
    public Object update(@PathVariable("id") String id,
                         @Valid @ModelAttribute("model") EnrollChargeitem enrollChargeitem,
                         BindingResult bindingResult) {

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        EnrollChargeitemExample enrollChargeitemExample = new EnrollChargeitemExample();
        enrollChargeitemExample.createCriteria()
                .andItemIdNotEqualTo(enrollChargeitem.getItemId())
                .andItemNameEqualTo(enrollChargeitem.getItemName())
                .andSchoolCodeEqualTo(enrollChargeitem.getSchoolCode());
        int existCount = enrollChargeitemService.countByExample(enrollChargeitemExample);
        if (existCount > 0) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("缴费类别名称重复!"));
        }

        int count = enrollChargeitemService.updateByPrimaryKeySelective(enrollChargeitem);

        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改缴费类别状态")
    @RequiresPermissions("enroll:chargeitem:update")
    @ResponseBody
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public String updateStatus(@PathVariable("id") int id, @RequestParam(value = "status", required = false, defaultValue = "0") String status) {
        EnrollChargeitemExample example = new EnrollChargeitemExample();
        EnrollChargeitemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        EnrollChargeitem enrollChargeitem = new EnrollChargeitem();
        enrollChargeitem.setStatus(status);
        int i = enrollChargeitemService.updateByExampleSelective(enrollChargeitem, example);
        return i > 0 ? "true" : "false";
    }
}
