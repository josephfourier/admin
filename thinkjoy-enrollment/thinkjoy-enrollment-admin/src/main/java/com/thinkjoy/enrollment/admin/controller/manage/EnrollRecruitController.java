package com.thinkjoy.enrollment.admin.controller.manage;

import com.alibaba.fastjson.JSONArray;
import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.enrollment.rpc.api.EnrollStudentService;
import com.thinkjoy.enrollment.rpc.api.EnrollTeacherService;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import com.thinkjoy.ucenter.rpc.api.UcenterSpecialtyService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gengsongbo on 2017/11/15.
 */
@Controller
@RequestMapping("/manage/recruit")
@Api(value ="学生录取管理",description = "学生录取管理")
class EnrollRecruitController extends BaseController<EnrollStudent, EnrollStudentService> {
	private static Logger _log = (Logger) LoggerFactory.getLogger(EnrollRecruitController.class);

	@Autowired
	private EnrollStudentService enrollStudentService;
	@Autowired
	private EnrollBatchService enrollBatchService;
	@Autowired
	private EnrollTeacherService enrollTeacherService;
	@Autowired
	private UcenterDictValuesService ucenterDictValuesService;
	@Autowired
	private UcenterAreaService ucenterAreaService;
	@Autowired
	private UcenterSpecialtyService ucenterSpecialtyService;

	@Override
	protected EnrollStudentService getService() {
		return enrollStudentService;
	}

	@ApiOperation("学生录取管理首页")
	@RequiresPermissions("enroll:recruit:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap,HttpServletRequest request){
		String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
		setdata(modelMap,schoolcode);

		//_log.info(">>>>> 学校code <<<<<"+schoolcode);
		modelMap.put("schoolcode", schoolcode);
		return "/manage/recruit/index.jsp";
	}

