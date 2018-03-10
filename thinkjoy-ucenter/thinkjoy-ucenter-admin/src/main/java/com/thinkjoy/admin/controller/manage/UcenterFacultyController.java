package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentBusService;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentService;
import com.thinkjoy.ucenter.rpc.api.UcenterFacultyService;
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
 * Created by gengsongbo on 2017/10/13.
 */
@Controller
@RequestMapping("/manage/faculty")
@Api(value ="院系管理",description = "院系管理")
public class UcenterFacultyController extends BaseController {
	private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterFacultyController.class);

	@Autowired
	private UpmsUserService upmsUserService;

	@Autowired
	private UcenterDepartmentService ucenterDepartmentService;

	@Autowired
	private UcenterDepartmentBusService ucenterDepartmentBusService;

	@Autowired
	private UcenterFacultyService ucenterFacultyService;

	@ApiOperation("院系管理首页")
	@RequiresPermissions("ucenter:faculty:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		modelMap.put("upmsUser",upmsUser);
		return "/manage/faculty/index.jsp";
	}

	@ApiOperation("院系管理列表")
	@RequiresPermissions("ucenter:faculty:read")
	@RequestMapping(value="/list",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "type") String type,
					   @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
					   @RequestParam(required = false, defaultValue = "", value = "search_year") String year,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_facultyCode") String serarch_facultyCode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_facultyName") String serarch_facultyName,
					   @RequestParam(required = false, value = "sort") String sort,
					   @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();
		//传入-1表示查询所有数据
		if (limit == -1) {
			limit = 0;
		}
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			ucenterFacultyExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(serarch_facultyCode)) {
			criteria.andFacultyCodeLike("%" + serarch_facultyCode + "%");
		}
		if (StringUtils.isNotBlank(serarch_facultyName)) {
			criteria.andFacultyNameLike("%" + serarch_facultyName + "%");
		}
		if (StringUtils.isNotBlank(schoolCode)) {
			criteria.andSchoolCodeEqualTo(schoolCode);
		}
		//新增三个查询
		if (StringUtils.isNotBlank(year)) {
			criteria.andYearEqualTo(Integer.parseInt(year));
		}
		List<UcenterFaculty> rows=ucenterFacultyService.selectByExampleForOffsetPage(ucenterFacultyExample,offset,limit);
		long total = ucenterFacultyService.countByExample(ucenterFacultyExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	/**
	 * 查院系编码否重复
	 * @param facultyCode
	 * @return
	 */
	@ApiOperation("查询院系编码是否重复")
	@ResponseBody
	@RequestMapping(value="/checkcode",method = RequestMethod.POST)
	public boolean checkcode(String facultyCode,String schoolCode) {
		boolean isExit = false;
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		Integer year=Integer.valueOf(yeardate).intValue();
		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		ucenterFacultyExample.createCriteria().andFacultyCodeEqualTo(facultyCode).andSchoolCodeEqualTo(schoolCode).andYearEqualTo(year);
		UcenterFaculty ucenterFaculty = ucenterFacultyService.selectFirstByExample(ucenterFacultyExample);
		if (ucenterFaculty != null) {
			isExit = true;
		}
		return  isExit;
	}

	/**
	 * 查询院系编码或名称是否重复
	 * @param facultyCode
	 * @param facultyName
	 * @param schoolCode
	 * @return
	 */
	@ApiOperation("查询院系编码或名称是否重复")
	@ResponseBody
	@RequestMapping(value="/checkCodeorName",method = RequestMethod.POST)
	public boolean checkCodeorName(String facultyCode,String facultyName,String schoolCode) {
		boolean isExit = false;
		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();

		if (StringUtils.isNotBlank(facultyCode)) {
			criteria.andFacultyCodeEqualTo(facultyCode).andSchoolCodeEqualTo(schoolCode);
		}
		if (StringUtils.isNotBlank(facultyName)) {
			criteria.andFacultyNameEqualTo(facultyName).andSchoolCodeEqualTo(schoolCode);
		}

		int count= ucenterFacultyService.countByExample(ucenterFacultyExample);
		if (count>0) {
			isExit = true;
		}
		return  isExit;
	}


	@ApiOperation(value = "打开新增院系页面")
	@RequiresPermissions("ucenter:faculty:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@RequestParam(required = false,defaultValue = "",value = "userType")String userType,
						 @RequestParam(required = false,defaultValue = "",value = "schoolCode")String schoolCode,
						 ModelMap modelMap) {
		setdata(modelMap);
		modelMap.put("userType", userType);
		modelMap.put("schoolCode", schoolCode);
		return "/manage/faculty/create.jsp";
	}

	@ApiOperation(value = "新增院系")
	@RequiresPermissions("ucenter:faculty:create")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object create(UcenterFaculty ucenterFaculty,
						 HttpServletRequest request){
		ComplexResult result = FluentValidator.checkAll()
				.on(ucenterFaculty.getFacultyName(), new LengthValidator(1, 64, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}


		//生成院系编码
		//ucenterFaculty.setFacultyCode(ucenterFacultyService.getNextCodeByCode(ucenterFaculty.getFacultyCode(),ucenterFaculty.getSchoolCode()));
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterFaculty.setCreator(username);
		long time = System.currentTimeMillis();
		ucenterFaculty.setCtime(time);
		ucenterFaculty.setYear(DateUtil.getCurrentYear());

		UcenterDepartment ucenterDepartment=new UcenterDepartment();
		ucenterDepartment.setSchoolCode(ucenterFaculty.getSchoolCode());
		ucenterDepartment.setDepartmentName(ucenterFaculty.getFacultyName());
		ucenterDepartment.setDepartmentType(UcenterConstant.DeptType.YX);
		ucenterDepartment.setCreator(username);
		ucenterDepartment.setCtime(time);


		int count =ucenterFacultyService.insertSelective(ucenterFaculty,ucenterDepartment);
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "克隆院系")
	@RequiresPermissions("ucenter:faculty:clone")
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
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		ucenterFacultyExample.createCriteria().andYearEqualTo(byear).andSchoolCodeEqualTo(schoolCode);
		List<UcenterFaculty> facultyList= ucenterFacultyService.selectByExample(ucenterFacultyExample);
		for(UcenterFaculty facultybean:facultyList){
			String facultyCode=facultybean.getFacultyCode();
			UcenterFaculty tfacultybean=ucenterFacultyService.getfacultybean(facultyCode, schoolCode, year);
			if(tfacultybean ==null){
				UcenterFaculty 	ucenterFaculty=new UcenterFaculty();
				ucenterFaculty.setYear(year);
				ucenterFaculty.setCreator(username);
				ucenterFaculty.setCtime(time);
				ucenterFaculty.setFacultyCode(facultyCode);
				ucenterFaculty.setFacultyName(facultybean.getFacultyName());
				ucenterFaculty.setBirthday(facultybean.getBirthday());
				ucenterFaculty.setDescription(facultybean.getDescription());
				ucenterFaculty.setFzrName(facultybean.getFzrName());
				ucenterFaculty.setSchoolCode(facultybean.getSchoolCode());
				ucenterFaculty.setTel(facultybean.getTel());
				ucenterFaculty.setStatus(facultybean.getStatus());
				count +=ucenterFacultyService.insertSelective(ucenterFaculty);
			}
		}
		if(count>0){
			return new UcenterResult(UcenterResultConstant.SUCCESS,count);
		}else{
			return new UcenterResult(UcenterResultConstant.FAILED,count);
		}
	}

	@ApiOperation(value = "删除院系")
	@RequiresPermissions("ucenter:faculty:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		String[] idArray = ids.split("-");
		int count = 0;
		for (String idStr : idArray) {
			if (StringUtils.isBlank(idStr)) {
				continue;
			}
			Integer facultyId = Integer.parseInt(idStr);
			UcenterDepartmentBusExample ucenterDepartmentBusExample = new UcenterDepartmentBusExample();
			ucenterDepartmentBusExample.createCriteria().andBusIdEqualTo(facultyId);
			UcenterDepartmentBus departmentBus=ucenterDepartmentBusService.selectFirstByExample(ucenterDepartmentBusExample);

			count = ucenterFacultyService.deletePrimaryKeys(facultyId,departmentBus);
		}
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "打开修改院系页面")
	@RequiresPermissions("ucenter:faculty:update")
	@RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id,@PathVariable("userType") String userType, ModelMap modelMap) {
		UcenterFaculty ucenterFaculty=ucenterFacultyService.selectByPrimaryKey(id);
		setdata(modelMap);
		modelMap.put("userType", userType);
		modelMap.put("ucenterFaculty", ucenterFaculty);
		return "/manage/faculty/update.jsp";
	}

	@ApiOperation("修改院系")
	@RequiresPermissions("ucenter:faculty:update")
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("id") int id,
						 @RequestParam(required = false,defaultValue = "",value = "areaType")String areaType,
						 UcenterFaculty ucenterFaculty){
		ComplexResult result = FluentValidator.checkAll()
				.on(ucenterFaculty.getFacultyName(), new LengthValidator(1, 64, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}

		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		ucenterFacultyExample.createCriteria().andFacultyNameEqualTo(ucenterFaculty.getFacultyName()).andSchoolCodeEqualTo(ucenterFaculty.getSchoolCode());
		UcenterFaculty ucenterFaculty1= ucenterFacultyService.selectFirstByExample(ucenterFacultyExample);
		if(ucenterFaculty1!=null&&!ucenterFaculty1.getFacultyId().equals(id)){
			return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("院系名称已经存在!"));
		}
		UcenterDepartmentBusExample ucenterDepartmentBusExample = new UcenterDepartmentBusExample();
		ucenterDepartmentBusExample.createCriteria().andBusIdEqualTo(id);

		UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
		UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterFaculty.setCreator(username);
		long time = System.currentTimeMillis();
		ucenterFaculty.setCtime(time);
		ucenterFaculty.setFacultyId(id);

		UcenterDepartmentBus departmentBus=ucenterDepartmentBusService.selectFirstByExample(ucenterDepartmentBusExample);
		Integer departmentId=departmentBus.getDepartmentId();
		criteria.andDepartmentIdEqualTo(departmentId);
		UcenterDepartment ucenterDepartment=this.ucenterDepartmentService.selectFirstByExample(ucenterDepartmentExample);
		ucenterDepartment.setSchoolCode(ucenterFaculty.getSchoolCode());
		ucenterDepartment.setDepartmentName(ucenterFaculty.getFacultyName());
		ucenterDepartment.setDepartmentType(UcenterConstant.DeptType.YX);
		ucenterDepartment.setCreator(username);
		ucenterDepartment.setCtime(time);

		int count = ucenterFacultyService.updateByPrimaryKeySelective(ucenterFaculty,departmentBus,ucenterDepartment);
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}
	/**
	 * 获取学年列表
	 */
	public void setdata(ModelMap modelMap){
		Integer year=0;
		Integer byear=0;
		List list = new ArrayList();
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		try {
			year = Integer.valueOf(yeardate).intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for(int i=0;i<5;i++){
			byear=year-i;
			list.add(byear);
		}
		modelMap.put("list", list);
	}
}
