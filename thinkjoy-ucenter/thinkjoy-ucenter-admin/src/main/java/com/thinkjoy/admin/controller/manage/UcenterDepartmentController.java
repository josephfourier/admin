package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentService;
import com.thinkjoy.upms.common.constant.UpmsResult;
import com.thinkjoy.upms.common.constant.UpmsResultConstant;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jingqinghua on 17/10/16.
 */
@Controller
@RequestMapping("/manage/department")
@Api(value ="学校部门管理",description = "学校部门管理")
public class UcenterDepartmentController  extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UcenterDepartmentController.class);
    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;
    @Autowired
    private UpmsUserService upmsUserService;

    @RequiresPermissions("ucenter:department:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
        modelMap.put("upmsUser",upmsUser);
        return "/manage/department/index.jsp";
    }

    @ApiOperation(value = "部门列表")
    @RequiresPermissions("ucenter:department:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
                       @RequestParam(required = false, value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, value = "managerType") String managerType,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {

        UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
        UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }

        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterDepartmentExample.setOrderByClause(sort + " " + order);
        }
        if(!StringUtils.isBlank(schoolCode)){
            criteria.andSchoolCodeEqualTo(schoolCode);
        }
        List<UcenterDepartment> rows = ucenterDepartmentService.selectByExampleForOffsetPage(ucenterDepartmentExample, offset, limit);
        long total = ucenterDepartmentService.countByExample(ucenterDepartmentExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);

        return result;
    }


    @ApiOperation(value = "新增部门")
    @RequiresPermissions("ucenter:department:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(required = false,defaultValue = "",value = "schoolCode")String schoolCode,ModelMap modelMap) {
        modelMap.put("schoolCode",schoolCode);
        return "/manage/department/create.jsp";
    }

    @ApiOperation(value = "新增部门")
    @RequiresPermissions("ucenter:department:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterDepartment ucenterDepartment) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterDepartment.getDepartmentName(), new LengthValidator(1, 20, "部门名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        ucenterDepartment.setCtime(time);
        ucenterDepartment.setDepartmentType(UcenterConstant.DeptType.XZ);
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        ucenterDepartment.setCreator(username);
        int count = ucenterDepartmentService.insertSelective(ucenterDepartment);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }




    @ApiOperation(value = "修改组织")
    @RequiresPermissions("ucenter:department:update")
    @RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id,@PathVariable("userType") String userType, ModelMap modelMap) {
        UcenterDepartment ucenterDepartment = ucenterDepartmentService.selectByPrimaryKey(id);
        if(ucenterDepartment.getParentId()!=null){
            UcenterDepartment parentDep = ucenterDepartmentService.selectByPrimaryKey(ucenterDepartment.getParentId());
            modelMap.put("parentDep", parentDep);
        }
        modelMap.put("ucenterDepartment", ucenterDepartment);
        modelMap.put("userType", "userType");
        return "/manage/department/update.jsp";
    }

    @ApiOperation(value = "修改组织")
    @RequiresPermissions("ucenter:department:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UcenterDepartment ucenterDepartment) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterDepartment.getDepartmentName(), new LengthValidator(1, 20, "部门名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new  UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        ucenterDepartment.setDepartmentId(id);
        int count = ucenterDepartmentService.updateByPrimaryKeySelective(ucenterDepartment);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "删除部门")
    @RequiresPermissions("ucenter:department:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = ucenterDepartmentService.deleteByPrimaryKeys(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }


    @ApiOperation(value = "获取部门自关联树")
    @RequestMapping(value = "/getDepTree", method = RequestMethod.POST)
    @ResponseBody
    public Object getDepTree(@RequestParam(required = false, value = "schoolCode") String schoolCode){

        return ucenterDepartmentService.getDepTree(schoolCode);
    }

    @ApiOperation(value = "获取部门自关联树")
    @RequestMapping(value = "/getDepTreeByTeacherId", method = RequestMethod.POST)
    @ResponseBody
    public Object getDepTree(@RequestParam(required = false, value = "schoolCode") String schoolCode,String teacherId){

        return ucenterDepartmentService.getDepTreeByTeacherId(schoolCode, teacherId);
    }

    @ApiOperation(value = "获取部门人员自关联树")
    @RequestMapping(value = "/getDepteacherTree", method = RequestMethod.POST)
    @ResponseBody
    public Object getDepteacherTree(@RequestParam(required = false, value = "schoolCode") String schoolCode,
                                    @RequestParam(required = false, value = "postId") Integer postId){
        _log.info(">>>>> 人员树 <<<<<");
        return ucenterDepartmentService.getDepteacherTree(schoolCode,postId);
    }



}
