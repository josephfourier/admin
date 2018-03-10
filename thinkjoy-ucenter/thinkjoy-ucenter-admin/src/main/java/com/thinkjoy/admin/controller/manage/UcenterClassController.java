package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.google.common.collect.Lists;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.*;
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
import java.util.*;

/**
 * Created by gengsongbo on 2017/10/23
 */
@Controller
@RequestMapping("/manage/class")
@Api(value = "学校班级管理", description = "学校班级管理")
public class UcenterClassController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterClassController.class);

    @Autowired
    private UpmsUserService upmsUserService;

    @Autowired
    private UcenterFacultyService ucenterFacultyService;

    @Autowired
    private UcenterSpecialtyService ucenterSpecialtyService;

    @Autowired
    private UcenterClassService ucenterClassService;

    @Autowired
    private UcenterTeacherService ucenterTeacherService;

    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;

    @Autowired
    private UcenterDepartmentBusService ucenterDepartmentBusService;

    @ApiOperation("班级管理首页")
    @RequiresPermissions("ucenter:class:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser = upmsUserService.selectFirstByExample(upmsUserExample);
        modelMap.put("upmsUser", upmsUser);
        return "/manage/class/index.jsp";
    }

    @ApiOperation("班级管理列表")
    @RequiresPermissions("ucenter:class:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
                       @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                       @RequestParam(required = false, defaultValue = "", value = "search_year") String year,
                       @RequestParam(required = false, defaultValue = "", value = "search_specialty") String specialty,
                       @RequestParam(required = false, defaultValue = "", value = "search_faculty") String faculty,
                       @RequestParam(required = false, defaultValue = "", value = "serarch_className") String serarch_className,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order, ModelMap modelMap) {
        UcenterClassExample ucenterClassExample = new UcenterClassExample();
        UcenterClassExample.Criteria criteria = ucenterClassExample.createCriteria();
        //传入-1表示查询所有数据
        if (limit == -1) {
            limit = 0;
        }
        if (!"".equals(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterClassExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(serarch_className)) {
            criteria.andClassNameLike("%" + serarch_className + "%");
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }
        //新增三个查询
        if (StringUtils.isNotBlank(year)) {
            criteria.andYearEqualTo(Integer.parseInt(year));
        }
        if (StringUtils.isNotBlank(faculty)) {
            criteria.andFacultyCodeEqualTo(faculty);
        }
        if (StringUtils.isNotBlank(specialty)) {
            String[] split = specialty.split(",");
            if (split != null && split.length > 1) {
                criteria.andSpecialtyCodeIn(Lists.newArrayList(split));
            }else {
                criteria.andSpecialtyCodeEqualTo(specialty);
            }
        }

        List<UcenterClass> rows = ucenterClassService.selectByExampleForOffsetPage(ucenterClassExample, offset, limit);
        long total = ucenterClassService.countByExample(ucenterClassExample);

        //学校名称装换
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

            //老师
            UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
            UcenterTeacherExample.Criteria criteria3 = ucenterTeacherExample.createCriteria();
            if (StringUtils.isNotBlank(schoolCode)) {
                criteria3.andSchoolCodeEqualTo(schoolCode);
            }
            criteria3.andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterTeacher> ucenterTeachers=ucenterTeacherService.selectByExample(ucenterTeacherExample);
            //名称装换
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
                    if(ucenterTeachers!=null&&ucenterTeachers.size()>0){
                        for(int j=0;j<ucenterTeachers.size();j++){
                            if(rows.get(i).getBzrId()!=null&&rows.get(i).getBzrId().intValue()==ucenterTeachers.get(j).getTeacherId().intValue()){
                                rows.get(i).setBzrName(ucenterTeachers.get(j).getTeacherName());
                            }
                            if(rows.get(i).getFdyId()!=null&&rows.get(i).getFdyId().intValue()==ucenterTeachers.get(j).getTeacherId().intValue()){
                                rows.get(i).setFdyName(ucenterTeachers.get(j).getTeacherName());
                            }
                        }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "打开新增班级页面")
    @RequiresPermissions("ucenter:class:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(required = false, defaultValue = "", value = "userType") String userType,
                         @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
                         ModelMap modelMap) {
		setdata(modelMap,schoolCode);
        modelMap.put("userType", userType);
        modelMap.put("schoolCode", schoolCode);
        return "/manage/class/create.jsp";
    }

    @ApiOperation(value = "新增班级")
    @RequiresPermissions("ucenter:class:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterClass ucenterClass,
                         HttpServletRequest request) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterClass.getClassName(), new LengthValidator(1, 64, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
        UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();
        criteria.andDepartmentNameEqualTo(ucenterClass.getFacultyName()).andSchoolCodeEqualTo(ucenterClass.getSchoolCode());
        UcenterDepartment ucenterDepartment1=ucenterDepartmentService.selectFirstByExample(ucenterDepartmentExample);

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterClass.setCreator(username);
//        ucenterClass.setYear(year);
        long time = System.currentTimeMillis();
        ucenterClass.setCtime(time);

        UcenterDepartment ucenterDepartment=new UcenterDepartment();
        ucenterDepartment.setParentId(ucenterDepartment1.getDepartmentId());
        ucenterDepartment.setSchoolCode(ucenterClass.getSchoolCode());
        ucenterDepartment.setDepartmentName(ucenterClass.getClassName());
        ucenterDepartment.setDepartmentType(UcenterConstant.DeptType.YX);
        ucenterDepartment.setCreator(username);
        ucenterDepartment.setCtime(time);

        int count = ucenterClassService.insertSelective(ucenterClass,ucenterDepartment);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation("查询名称是否重复")
    @ResponseBody
    @RequestMapping(value="/checkName",method = RequestMethod.POST)
    public boolean checkName(String className,String schoolCode) {
        boolean isExit = false;
        UcenterClassExample ucenterClassExample = new UcenterClassExample();
        UcenterClassExample.Criteria criteria = ucenterClassExample.createCriteria();

        if (StringUtils.isNotBlank(className)) {
            criteria.andClassNameEqualTo(className).andSchoolCodeEqualTo(schoolCode);
        }


        int count= ucenterClassService.countByExample(ucenterClassExample);
        if (count>0) {
            isExit = true;
        }
        return  isExit;
    }

	@ApiOperation(value = "克隆班级")
	@RequiresPermissions("ucenter:class:clone")
	@ResponseBody
	@RequestMapping(value = "/clone", method = RequestMethod.POST)
	public Object clone(HttpServletRequest request,
						@RequestParam(required = false,defaultValue = "",value = "schoolCode")String schoolCode){
		ComplexResult result = FluentValidator.checkAll()
				.doValidate()
				.result(ResultCollectors.toComplex());
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		long time = System.currentTimeMillis();

		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		Integer year=Integer.valueOf(yeardate).intValue();
		Integer byear=year-1;
		int count=0;
		UcenterClassExample ucenterClassExample=new UcenterClassExample();
		ucenterClassExample.createCriteria().andYearEqualTo(byear).andSchoolCodeEqualTo(schoolCode);
		List<UcenterClass> classList= ucenterClassService.selectByExample(ucenterClassExample);
		for(UcenterClass classbean:classList){
			UcenterClass ucenterClass=new UcenterClass();
			ucenterClass.setYear(year);
			ucenterClass.setCreator(username);
			ucenterClass.setCtime(time);
			ucenterClass.setClassName(classbean.getClassName());
			ucenterClass.setBzrId(classbean.getBzrId());
			ucenterClass.setBzrName(classbean.getBzrName());
			ucenterClass.setFdyId(classbean.getFdyId());
			ucenterClass.setFdyName(classbean.getFdyName());
			ucenterClass.setFacultyCode(classbean.getFacultyCode());
			ucenterClass.setFacultyName(classbean.getFacultyName());
			ucenterClass.setSpecialtyCode(classbean.getSpecialtyCode());
			ucenterClass.setSpecialtyName(classbean.getSpecialtyName());
			ucenterClass.setDescription(classbean.getDescription());
			ucenterClass.setStatus(classbean.getStatus());
			ucenterClass.setSchoolCode(classbean.getSchoolCode());
			count +=ucenterClassService.insertSelective(ucenterClass);
		}
		if(count>0){
			return new UcenterResult(UcenterResultConstant.SUCCESS,count);
		}else{
			return new UcenterResult(UcenterResultConstant.FAILED,count);
		}
	}

    @ApiOperation(value = "删除班级")
    @RequiresPermissions("ucenter:class:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] idArray = ids.split("-");
        int count = 0;
        for (String idStr : idArray) {
            if (StringUtils.isBlank(idStr)) {
                continue;
            }
            Integer classId = Integer.parseInt(idStr);
            UcenterDepartmentBusExample ucenterDepartmentBusExample = new UcenterDepartmentBusExample();
            ucenterDepartmentBusExample.createCriteria().andBusIdEqualTo(classId);
            UcenterDepartmentBus departmentBus=ucenterDepartmentBusService.selectFirstByExample(ucenterDepartmentBusExample);

            count = ucenterClassService.deletePrimaryKey(classId, departmentBus);
        }
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "打开修改班级页面")
    @RequiresPermissions("ucenter:class:update")
    @RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, @PathVariable("userType") String userType, ModelMap modelMap) {
        UcenterClass ucenterClass = ucenterClassService.selectByPrimaryKey(id);
		String schoolCode = ucenterClass.getSchoolCode();
		setdata(modelMap,schoolCode);
        modelMap.put("userType", userType);
        modelMap.put("ucenterClass", ucenterClass);
        return "/manage/class/update.jsp";
    }

    @ApiOperation("修改班级")
    @RequiresPermissions("ucenter:class:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,
                         @RequestParam(required = false, defaultValue = "", value = "areaType") String areaType,
                         UcenterClass ucenterClass) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterClass.getClassName(), new LengthValidator(1, 64, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        UcenterClassExample ucenterClassExample = new UcenterClassExample();
        ucenterClassExample.createCriteria().andClassNameEqualTo(ucenterClass.getClassName()).andSchoolCodeEqualTo(ucenterClass.getSchoolCode());

        UcenterClass ucenterClass1= ucenterClassService.selectFirstByExample(ucenterClassExample);
        if(ucenterClass1!=null&&!ucenterClass1.getClassId().equals(id)){
            return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("班级名称已经存在!"));
        }

        UcenterDepartmentBusExample ucenterDepartmentBusExample = new UcenterDepartmentBusExample();
        ucenterDepartmentBusExample.createCriteria().andBusIdEqualTo(id);


        UcenterDepartmentExample ucenterDepartmentExample1 = new UcenterDepartmentExample();
        UcenterDepartmentExample.Criteria criteria1=ucenterDepartmentExample1.createCriteria();
        criteria1.andDepartmentNameEqualTo(ucenterClass.getFacultyName()).andSchoolCodeEqualTo(ucenterClass.getSchoolCode());
        UcenterDepartment ucenterDepartment1=ucenterDepartmentService.selectFirstByExample(ucenterDepartmentExample1);

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterClass.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterClass.setCtime(time);
        ucenterClass.setClassId(id);


        UcenterDepartmentBus departmentBus=ucenterDepartmentBusService.selectFirstByExample(ucenterDepartmentBusExample);

        UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
        UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();
        Integer departmentId=departmentBus.getDepartmentId();
        criteria.andDepartmentIdEqualTo(departmentId);
        UcenterDepartment ucenterDepartment=this.ucenterDepartmentService.selectFirstByExample(ucenterDepartmentExample);

        ucenterDepartment.setParentId(ucenterDepartment1.getDepartmentId());
        ucenterDepartment.setSchoolCode(ucenterClass.getSchoolCode());
        ucenterDepartment.setDepartmentName(ucenterClass.getClassName());
        ucenterDepartment.setDepartmentType(UcenterConstant.DeptType.YX);
        ucenterDepartment.setCreator(username);
        ucenterDepartment.setCtime(time);

        int count = ucenterClassService.updateByPrimaryKeySelective(ucenterClass,departmentBus,ucenterDepartment);

        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

	@ApiOperation("专业管理列表")
	@RequestMapping(value="/specialtylist",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
					   @RequestParam(required = false,defaultValue = "",value = "facultyCode")String facultyCode){
		UcenterSpecialtyExample ucenterSpecialtyExample=new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria=ucenterSpecialtyExample.createCriteria();
		//传入-1表示查询所有数据
		if (limit == -1) {
			limit = 0;
		}
//		Calendar date = Calendar.getInstance();
//		String yeardate = String.valueOf(date.get(Calendar.YEAR));
//		Integer year=Integer.valueOf(yeardate).intValue();
		if (StringUtils.isNotBlank(schoolCode)) {
			criteria.andSchoolCodeEqualTo(schoolCode).andFacultyCodeEqualTo(facultyCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		}

		List<UcenterSpecialty> rows=ucenterSpecialtyService.selectByExampleForOffsetPage(ucenterSpecialtyExample, offset, limit);
		long total = ucenterSpecialtyService.countByExample(ucenterSpecialtyExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	/**
	 * 查询字典数据
	 */
	public void setdata(ModelMap modelMap,String schoolCode){
		Integer year=0;
		Integer byear=0;
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		try {
			year = Integer.valueOf(yeardate).intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria = ucenterFacultyExample.createCriteria();

		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria1 = ucenterSpecialtyExample.createCriteria();

		UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
		UcenterTeacherExample.Criteria teacher = ucenterTeacherExample.createCriteria();

		if (StringUtils.isNotBlank(schoolCode)) {
			criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			criteria1.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			teacher.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		}
		//获取该学校院系列表
		List<UcenterFaculty> facultyList = ucenterFacultyService.selectByExample(ucenterFacultyExample);
		//获取该学校专业列表
		List<UcenterSpecialty> specialtyList = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
		//获取该学校老师列表
		List<UcenterTeacher> teacherList = ucenterTeacherService.selectByExample(ucenterTeacherExample);

		modelMap.put("specialtyList", specialtyList);
		modelMap.put("facultyList", facultyList);
		modelMap.put("teacherList", teacherList);

		//获取学年列表
		List list = new ArrayList();
		for(int i=0;i<5;i++){
			byear=year-i;
			list.add(byear);
		}
		modelMap.put("list", list);
	}
}