	@ApiOperation("学生录取管理列表")
	@RequiresPermissions("enroll:recruit:read")
	@RequestMapping(value="/list",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "schoolcode") String schoolcode,
					   @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_firstVolcode") String serarch_firstVolcode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_batchName") String serarch_batchName,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_studentName") String serarch_studentName,
					   @RequestParam(required = false, value = "sort") String sort,
					   @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
		EnrollStudentExample enrollStudentExample=new EnrollStudentExample();
		EnrollStudentExample.Criteria criteria=enrollStudentExample.createCriteria();
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			enrollStudentExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(schoolcode)) {
			criteria.andSchoolCodeEqualTo(schoolcode);
		}
		if (StringUtils.isNotBlank(serarch_firstVolcode)) {
			criteria.andSpecialtyCodeEqualTo(serarch_firstVolcode);
		}
		if (StringUtils.isNotBlank(search_year)) {
			Integer date=Integer.parseInt(search_year);
			criteria.andBatchYearEqualTo(date);
		}
		if (StringUtils.isNotBlank(serarch_batchName)) {
			criteria.andBatchNameEqualTo(serarch_batchName);
		}
		if (StringUtils.isNotBlank(serarch_studentName)) {
			criteria.andStudentNameLike("%" + serarch_studentName + "%");
		}
		criteria.andEnrollStatusEqualTo(BaseConstants.EnrollStatus.YJLQ);
		enrollStudentExample.or().andEnrollStatusEqualTo(BaseConstants.EnrollStatus.SCLQ);
		List<EnrollStudent> rows=enrollStudentService.selectByExampleForOffsetPage(enrollStudentExample,offset,limit);
		long total = enrollStudentService.countByExample(enrollStudentExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	@ApiOperation(value = "打开学生录取表单页面")
	@RequiresPermissions("enroll:recruit:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ModelMap modelMap,
						 @RequestParam(required = false,defaultValue = "",value = "schoolcode")String schoolcode) {
		setdata(modelMap,schoolcode);
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		modelMap.put("year",year);
		modelMap.put("schoolcode",schoolcode);
		return "/manage/recruit/create.jsp";
	}

	@ApiOperation(value = "新增学生")
	@RequiresPermissions("enroll:recruit:create")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object create(EnrollStudent enrollStudent,
						 HttpServletRequest request){
		ComplexResult result = FluentValidator.checkAll()
				.on(enrollStudent.getStudentName(), new LengthValidator(1, 128, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new EnrollResult(EnrollResultConstant.INVALID_LENGTH, result.getErrors());
		}

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		enrollStudent.setCreator(username);
		long time = System.currentTimeMillis();
		enrollStudent.setCtime(time);
		enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
		enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.WLQ);
		int count =enrollStudentService.insertSelective(enrollStudent);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "删除学生")
	@RequiresPermissions("enroll:recruit:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = enrollStudentService.deleteByPrimaryKeys(ids);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
	}


	@ApiOperation(value = "打开修改学生页面")
	@RequiresPermissions("enroll:recruit:update")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id,@ModelAttribute("model") EnrollStudent enrollStudent,ModelMap modelMap) {
		String areaCodes2[]=enrollStudent.getOrigin().split(",");
		String areaCodes[]=enrollStudent.getDomicile().split(",");
		String areaCodes1[]=enrollStudent.getFromplace().split(",");
		if(areaCodes2.length==2){
			modelMap.put("ppareaCode2",areaCodes2[0]);
			modelMap.put("pareaCode2",areaCodes2[1]);
		}else{
			modelMap.put("ppareaCode2",0);
			modelMap.put("pareaCode2",0);
		}
		if(areaCodes.length==2){
			modelMap.put("ppareaCode",areaCodes[0]);
			modelMap.put("pareaCode",areaCodes[1]);
		}else{
			modelMap.put("ppareaCode",0);
			modelMap.put("pareaCode",0);
		}
		if(areaCodes1.length==2){
			modelMap.put("ppareaCode1",areaCodes1[0]);
			modelMap.put("pareaCode1",areaCodes1[1]);
		}else{
			modelMap.put("ppareaCode1",0);
			modelMap.put("pareaCode1",0);
		}
		setdata(modelMap, enrollStudent.getSchoolCode());
		modelMap.put("enrollStudent", enrollStudent);
		return "/manage/recruit/update.jsp";
	}

	@ApiOperation("修改招生")
	@RequiresPermissions("enroll:recruit:update")
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("id") int id,
						 EnrollStudent enrollStudent){
		ComplexResult result = FluentValidator.checkAll()
				.on(enrollStudent.getStudentName(), new LengthValidator(1, 128, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new EnrollResult(EnrollResultConstant.INVALID_LENGTH, result.getErrors());
		}

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		enrollStudent.setCreator(username);
		long time = System.currentTimeMillis();
		enrollStudent.setCtime(time);
		enrollStudent.setStudentId(id);
		int studentId=enrollStudent.getStudentId();
		EnrollStudent enrollStudent1=enrollStudentService.selectByPrimaryKey(studentId);
		enrollStudent.setBatchId(enrollStudent1.getBatchId());
		enrollStudent.setBatchYear(enrollStudent1.getBatchYear());
		enrollStudent.setEnrollteacherId(enrollStudent1.getEnrollteacherId());
		enrollStudent.setEnrollStatus(enrollStudent1.getEnrollStatus());
		enrollStudent.setNation(enrollStudent1.getNation());
		enrollStudent.setPolitics(enrollStudent1.getPolitics());
		enrollStudent.setDomicileType(enrollStudent1.getDomicileType());
		enrollStudent.setStudentType(enrollStudent1.getStudentType());
		EnrollSpecialtychangeLog spechangelog=new EnrollSpecialtychangeLog();
		if(!enrollStudent.getSpecialtyCode().equals(enrollStudent1.getSpecialtyCode())){
			SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			long time1=System.currentTimeMillis();
			Date dt = new Date(time1);
			spechangelog.setStudentId(enrollStudent.getStudentId());
			spechangelog.setYear(Integer.parseInt(DateUtil.getYear()));
			spechangelog.setCreator(UserUtil.getCurrentUser());
			spechangelog.setCtime(System.currentTimeMillis());
			spechangelog.setSchoolCode(enrollStudent.getSchoolCode());
			spechangelog.setBatchYear(enrollStudent.getYear());
			spechangelog.setBatchId(enrollStudent.getBatchId());
			spechangelog.setBatchName(enrollStudent.getBatchName());
			spechangelog.setStudentName(enrollStudent.getStudentName());
			spechangelog.setSex(enrollStudent.getSex());
			spechangelog.setIdcard(enrollStudent.getIdcard());
			spechangelog.setBeforeSpecialty(enrollStudent1.getSpecialtyName());
			spechangelog.setAfterSpecialty(enrollStudent.getSpecialtyName());
			spechangelog.setModifier(UserUtil.getCurrentUser());
			spechangelog.setMtime(dt);
		}
		int count = enrollStudentService.updateByPrimaryKeySelective(enrollStudent, spechangelog);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "行政区划列表")
	@RequestMapping(value = "/areaList", method = RequestMethod.GET)
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
		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		UcenterAreaExample.Criteria criteria=ucenterAreaExample.createCriteria();
		criteria.andStatusEqualTo(UcenterConstant.Status.NORMAL);
		if(!"".equals(areaCode)&&!"0".equals(areaCode)){
			criteria.andPcodeEqualTo(areaCode);
		}
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			ucenterAreaExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(search)) {
			ucenterAreaExample.or().andAreaNameLike("%" + search + "%");
		}

		List<UcenterArea> rows=ucenterAreaService.selectByExampleForOffsetPage(ucenterAreaExample, offset, limit);
		long total = ucenterAreaService.countByExample(ucenterAreaExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	/**
	 * 根据校内专业编码查询专业信息
	 * @param specialtyCode
	 * @return
	 */
	@ApiOperation("查询校内专业编码查询专业信息")
	@ResponseBody
	@RequestMapping(value="/specialty",method = RequestMethod.POST)
	public Object ucenterSpecialty(String specialtyCode,String schoolcode) {
		JSONArray array = new JSONArray();
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyCodeEqualTo(specialtyCode).andSchoolCodeEqualTo(schoolcode);
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		array.add(ucenterSpecialty);
		return  array;
	}

	/**
	 * 查询字典数据
	 */
	public void setdata(ModelMap modelMap,String schoolcode) {
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria = ucenterSpecialtyExample.createCriteria();
		if (StringUtils.isNotBlank(schoolcode)) {
			criteria.andSchoolCodeEqualTo(schoolcode).andYearEqualTo(Integer.parseInt(DateUtil.getYear()));
		}
		//获取该学校专业列表
		List<UcenterSpecialty> specialtyList = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
		modelMap.put("specialtyList", specialtyList);

		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		ucenterAreaExample.createCriteria()
				.andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		//行政区划选择
		List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
		modelMap.put("ucenterAreas", ucenterAreas);

		UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
		//民族
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.NATION);
		List<UcenterDictValues> nationDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("nationDicts", nationDicts);
		ucenterDictValuesExample.clear();
		//毕业类别
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.TARGET);
		List<UcenterDictValues> targetDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("targetDicts", targetDicts);
		ucenterDictValuesExample.clear();
		//户籍性质
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.DOMICILETYPE);
		List<UcenterDictValues> domicileDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("domicileDicts", domicileDicts);
		ucenterDictValuesExample.clear();
		//政治面貌
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.POLITICS);
		List<UcenterDictValues> politicsDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("politicsDicts", politicsDicts);
		ucenterDictValuesExample.clear();
		//是否是贫困生
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ISPOOR);
		List<UcenterDictValues> ispoorDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("ispoorDicts", ispoorDicts);
		ucenterDictValuesExample.clear();
		//是否住校
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ISLIVESCHOOL);
		List<UcenterDictValues> isliveschoolDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("isliveschoolDicts", isliveschoolDicts);
		ucenterDictValuesExample.clear();
		//性别
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.SEX);
		List<UcenterDictValues> sexDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("sexDicts", sexDicts);
		ucenterDictValuesExample.clear();
		//缴费状态
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.FEESTATUS);
		List<UcenterDictValues> feestatusDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("feestatusDicts", feestatusDicts);
		ucenterDictValuesExample.clear();
		//录取状态
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ENROLLSTATUS);
		List<UcenterDictValues> enrollstatusDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("enrollstatusDicts", enrollstatusDicts);
		ucenterDictValuesExample.clear();

		//录取志愿
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ENROLLWILL);
		List<UcenterDictValues> lqzyDicts = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);
		modelMap.put("lqzyDicts", lqzyDicts);
		ucenterDictValuesExample.clear();

		EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
		EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
		List<EnrollBatch> batchList=null;
		List<EnrollTeacher> teacherList=null;
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode);
			//招生批次
			batchList = enrollBatchService.selectByExample(enrollBatchExample);
			enrollTeacherExample.createCriteria().andSchoolCodeEqualTo(schoolcode);
			//招生老师
			teacherList = enrollTeacherService.selectByExample(enrollTeacherExample);
		}
		modelMap.put("batchList",batchList);
		modelMap.put("teacherList",teacherList);
	}
}
