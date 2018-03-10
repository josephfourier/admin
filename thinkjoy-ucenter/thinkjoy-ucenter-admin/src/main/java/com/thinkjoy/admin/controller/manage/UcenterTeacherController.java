package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.common.validator.NotNullValidator;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentService;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherClassService;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherService;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherSpecialtyService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengsongboƒ
 * @date 2017/09/20
 */
@Controller
@RequestMapping("/manage/teacher")
@Api(value = "老师管理", description = "老师管理")
public class UcenterTeacherController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterTeacherController.class);

    @Autowired
    private UcenterTeacherService ucenterTeacherService;

    @Autowired
    private UpmsApiService upmsApiService;

    @Autowired
    private UcenterTeacherSpecialtyService ucenterTeacherSpecialtyService;

    @Autowired
    private UcenterTeacherClassService ucenterTeacherClassService;

    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;

    @ApiOperation("老师管理首页")
    @RequiresPermissions("ucenter:teacher:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        _log.debug(">>>>> 老师管理系统 <<<<<");
        model.addAttribute("upmsUser", getCurrentUpmsUser());
        return "/manage/teacher/index.jsp";
    }

    @ApiOperation("老师管理列表")
    @RequiresPermissions("ucenter:teacher:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
                       @RequestParam(required = false, defaultValue = "", value = "search_education") String search_education,
                       @RequestParam(required = false, defaultValue = "", value = "search_politics") String search_politics,
                       @RequestParam(required = false, defaultValue = "", value = "search_sex") String search_sex,
                       @RequestParam(required = false, defaultValue = "", value = "serarch_teacherName") String serarch_teacherName,
                       @RequestParam(required = false, value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order, ModelMap modelMap) {
        UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
        UcenterTeacherExample.Criteria criteria = ucenterTeacherExample.createCriteria();
        //传入-1表示查询所有数据
        if (limit == -1) {
            limit = 0;
        }
        if (StringUtils.isNotBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterTeacherExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }

        //条件查询
        if (StringUtils.isNotBlank(search_education)) {
            criteria.andEducationEqualTo(search_education);
        }
        if (StringUtils.isNotBlank(search_politics)) {
            criteria.andPoliticsEqualTo(search_politics);
        }
        if (StringUtils.isNotBlank(search_sex)) {
            criteria.andSexEqualTo(search_sex);
        }
        if (StringUtils.isNotBlank(serarch_teacherName)) {
            criteria.andTeacherNameLike("%" + serarch_teacherName + "%");
        }

        List<UcenterTeacher> rows = ucenterTeacherService.selectByExampleForOffsetPage(ucenterTeacherExample, offset, limit);
        long total = ucenterTeacherService.countByExample(ucenterTeacherExample);

        //名称装换
        if(rows!=null&&rows.size()>0){
            UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
            UcenterDepartmentExample.Criteria criteria1=ucenterDepartmentExample.createCriteria();
            if (StringUtils.isNotBlank(schoolCode)) {
                criteria1.andSchoolCodeEqualTo(schoolCode);
            }
            criteria1.andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterDepartment> ucenterDepartments=ucenterDepartmentService.selectByExample(ucenterDepartmentExample);
            if (ucenterDepartments != null && ucenterDepartments.size() > 0) {
                //名称转换
                for(int i=0;i<rows.size();i++) {
                    for (int j = 0; j < ucenterDepartments.size(); j++) {
                        if (rows.get(i).getDepartmentId() != null && rows.get(i).getDepartmentId().equals(ucenterDepartments.get(j).getDepartmentId())) {
                            rows.get(i).setDepartmentName(ucenterDepartments.get(j).getDepartmentName());
                            continue;
                        }
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);

        modelMap.put("schoolCode", schoolCode);
        return result;
    }

    @ApiOperation(value = "打开新增老师")
    @RequiresPermissions("ucenter:teacher:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap, @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode) {
        modelMap.put("schoolCode", schoolCode);
        return "/manage/teacher/create.jsp";
    }

    @ApiOperation(value = "新增老师")
    @RequiresPermissions("ucenter:teacher:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(
            UcenterTeacher ucenterTeacher,
            @RequestParam(required = true, defaultValue = "", value = "schoolCode") String schoolCode,
            @RequestParam(required = false, defaultValue = "", value = "classIds") String classIds,
            @RequestParam(required = false, defaultValue = "", value = "specialtyCodes") String specialtyCodes,HttpServletRequest request
    ) throws Exception {
        String depIds=request.getParameter("deps");
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterTeacher.getTeacherName(), new LengthValidator(1, 64, "老师姓名"))
                .on(ucenterTeacher.getTeacherCode(), new LengthValidator(1, 64, "教工号"))
                .on(ucenterTeacher.getPhone(), new LengthValidator(1, 128, "手机号"))
                .on(ucenterTeacher.getIdcard(), new LengthValidator(1, 128, "身份证号"))
                .doValidate()
                .result(ResultCollectors.toComplex());

        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        if (StringUtils.isBlank(schoolCode)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("请先选择学校!"));
        }

        long time = System.currentTimeMillis();
        ucenterTeacher.setCtime(time);
        ucenterTeacher.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        ucenterTeacher.setSchoolCode(schoolCode);

        String msg = checkAddExist(ucenterTeacher);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }
        //新增老师
        int count = 0;
        try {
            count = ucenterTeacherService.insertTeacher(ucenterTeacher, schoolCode, specialtyCodes, classIds,depIds);
        } catch (Exception e) {
            if (e instanceof BusindessException) {
                return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(((BusindessException) e).getName()));
            } else {
                throw e;
            }
        }

        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    /**
     * 检查是否已经存在
     *
     * @param ucenterTeacher
     * @return
     */
    public String checkUpdateExist(UcenterTeacher ucenterTeacher) {
        UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();

        if (StringUtils.isNotBlank(ucenterTeacher.getPhone())) {
            ucenterTeacherExample.or()
                    .andPhoneEqualTo(ucenterTeacher.getPhone())
                    .andTeacherIdNotEqualTo(ucenterTeacher.getTeacherId());
        }

        int i = ucenterTeacherService.countByExample(ucenterTeacherExample);

        if (i > 0) {
            return "手机号重复";
        }


        ucenterTeacherExample.clear();
        if (StringUtils.isNotBlank(ucenterTeacher.getIdcard())) {
            ucenterTeacherExample.or()
                    .andIdcardEqualTo(ucenterTeacher.getIdcard())
                    .andTeacherIdNotEqualTo(ucenterTeacher.getTeacherId());
        }

        int i1 = ucenterTeacherService.countByExample(ucenterTeacherExample);

        if (i1 > 0) {
            return "身份证号重复";
        }

        return null;
    }

    /**
     * 检查是否已经存在
     *
     * @param ucenterTeacher
     * @return
     */
    public String checkAddExist(UcenterTeacher ucenterTeacher) {
        UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();

        // 手机号重复判断
        if (StringUtils.isNotBlank(ucenterTeacher.getPhone())) {
            ucenterTeacherExample.or()
                    .andPhoneEqualTo(ucenterTeacher.getPhone());
        }

        int i = ucenterTeacherService.countByExample(ucenterTeacherExample);

        if (i > 0) {
            return "手机号重复";
        }

        ucenterTeacherExample.clear();

        if (StringUtils.isNotBlank(ucenterTeacher.getIdcard())) {
            ucenterTeacherExample.or()
                    .andIdcardEqualTo(ucenterTeacher.getIdcard());
        }

        int i1 = ucenterTeacherService.countByExample(ucenterTeacherExample);

        if (i1 > 0) {
            return "身份证号重复";
        }

        return null;
    }


    @ApiOperation(value = "删除老师")
    @RequiresPermissions("ucenter:teacher:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) throws Exception {
        int count = ucenterTeacherService.deleteTeacher(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "打开修改老师")
    @RequiresPermissions("ucenter:teacher:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id,
                         ModelMap modelMap,
                         @ModelAttribute("model") UcenterTeacher ucenterTeacher) {

        //名称装换
        if(ucenterTeacher!=null&&ucenterTeacher.getDepartmentId()!=null){
            UcenterDepartment ucenterDepartment=ucenterDepartmentService.selectByPrimaryKey(ucenterTeacher.getDepartmentId());
            if (ucenterDepartment != null) {
                //名称转换
                ucenterTeacher.setDepartmentName(ucenterDepartment.getDepartmentName());
            }
        }

        modelMap.put("ucenterTeacher", ucenterTeacher);

        UcenterTeacherSpecialtyExample ucenterTeacherSpecialtyExample = new UcenterTeacherSpecialtyExample();
        ucenterTeacherSpecialtyExample.createCriteria().andTeacherIdEqualTo(id);
        List<UcenterTeacherSpecialty> ucenterTeacherSpecialties = ucenterTeacherSpecialtyService.selectByExample(ucenterTeacherSpecialtyExample);

        UcenterTeacherClassExample ucenterTeacherClassExample = new UcenterTeacherClassExample();
        ucenterTeacherClassExample.createCriteria().andTeacherIdEqualTo(id);
        List<UcenterTeacherClass> ucenterTeacherClasses = ucenterTeacherClassService.selectByExample(ucenterTeacherClassExample);

        StringBuilder specialtyCodes = new StringBuilder();
        StringBuilder classIds = new StringBuilder();

        if (CollectionUtils.isNotEmpty(ucenterTeacherSpecialties)) {
            for (int i = 0; i < ucenterTeacherSpecialties.size(); i++) {
                specialtyCodes.append(ucenterTeacherSpecialties.get(i).getSpecialtyCode());
                if (i != ucenterTeacherSpecialties.size() - 1) {
                    specialtyCodes.append(",");
                }
            }
        }

        if (CollectionUtils.isNotEmpty(ucenterTeacherClasses)) {
            for (int i = 0; i < ucenterTeacherClasses.size(); i++) {
                classIds.append(ucenterTeacherClasses.get(i).getClassId());
                if (i != ucenterTeacherClasses.size() - 1) {
                    classIds.append(",");
                }
            }
        }

        modelMap.put("specialtyCodes", specialtyCodes.toString());
        modelMap.put("classIds", classIds.toString());

        return "/manage/teacher/update.jsp";
    }

    @ApiOperation("修改老师")
    @RequiresPermissions("ucenter:teacher:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, @ModelAttribute("model") UcenterTeacher ucenterTeacher,
                         @RequestParam(required = false, defaultValue = "", value = "classIds") String classIds,
                         @RequestParam(required = false, defaultValue = "", value = "specialtyCodes") String specialtyCodes,HttpServletRequest request
    ) throws Exception {
        String depIds=request.getParameter("deps");
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterTeacher.getTeacherName(), new LengthValidator(1, 64, "老师姓名"))
                .on(ucenterTeacher.getTeacherCode(), new LengthValidator(1, 64, "教工号"))
                .on(ucenterTeacher.getPhone(), new LengthValidator(1, 128, "手机号"))
                .on(ucenterTeacher.getIdcard(), new LengthValidator(1, 128, "身份证号"))
                .doValidate()
                .result(ResultCollectors.toComplex());

        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        String msg = checkUpdateExist(ucenterTeacher);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }

        long time = System.currentTimeMillis();
        ucenterTeacher.setCtime(time);
        ucenterTeacher.setTeacherId(id);

        int count = ucenterTeacherService.updateTeacher(ucenterTeacher, specialtyCodes, classIds,depIds);
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


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Override
    @ModelAttribute("model")
    public UcenterTeacher getModel(
            @PathVariable(value = "id", required = false) Integer id) {
        if (id != null) {
            return ucenterTeacherService.selectByPrimaryKey(id);
        }
        return null;
    }

    @ApiOperation(value = "导入老师")
    @RequiresPermissions("ucenter:teacher:create")
    @RequestMapping(value = "/upload" , method = RequestMethod.GET)
    public String upload(ModelMap modelMap, @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode) {
        modelMap.put("schoolCode", schoolCode);
        modelMap.put("baseModel", BaseConstants.ImportModel.BASEMODEL_TEACHER);//模板名称定义
        return "/manage/upload/upload.jsp";
    }
}
