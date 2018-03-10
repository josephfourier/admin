package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.common.validator.NotNullValidator;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengsongbo
 * @date 2017/09/20
 */
@Controller
@RequestMapping("/manage/student")
@Api(value = "学生管理", description = "学生管理")
public class UcenterStudentController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterStudentController.class);

    @Autowired
    private UcenterStudentService ucenterStudentService;

    @Autowired
    private UpmsApiService upmsApiService;

    @Autowired
    private UcenterUserService ucenterUserService;

    @Autowired
    private UcenterFacultyService ucenterFacultyService;

    @Autowired
    private UcenterSpecialtyService ucenterSpecialtyService;

    @Autowired
    private UcenterClassService ucenterClassService;

    @ApiOperation("学生管理首页")
    @RequiresPermissions("ucenter:student:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        _log.debug(">>>>> 学生管理系统 <<<<<");
        model.addAttribute("upmsUser", getCurrentUpmsUser());
        return "/manage/student/index.jsp";
    }

    @ApiOperation("学生管理列表")
    @RequiresPermissions("ucenter:student:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, defaultValue = "", value = "userType") String userType,
                       @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
                       @RequestParam(required = false, defaultValue = "", value = "search_studentType") String search_studentType,
                       @RequestParam(required = false, defaultValue = "", value = "search_faculty") String search_faculty,
                       @RequestParam(required = false, defaultValue = "", value = "search_specialty") String search_specialty,
                       @RequestParam(required = false, defaultValue = "", value = "search_class") String search_class,
                       @RequestParam(required = false, defaultValue = "", value = "serarch_studentName") String serarch_studentName,
                       @RequestParam(required = false, value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order, ModelMap modelMap) {
        UcenterStudentExample ucenterStudentExample = new UcenterStudentExample();
        UcenterStudentExample.Criteria criteria = ucenterStudentExample.createCriteria();
        //传入-1表示查询所有数据
        if (limit == -1) {
            limit = 0;
        }
        if (StringUtils.isNotBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterStudentExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }

        //条件查询
//        if (StringUtils.isNotBlank(search_year)) {
//            criteria.andYearEqualTo(Integer.parseInt(search_year));
//        }
        if (StringUtils.isNotBlank(search_year)) {
            criteria.andEnterYearEqualTo(Integer.parseInt(search_year));
        }
        if (StringUtils.isNotBlank(search_studentType)) {
            criteria.andStudentTypeEqualTo(search_studentType);
        }
        if (StringUtils.isNotBlank(search_faculty)) {
            criteria.andFacultyCodeEqualTo(search_faculty);
        }
        if (StringUtils.isNotBlank(search_specialty)) {
            criteria.andSpecialtyCodeEqualTo(search_specialty);
        }
        if (StringUtils.isNotBlank(search_class)) {
            criteria.andClassIdEqualTo(Integer.parseInt(search_class));
        }
        if (StringUtils.isNotBlank(serarch_studentName)) {
            criteria.andStudentNameLike("%" + serarch_studentName + "%");
        }

        List<UcenterStudent> rows = ucenterStudentService.selectByExampleForOffsetPage(ucenterStudentExample, offset, limit);
        long total = ucenterStudentService.countByExample(ucenterStudentExample);

        //名称装换
        if(rows!=null&&rows.size()>0){
            //院系
            UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
            UcenterFacultyExample.Criteria criteria1=ucenterFacultyExample.createCriteria();
            if (StringUtils.isNotBlank(schoolCode)) {
                criteria1.andSchoolCodeEqualTo(schoolCode);
            }
            criteria1.andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterFaculty> ucenterFaculties=ucenterFacultyService.selectByExample(ucenterFacultyExample);
            //专业
            UcenterSpecialtyExample ucenterSpecialtyExample=new UcenterSpecialtyExample();
            UcenterSpecialtyExample.Criteria criteria2=ucenterSpecialtyExample.createCriteria();
            if (StringUtils.isNotBlank(schoolCode)) {
                criteria2.andSchoolCodeEqualTo(schoolCode);
            }
            criteria2.andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterSpecialty> ucenterSpecialties=ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);

            //班级
            UcenterClassExample ucenterClassExample = new UcenterClassExample();
            UcenterClassExample.Criteria criteria3= ucenterClassExample.createCriteria();
            if (StringUtils.isNotBlank(schoolCode)) {
                criteria3.andSchoolCodeEqualTo(schoolCode);
            }
            criteria3.andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterClass> ucenterClasses=ucenterClassService.selectByExample(ucenterClassExample);

            //名称转换
            for(int i=0;i<rows.size();i++){
                if(ucenterFaculties!=null&&ucenterFaculties.size()>0){
                    for(int j=0;j<ucenterFaculties.size();j++){
                        if(rows.get(i).getFacultyCode()!=null&&rows.get(i).getFacultyCode().equals(ucenterFaculties.get(j).getFacultyCode())){
                            rows.get(i).setFacultyName(ucenterFaculties.get(j).getFacultyName());
                            continue;
                        }
                    }
                }
                if(ucenterSpecialties!=null&&ucenterSpecialties.size()>0){
                    for(int j=0;j<ucenterSpecialties.size();j++){
                        if(rows.get(i).getSpecialtyCode()!=null&&rows.get(i).getSpecialtyCode().equals(ucenterSpecialties.get(j).getSpecialtyCode())){
                            rows.get(i).setSpecialtyName(ucenterSpecialties.get(j).getSpecialtyName());
                            continue;
                        }
                    }
                }
                if(ucenterClasses!=null&&ucenterClasses.size()>0){
                    for(int j=0;j<ucenterClasses.size();j++){
                        if(rows.get(i).getClassId()!=null&&rows.get(i).getClassId().equals(ucenterClasses.get(j).getClassId())){
                            rows.get(i).setClassName(ucenterClasses.get(j).getClassName());
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

    @ApiOperation(value = "打开新增学生")
    @RequiresPermissions("ucenter:student:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap, @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode) {
        modelMap.put("schoolCode", schoolCode);
        return "/manage/student/create.jsp";
    }

    @ApiOperation(value = "新增学生")
    @RequiresPermissions("ucenter:student:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(
            UcenterStudent ucenterStudent,
            @RequestParam(required = true, defaultValue = "", value = "schoolCode") String schoolCode) throws Exception {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterStudent.getStudentName(), new LengthValidator(1, 64, "学生姓名"))
                .on(ucenterStudent.getPhone(), new NotNullValidator("手机号"))
                .on(ucenterStudent.getIdcard(), new NotNullValidator("身份证号"))
                .on(ucenterStudent.getSex(), new NotNullValidator("性别"))
                .on(ucenterStudent.getStudentCode(), new NotNullValidator("学号"))
                .on(ucenterStudent.getFacultyCode(), new NotNullValidator("院系code"))
                .on(ucenterStudent.getFacultyName(), new NotNullValidator("院系名称"))
                .on(ucenterStudent.getSpecialtyCode(), new NotNullValidator("专业code"))
                .on(ucenterStudent.getSpecialtyName(), new NotNullValidator("专业名称"))
                .on(String.valueOf(ucenterStudent.getClassId()), new NotNullValidator("班级ID"))
                .on(ucenterStudent.getClassName(), new NotNullValidator("班级名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (StringUtils.isBlank(schoolCode)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("学校编码为空!"));
        }
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, result.getErrors());
        }

        long time = System.currentTimeMillis();
        ucenterStudent.setCtime(time);
        ucenterStudent.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        //默认入学年份用当年
        if(ucenterStudent.getEnterYear()==null||ucenterStudent.getEnterYear().equals("")){
            ucenterStudent.setEnterYear(Integer.valueOf(DateUtil.getYear()));
        }
        ucenterStudent.setYear(Integer.parseInt(DateUtil.getYear()));
        ucenterStudent.setSchoolCode(schoolCode);

        String msg = checkAddExist(ucenterStudent);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }

        int count = 0;
        try {
            count = ucenterStudentService.insertStudent(ucenterStudent, schoolCode);
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
     * @param ucenterStudent
     * @return
     */
    public String checkAddExist(UcenterStudent ucenterStudent) {
        UcenterStudentExample ucenterStudentExample = new UcenterStudentExample();
        if (StringUtils.isNotBlank(ucenterStudent.getPhone())) {
            ucenterStudentExample.or()
                    .andPhoneEqualTo(ucenterStudent.getPhone());
        }

        int i = ucenterStudentService.countByExample(ucenterStudentExample);

        if (i > 0) {
            return "手机号重复";
        }

        ucenterStudentExample.clear();
        if (StringUtils.isNotBlank(ucenterStudent.getIdcard())) {
            ucenterStudentExample.or()
                    .andIdcardEqualTo(ucenterStudent.getIdcard());
        }

        int i1 = ucenterStudentService.countByExample(ucenterStudentExample);

        if (i1 > 0) {
            return "身份证号重复";
        }

        return null;
    }

    /**
     * 检查是否已经存在
     *
     * @param ucenterStudent
     * @return
     */
    public String checkUpdateExist(UcenterStudent ucenterStudent) {
        UcenterStudentExample ucenterStudentExample = new UcenterStudentExample();

        if (StringUtils.isNotBlank(ucenterStudent.getPhone())) {
            ucenterStudentExample.or()
                    .andPhoneEqualTo(ucenterStudent.getPhone())
                    .andStudentIdNotEqualTo(ucenterStudent.getStudentId());
        }

        int i = ucenterStudentService.countByExample(ucenterStudentExample);

        if (i > 0) {
            return "手机号重复";
        }

        ucenterStudentExample.clear();

        if (StringUtils.isNotBlank(ucenterStudent.getIdcard())) {
            ucenterStudentExample.or()
                    .andIdcardEqualTo(ucenterStudent.getIdcard())
                    .andStudentIdNotEqualTo(ucenterStudent.getStudentId());
        }


        int i1 = ucenterStudentService.countByExample(ucenterStudentExample);

        if (i1 > 0) {
            return "身份证号重复";
        }

        return null;
    }

    @ApiOperation(value = "删除学生")
    @RequiresPermissions("ucenter:student:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) throws Exception {
        int count = ucenterStudentService.deleteStudent(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "打开修改学生")
    @RequiresPermissions("ucenter:student:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id,
                         ModelMap modelMap,
                         @ModelAttribute("model") UcenterStudent ucenterStudent) {
        modelMap.put("ucenterStudent", ucenterStudent);
        return "/manage/student/update.jsp";
    }

    @ApiOperation("修改学生")
    @RequiresPermissions("ucenter:student:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, @ModelAttribute("model") UcenterStudent ucenterStudent) throws Exception {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterStudent.getStudentName(), new LengthValidator(1, 64, "学生姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        long time = System.currentTimeMillis();
        ucenterStudent.setCtime(time);
        ucenterStudent.setStudentId(id);

        String msg = checkUpdateExist(ucenterStudent);
        if (StringUtils.isNotBlank(msg)) {
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg(msg));
        }
        int count = ucenterStudentService.updateStudent(ucenterStudent);
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
    public UcenterStudent getModel(
            @PathVariable(value = "id", required = false) Integer id) {
        if (id != null) {
            return ucenterStudentService.selectByPrimaryKey(id);
        }
        return null;
    }

    @ApiOperation(value = "导入学生")
    @RequiresPermissions("ucenter:student:create")
    @RequestMapping(value = "/upload" , method = RequestMethod.GET)
    public String upload(ModelMap modelMap, @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode) {
        modelMap.put("schoolCode", schoolCode);
        modelMap.put("baseModel", BaseConstants.ImportModel.BASEMODEL_STUDENT);//模板名称定义
        return "/manage/upload/upload.jsp";
    }

}
