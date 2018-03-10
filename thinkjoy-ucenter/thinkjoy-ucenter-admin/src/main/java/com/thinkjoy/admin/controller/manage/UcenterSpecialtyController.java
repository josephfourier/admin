package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import com.thinkjoy.ucenter.rpc.api.UcenterFacultyService;
import com.thinkjoy.ucenter.rpc.api.UcenterSpecialtyService;
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
import java.util.*;

/**
 * Created by gengsongbo on 2017/10/18.
 */
@Controller
@RequestMapping("/manage/specialty")
@Api(value ="学校专业管理",description = "学校专业管理")
public class UcenterSpecialtyController extends BaseController {
	private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterSpecialtyController.class);

	@Autowired
	private UpmsUserService upmsUserService;

	@Autowired
	private UcenterFacultyService ucenterFacultyService;

	@Autowired
	private UcenterSpecialtyService ucenterSpecialtyService;

	@Autowired
	private UcenterDictValuesService ucenterDictValuesService;

	@Autowired
	private UcenterTrainingdirectionService TradService;

	@ApiOperation("专业管理首页")
	@RequiresPermissions("ucenter:specialty:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		modelMap.put("upmsUser",upmsUser);
		return "/manage/specialty/index.jsp";
	}

	@ApiOperation("专业管理列表")
	@RequiresPermissions("ucenter:specialty:read")
	@RequestMapping(value="/list",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
					   @RequestParam(required = false, defaultValue = "", value = "search_year") String year,
					   @RequestParam(required = false,defaultValue = "",value = "facultyCode")String facultyCode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_specialtyNo") String serarch_specialtyNo,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_specialtyCode") String serarch_specialtyCode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_specialtyName") String serarch_specialtyName,
					   @RequestParam(required = false, value = "sort") String sort,
					   @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
		UcenterSpecialtyExample ucenterSpecialtyExample=new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria=ucenterSpecialtyExample.createCriteria();
		//传入-1表示查询所有数据
		if (limit == -1) {
			limit = 0;
		}
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			ucenterSpecialtyExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(serarch_specialtyNo)) {
			criteria.andSpecialtyNoLike("%" + serarch_specialtyNo + "%");
		}
		if (StringUtils.isNotBlank(serarch_specialtyCode)) {
			criteria.andSpecialtyCodeLike("%" + serarch_specialtyCode + "%");
		}
		if (StringUtils.isNotBlank(serarch_specialtyName)) {
			criteria.andSpecialtyNameLike("%" + serarch_specialtyName + "%");
		}
		if (StringUtils.isNotBlank(schoolCode)) {
			if(StringUtils.isNotBlank(facultyCode)){
				criteria.andSchoolCodeEqualTo(schoolCode).andFacultyCodeEqualTo(facultyCode);
			}else{
				criteria.andSchoolCodeEqualTo(schoolCode);
			}
			criteria.andSchoolCodeEqualTo(schoolCode);
		}
		//新增三个查询
		if (StringUtils.isNotBlank(year)) {
			criteria.andYearEqualTo(Integer.parseInt(year));
		}
		List<UcenterSpecialty> rows=ucenterSpecialtyService.selectByExampleForOffsetPage(ucenterSpecialtyExample, offset, limit);
		long total = ucenterSpecialtyService.countByExample(ucenterSpecialtyExample);

		//名称装换
		if(rows!=null&&rows.size()>0) {
			//院系
			UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
			UcenterFacultyExample.Criteria criteria1 = ucenterFacultyExample.createCriteria();
			if (StringUtils.isNotBlank(schoolCode)) {
				criteria1.andSchoolCodeEqualTo(schoolCode);
			}
			criteria1.andStatusEqualTo(BaseConstants.Status.NORMAL);
			List<UcenterFaculty> ucenterFaculties = ucenterFacultyService.selectByExample(ucenterFacultyExample);
			//培养方向
			UcenterTrainingdirectionExample ucenterTrainingdirectionExample = new UcenterTrainingdirectionExample();
			UcenterTrainingdirectionExample.Criteria criteria2 = ucenterTrainingdirectionExample.createCriteria();
			if (StringUtils.isNotBlank(schoolCode)) {
				criteria2.andSchoolCodeEqualTo(schoolCode);
			}
			criteria2.andStatusEqualTo(BaseConstants.Status.NORMAL);
			List<UcenterTrainingdirection> ucenterTrainingdirections=TradService.selectByExample(ucenterTrainingdirectionExample);

			for(int i=0;i<rows.size();i++){
				if(ucenterFaculties!=null&&ucenterFaculties.size()>0){
					for(int j=0;j<ucenterFaculties.size();j++){
						if(rows.get(i).getFacultyCode()!=null&&rows.get(i).getFacultyCode().equals(ucenterFaculties.get(j).getFacultyCode())){
							rows.get(i).setFacultyName(ucenterFaculties.get(j).getFacultyName());
							continue;
						}
					}
				}
				if(ucenterTrainingdirections!=null&&ucenterTrainingdirections.size()>0){
					for(int j=0;j<ucenterTrainingdirections.size();j++){
						if(rows.get(i).getTrdrId()!=null&&rows.get(i).getTrdrId().equals(ucenterTrainingdirections.get(j).getTrdrId())){
							rows.get(i).setTrdrName(ucenterTrainingdirections.get(j).getTrdrName());
							continue;
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

	/**
	 * 查教育局专业编码否重复
	 * @param specialtyNo
	 * @param schoolCode
	 * @return
	 */
	@ApiOperation("查询教育局专业编码是否重复")
	@ResponseBody
	@RequestMapping(value="/checkcode",method = RequestMethod.POST)
	public boolean checkcode(String specialtyNo,String schoolCode) {
		boolean isExit = false;
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyNoEqualTo(specialtyNo).andSchoolCodeEqualTo(schoolCode);
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		if (ucenterSpecialty != null) {
			isExit = true;
		}
		return  isExit;
	}

	/**
	 * 查校内专业编码否重复
	 * @param specialtyCode
	 * @param schoolCode
	 * @return
	 */
	@ApiOperation("查询校内专业编码是否重复")
	@ResponseBody
	@RequestMapping(value="/checkxncode",method = RequestMethod.POST)
	public boolean checkxncode(String specialtyCode,String schoolCode) {
		boolean isExit = false;
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyCodeEqualTo(specialtyCode).andSchoolCodeEqualTo(schoolCode);
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		if (ucenterSpecialty != null) {
			isExit = true;
		}
		return  isExit;
	}

	/**
	 * 查询专业名称是否重复
	 * @param specialtyName
	 * @param schoolCode
	 * @return
	 */
	@ApiOperation("查询专业名称是否重复")
	@ResponseBody
	@RequestMapping(value="/checkname",method = RequestMethod.POST)
	public boolean checkname(String specialtyName,String schoolCode){
		boolean isExit = false;
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyNameEqualTo(specialtyName).andSchoolCodeEqualTo(schoolCode);
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		if (ucenterSpecialty != null) {
			isExit = true;
		}
		return  isExit;
	}

	@ApiOperation(value = "打开新增专业页面")
	@RequiresPermissions("ucenter:specialty:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(@RequestParam(required = false,defaultValue = "",value = "userType")String userType,
						 @RequestParam(required = false,defaultValue = "",value = "schoolCode")String schoolCode,
						 ModelMap modelMap) {
//		Calendar date = Calendar.getInstance();
//		String yeardate = String.valueOf(date.get(Calendar.YEAR));
//		Integer year=Integer.valueOf(yeardate).intValue();
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();

		UcenterTrainingdirectionExample ucenterTradExample=new UcenterTrainingdirectionExample();
		UcenterTrainingdirectionExample.Criteria criteria1=ucenterTradExample.createCriteria();
		setdata(modelMap);

		if (StringUtils.isNotBlank(schoolCode)) {
//			criteria.andSchoolCodeEqualTo(schoolCode).andYearEqualTo(year).andStatusEqualTo(BaseConstants.Status.NORMAL);
			criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			criteria1.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		}
		//获取该学校院系列表
		List<UcenterFaculty> facultyList = ucenterFacultyService.selectByExample(ucenterFacultyExample);
		//获取该学校专业方向列表
		List<UcenterTrainingdirection> tradList = TradService.selectByExample(ucenterTradExample);

		modelMap.put("tradList", tradList);
		modelMap.put("facultyList", facultyList);
		modelMap.put("userType", userType);
		modelMap.put("schoolCode", schoolCode);
		return "/manage/specialty/create.jsp";
	}

	@ApiOperation(value = "新增专业")
	@RequiresPermissions("ucenter:specialty:create")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object create(UcenterSpecialty ucenterSpecialty,
						 HttpServletRequest request){
		ComplexResult result = FluentValidator.checkAll()
				.on(ucenterSpecialty.getSpecialtyName(), new LengthValidator(1, 64, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterSpecialty.setCreator(username);
		long time = System.currentTimeMillis();
		ucenterSpecialty.setCtime(time);
		ucenterSpecialty.setYear(DateUtil.getCurrentYear());
		int count =ucenterSpecialtyService.insertSelective(ucenterSpecialty);
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "克隆专业")
	@RequiresPermissions("ucenter:specialty:clone")
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
		UcenterSpecialtyExample ucenterSpecialtyExample=new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andYearEqualTo(byear).andSchoolCodeEqualTo(schoolCode);
		List<UcenterSpecialty> specialtyList= ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
		for(UcenterSpecialty specialtybean:specialtyList){
			String specialtyCode=specialtybean.getSpecialtyCode();
			String specialtyNo=specialtybean.getSpecialtyNo();
			UcenterSpecialty tspecialtybean=ucenterSpecialtyService.getspecialtybeanExt(specialtyCode, schoolCode);
			UcenterSpecialty tspecialtyNobean=ucenterSpecialtyService.getspecialtyNobeanExt(specialtyNo, schoolCode);
			if(tspecialtybean==null && tspecialtyNobean==null){
				UcenterSpecialty 	ucenterSpecialty=new UcenterSpecialty();
				ucenterSpecialty.setYear(year);
				ucenterSpecialty.setCreator(username);
				ucenterSpecialty.setCtime(time);
				ucenterSpecialty.setSpecialtyCode(specialtyCode);
				ucenterSpecialty.setSpecialtyName(specialtybean.getSpecialtyName());
				ucenterSpecialty.setSpecialtyNo(specialtyNo);
				ucenterSpecialty.setTrdrId(specialtybean.getTrdrId());
				ucenterSpecialty.setTrdrName(specialtybean.getTrdrName());
				ucenterSpecialty.setSpecialtyType(specialtybean.getSpecialtyType());
				ucenterSpecialty.setFacultyCode(specialtybean.getFacultyCode());
				ucenterSpecialty.setFacultyName(specialtybean.getFacultyName());
				ucenterSpecialty.setSchoolSystem(specialtybean.getSchoolSystem());
				ucenterSpecialty.setSpecialtyNature(specialtybean.getSpecialtyNature());
				ucenterSpecialty.setEnrollmentTarget(specialtybean.getEnrollmentTarget());
				ucenterSpecialty.setDescription(specialtybean.getDescription());
				ucenterSpecialty.setStatus(specialtybean.getStatus());
				ucenterSpecialty.setSchoolCode(specialtybean.getSchoolCode());
				count +=ucenterSpecialtyService.insertSelective(ucenterSpecialty);
			}
		}
		if(count>0){
			return new UcenterResult(UcenterResultConstant.SUCCESS,count);
		}else{
			return new UcenterResult(UcenterResultConstant.FAILED,count);
		}
	}

	@ApiOperation(value = "删除专业")
	@RequiresPermissions("ucenter:specialty:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = ucenterSpecialtyService.deleteByPrimaryKeys(ids);
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "打开修改专业页面")
	@RequiresPermissions("ucenter:specialty:update")
	@RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id,@PathVariable("userType") String userType, ModelMap modelMap) {
		UcenterSpecialty ucenterSpecialty=ucenterSpecialtyService.selectByPrimaryKey(id);
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		Integer year=Integer.valueOf(yeardate).intValue();

		UcenterTrainingdirectionExample ucenterTradExample=new UcenterTrainingdirectionExample();
		UcenterTrainingdirectionExample.Criteria criteria1=ucenterTradExample.createCriteria();
		String schoolCode=ucenterSpecialty.getSchoolCode();
		if (StringUtils.isNotBlank(schoolCode)) {
//			criteria.andSchoolCodeEqualTo(schoolCode).andYearEqualTo(year).andStatusEqualTo(BaseConstants.Status.NORMAL);
			criteria.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			criteria1.andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		}
		List targetList = new ArrayList();
		String enrollmentTarget=ucenterSpecialty.getEnrollmentTarget();
		if (StringUtils.isNotBlank(enrollmentTarget)) {
			String target[] = enrollmentTarget.split(",");
			for (int i = 0; i < target.length; i++) {
				targetList.add(target[i]);
				;
			}
		}
		//获取该学校院系列表
		List<UcenterFaculty> facultyList = ucenterFacultyService.selectByExample(ucenterFacultyExample);
		//获取该学校专业方向列表
		List<UcenterTrainingdirection> tradList = TradService.selectByExample(ucenterTradExample);
		modelMap.put("tradList", tradList);
		modelMap.put("targetList", targetList);
		modelMap.put("facultyList", facultyList);
		setdata(modelMap);
		modelMap.put("userType", userType);
		modelMap.put("ucenterSpecialty", ucenterSpecialty);
		return "/manage/specialty/update.jsp";
	}

	@ApiOperation("修改专业")
	@RequiresPermissions("ucenter:specialty:update")
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("id") int id,
						 @RequestParam(required = false,defaultValue = "",value = "areaType")String areaType,
						 UcenterSpecialty ucenterSpecialty){
		ComplexResult result = FluentValidator.checkAll()
				.on(ucenterSpecialty.getSpecialtyName(), new LengthValidator(1, 64, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}

		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyNameEqualTo(ucenterSpecialty.getSpecialtyName()).andSchoolCodeEqualTo(ucenterSpecialty.getSchoolCode());
		UcenterSpecialty ucenterSpecialty1 = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		if(ucenterSpecialty1!=null&&!ucenterSpecialty1.getSpecialtyId().equals(id)){
			return new UcenterResult(UcenterResultConstant.INVALID_PARAMETER, getErrorMsg("专业名称已经存在!"));
		}

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterSpecialty.setCreator(username);
		long time = System.currentTimeMillis();
		ucenterSpecialty.setCtime(time);
		ucenterSpecialty.setSpecialtyId(id);
		int count = ucenterSpecialtyService.updateByPrimaryKeySelective(ucenterSpecialty);
		return new UcenterResult(UcenterResultConstant.SUCCESS,count);
	}

	/**
	 * 查询字典数据
	 */
	public void setdata(ModelMap modelMap){
		UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
		//学制
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.XZ);
		List<UcenterDictValues> xzDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("xzDicts", xzDicts);
		ucenterDictValuesExample.clear();
		//招生对象
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.TARGET);
		List<UcenterDictValues> targetDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("targetDicts", targetDicts);
		ucenterDictValuesExample.clear();
		//专业性质
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.FACUlTY_NATURE);
		List<UcenterDictValues> facuityDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("facuityDicts", facuityDicts);
		ucenterDictValuesExample.clear();
		//专业类别
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.FACUlTY_TYPE);
		List<UcenterDictValues> facTypeDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("facTypeDicts", facTypeDicts);
		ucenterDictValuesExample.clear();

		//获取学年列表
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
