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
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.dao.model.enrollDto.EnrollStudentDto;
import com.thinkjoy.enrollment.rpc.api.*;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;
import com.thinkjoy.ucenter.rpc.api.UcenterFacultyService;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by gengsongbo on 2017/11/3.
 */
@Controller
@RequestMapping("/manage/enroll")
@Api(value ="招生管理",description = "招生管理")
class EnrollStudentController extends BaseController<EnrollStudent, EnrollStudentService> {
	private static Logger _log = (Logger) LoggerFactory.getLogger(EnrollStudentController.class);

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
	@Autowired
	private EnrollPlanService enrollPlanService;
	@Autowired
	private UcenterFacultyService ucenterFacultyService;
	@Autowired
	private EnrollChargedetailService enrollChargedetailService;

	@Override
	protected EnrollStudentService getService() {
		return enrollStudentService;
	}

	@ApiOperation("招生管理首页")
	@RequiresPermissions("enroll:student:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap,HttpServletRequest request){
		String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
		criteria.andYearEqualTo(Integer.parseInt(DateUtil.getYear())).andSchoolCodeEqualTo(schoolcode).andStatusEqualTo(BaseConstants.Status.NORMAL);
		EnrollChargedetail enrollChargedetail=enrollChargedetailService.selectFirstByExample(enrollChargedetailExample);

		setdata(modelMap, schoolcode);
		_log.info(">>>>> 学校code <<<<<" + schoolcode);
		modelMap.put("schoolcode", schoolcode);
		modelMap.put("enrollChargedetail", enrollChargedetail);
		return "/manage/enroll/index.jsp";
	}

	@ApiOperation("招生管理列表")
	@RequiresPermissions("enroll:student:read")
	@RequestMapping(value="/list",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "schoolcode") String schoolcode,
					   @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_firstVolcode") String serarch_firstVolcode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_batchName") String serarch_batchName,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_enrollStatus") String serarch_enrollStatus,
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
		if (StringUtils.isNotBlank(serarch_enrollStatus)) {
			criteria.andEnrollStatusEqualTo(serarch_enrollStatus);
		}
		if (StringUtils.isNotBlank(serarch_studentName)) {
			criteria.andStudentNameLike("%" + serarch_studentName + "%");
		}

		List<EnrollStudent> rows=enrollStudentService.selectByExampleForOffsetPage(enrollStudentExample, offset, limit);
		long total = enrollStudentService.countByExample(enrollStudentExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	@ApiOperation(value = "打开招生表单页面")
	@RequiresPermissions("enroll:student:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ModelMap modelMap,
						 @RequestParam(required = false,defaultValue = "",value = "schoolcode")String schoolcode) {
		setdata(modelMap, schoolcode);
		Calendar date = Calendar.getInstance();
		String batchYear = String.valueOf(date.get(Calendar.YEAR));
		modelMap.put("batchYear", batchYear);
		modelMap.put("schoolcode", schoolcode);
		return "/manage/enroll/create.jsp";
	}

	@ApiOperation(value = "新增学生")
	@RequiresPermissions("enroll:student:create")
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
		enrollStudent.setYear(Integer.parseInt(DateUtil.getYear()));
		enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
		enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YXWSY);
		int count =enrollStudentService.insertSelective(enrollStudent);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
	}

	@ApiOperation(value = "删除学生")
	@RequiresPermissions("enroll:student:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = enrollStudentService.deleteStudent(ids);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
	}
	@ApiOperation(value = "未录取学生")
	@RequiresPermissions("enroll:student:wlqadmitted")
	@RequestMapping(value = "/wlqadmitted/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object wlqadmitted(@PathVariable("ids") String ids) {
		int count=0;
		int num=0;
		String id[]=ids.split("-");
		for(int i=0;i<id.length;i++){
			int id1=Integer.parseInt(id[i]);
			EnrollStudent enrollStudent1=enrollStudentService.selectByPrimaryKey(id1);
			if(BaseConstants.EnrollStatus.YXWSY.equals(enrollStudent1.getEnrollStatus())||BaseConstants.EnrollStatus.YLQ.equals(enrollStudent1.getEnrollStatus())
					&& BaseConstants.FeeStatus.WJF.equals(enrollStudent1.getFeeStatus()) || BaseConstants.FeeStatus.YJF.equals(enrollStudent1.getFeeStatus()) ){
				EnrollStudent enrollStudent=new EnrollStudent();
				enrollStudent.setStudentId(id1);
				enrollStudent.setSpecialtyName(enrollStudent1.getSpecialtyName());
				enrollStudent.setPhone(enrollStudent1.getPhone());
				enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.WLQ);
				enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
				count+=enrollStudentService.updateByPrimaryKeySelective(enrollStudent);
			}else{
				num++;
			}
		}
		if(num>0){
			count=0;
		}
		if(count>0){
			return new EnrollResult(EnrollResultConstant.SUCCESS,count);
		}else{
			return new EnrollResult(EnrollResultConstant.FAILED,count);
		}
	}
	@ApiOperation(value = "预录取学生")
	@RequiresPermissions("enroll:student:ylqadmitted")
	@RequestMapping(value = "/ylqadmitted/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object ylqadmitted(@PathVariable("ids") String ids) {
		int count=0;
		int num=0;
		String id[]=ids.split("-");
		for(int i=0;i<id.length;i++){
			int id1=Integer.parseInt(id[i]);
			EnrollStudent enrollStudent1=enrollStudentService.selectByPrimaryKey(id1);
			if(BaseConstants.EnrollStatus.YXWSY.equals(enrollStudent1.getEnrollStatus()) && BaseConstants.FeeStatus.WJF.equals(enrollStudent1.getFeeStatus())){
				EnrollStudent enrollStudent=new EnrollStudent();
				enrollStudent.setStudentId(id1);
				enrollStudent.setSpecialtyName(enrollStudent1.getSpecialtyName());
				enrollStudent.setPhone(enrollStudent1.getPhone());
				enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YLQ);
				enrollStudent.setFeeStatus(BaseConstants.FeeStatus.YJF);
				count+=enrollStudentService.yluquAndcreateNotice(enrollStudent);
			}else{
				num++;
			}
		}
		if(num>0){
			count=0;
		}
		if(count>0){
			return new EnrollResult(EnrollResultConstant.SUCCESS,count);
		}else{
			return new EnrollResult(EnrollResultConstant.FAILED,count);
		}
	}

	@ApiOperation(value = "录取学生")
	@RequiresPermissions("enroll:student:admitted")
	@RequestMapping(value = "/admitted/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object admitted(@PathVariable("ids") String ids) {
		int count = 0;
		int num=0;
		String id[]=ids.split("-");
		for(int i=0;i<id.length;i++){
			int id1=Integer.parseInt(id[i]);
			EnrollStudent enrollStudent=enrollStudentService.selectByPrimaryKey(id1);
			String schoolCode=enrollStudent.getSchoolCode();
			EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
			EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
			criteria.andYearEqualTo(Integer.parseInt(DateUtil.getYear())).andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			EnrollChargedetail enrollChargedetail=enrollChargedetailService.selectFirstByExample(enrollChargedetailExample);
			if(enrollChargedetail!=null){
				if(BaseConstants.EnrollStatus.YLQ.equals(enrollStudent.getEnrollStatus()) && BaseConstants.FeeStatus.YJJF.equals(enrollStudent.getFeeStatus())) {
					enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YJLQ);
					count += enrollStudentService.luquAndcreateNotice(enrollStudent);
				}else{
					num++;
				}
			}else{
				if(BaseConstants.EnrollStatus.YXWSY.equals(enrollStudent.getEnrollStatus()) && BaseConstants.FeeStatus.WJF.equals(enrollStudent.getFeeStatus())) {
					enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.YJLQ);
					count += enrollStudentService.luquAndcreateNotice(enrollStudent);
				}else{
					num++;
				}
			}
		}
		if(num>0){
			count=0;
		}
		if(count>0){
			return new EnrollResult(EnrollResultConstant.SUCCESS,count);
		}else{
			return new EnrollResult(EnrollResultConstant.FAILED,count);
		}
	}

	@ApiOperation(value = "打开修改招生页面")
	@RequiresPermissions("enroll:student:update")
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
		return "/manage/enroll/update.jsp";
	}

	@ApiOperation("修改招生")
	@RequiresPermissions("enroll:student:update")
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
		//enrollStudent.setFeeStatus(BaseConstants.FeeStatus.WJF);
		//enrollStudent.setEnrollStatus(BaseConstants.EnrollStatus.WLQ);
		int count = enrollStudentService.updateByPrimaryKeySelective(enrollStudent);
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
			@RequestParam(value = "areaType") String areaType,
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
		if (StringUtils.isNotBlank(areaType)) {
			criteria.andAreaTypeEqualTo(areaType);
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
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("查询校内专业编码查询专业信息")
	@ResponseBody
	@RequestMapping(value="/specialty",method = RequestMethod.POST)
	public Object ucenterSpecialty(String specialtyCode,String schoolcode) {
		JSONArray array = new JSONArray();
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria().andSpecialtyCodeEqualTo(specialtyCode).andSchoolCodeEqualTo(schoolcode).andYearEqualTo(Integer.parseInt(DateUtil.getYear()));
		UcenterSpecialty ucenterSpecialty = ucenterSpecialtyService.selectFirstByExample(ucenterSpecialtyExample);
		array.add(ucenterSpecialty);
		return  array;
	}

	@ApiOperation("统计分析首页")
	@RequiresPermissions("enroll:count:read")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public String count(ModelMap modelMap,HttpServletRequest request,
						@RequestParam(required = false, defaultValue = "", value = "batchYear") String batchYear){
		String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
		/*
		List<EnrollStudentDto> enrollStudentList =enrollStudentService.selectMbatchMap(schoolcode, batchYear, UcenterConstant.sexStatus.man);
		for(EnrollStudentDto studentbean:enrollStudentList){
			//统计报名女生人数
			EnrollStudentDto bstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.woman,null);
			//统计录取女生人数
			EnrollStudentDto lwstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.woman,UcenterConstant.EnrollStatus.YJLQ);
			//统计录取男生人数
			EnrollStudentDto lmstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.man,UcenterConstant.EnrollStatus.YJLQ);
			//统计批次招生人数
			Integer planNum=enrollPlanService.selectcountMap(studentbean.getBatchId(), studentbean.getBatchYear(), schoolcode);
			studentbean.setPlannum(planNum);
			studentbean.setBwnum(bstudbean.getRnum());
			Integer bzrnum=studentbean.getBmnum()+bstudbean.getRnum();
			studentbean.setBzrnum(bzrnum);
			Integer	lwnum=lwstudbean.getRnum();
			Integer lmnum=lmstudbean.getRnum();
			Integer lzrnum=lwnum+lmnum;
			studentbean.setLwnum(lwnum);
			studentbean.setLmnum(lmnum);
			studentbean.setLzrnum(lzrnum);
		}
		modelMap.put("enrollStudentList", enrollStudentList);*/
		setdata1(modelMap,schoolcode);
		_log.info(">>>>> 学校code <<<<<" + schoolcode);
		modelMap.put("schoolcode", schoolcode);
		return "/manage/count/index.jsp";
	}

	/**
	 * 根据年份和学校code查询院系学生信息（统计分析）
	 * @param batchYear
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("统计院系学生信息")
	@ResponseBody
	@RequestMapping(value="/selectfaculty",method = RequestMethod.POST)
	public Object selectfacultyMap(String batchYear,String schoolcode) {
		String returnJson = "";
		String enrollStatus=BaseConstants.EnrollStatus.YJLQ;
		List<EnrollStudentDto> enrollStudentList = enrollStudentService.selectfacultyMap(schoolcode, batchYear,enrollStatus);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(enrollStudentList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}
	/**
	 * 根据年份、专业院系code和学校code查询专业学生信息（统计分析）
	 * @param batchYear
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("统计专业学生信息")
	@ResponseBody
	@RequestMapping(value="/selectspecialty",method = RequestMethod.POST)
	public Object selectspecialtyMap(String batchYear,String schoolcode,String facultyCode) {
		String returnJson = "";
		String enrollStatus=BaseConstants.EnrollStatus.YJLQ;
		List<EnrollStudentDto> enrollStudentList =enrollStudentService.selectspecialtyMap(schoolcode, batchYear, facultyCode, enrollStatus);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(enrollStudentList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}
	/**
	 * 根据年份、招生老师ID和学校code查询招生老师学生信息（统计分析）
	 * @param batchYear
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("统计招生老师学生信息")
	@ResponseBody
	@RequestMapping(value="/selectteacher",method = RequestMethod.POST)
	public Object selectteacherMap(String batchYear,String schoolcode,String batchId) {
		String returnJson = "";
		String enrollStatus=BaseConstants.EnrollStatus.YJLQ;
		List<EnrollStudentDto> enrollStudentList = enrollStudentService.selectteacherMap(schoolcode, batchYear, batchId, enrollStatus);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(enrollStudentList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	/**
	 * 根据年份和学校code查询生源地学生信息（统计分析）
	 * @param batchYear
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("统计生源地学生信息")
	@ResponseBody
	@RequestMapping(value="/selectfromplace",method = RequestMethod.POST)
	public Object selectfromplaceMap(String batchYear,String schoolcode) {
		String returnJson = "";
		String enrollStatus=BaseConstants.EnrollStatus.YJLQ;
		List<EnrollStudentDto> enrollStudentList =enrollStudentService.selectfromplaceMap(schoolcode, batchYear, enrollStatus);
		for(EnrollStudentDto studentbean:enrollStudentList){
			String fromplace=studentbean.getFromplace();
			String cityCode1[]=fromplace.split(",");
			String cityCode=cityCode1[1];
			studentbean.setCityName(getAreaName(cityCode));
		}
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(enrollStudentList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	/**
	 * 根据年份和学校code查询批次招生学生信息（统计分析）
	 * @param batchYear
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("统计招生批次学生信息")
	@ResponseBody
	@RequestMapping(value="/selectbatch",method = RequestMethod.POST)
	public Object selectbatchMap(String batchYear,String schoolcode) {
		String returnJson = "";
		List<EnrollStudentDto> enrollStudentList =enrollStudentService.selectMbatchMap(schoolcode, batchYear, UcenterConstant.sexStatus.man);
		for(EnrollStudentDto studentbean:enrollStudentList){
			//统计报名女生人数
			EnrollStudentDto bstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.woman,null);
			//统计录取女生人数
			EnrollStudentDto lwstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.woman,UcenterConstant.EnrollStatus.YJLQ);
			//统计录取男生人数
			EnrollStudentDto lmstudbean=enrollStudentService.selectWbatchMap(schoolcode,batchYear,studentbean.getBatchId(),UcenterConstant.sexStatus.man,UcenterConstant.EnrollStatus.YJLQ);
			//统计批次招生人数
			Integer planNum=enrollPlanService.selectcountMap(studentbean.getBatchId(), studentbean.getBatchYear(), schoolcode);
			studentbean.setPlannum(planNum);
			studentbean.setBwnum(bstudbean.getRnum());
			Integer bzrnum=studentbean.getBmnum()+bstudbean.getRnum();
			studentbean.setBzrnum(bzrnum);
			Integer	lwnum=lwstudbean.getRnum();
			Integer lmnum=lmstudbean.getRnum();
			Integer lzrnum=lwnum+lmnum;
			studentbean.setLwnum(lwnum);
			studentbean.setLmnum(lmnum);
			studentbean.setLzrnum(lzrnum);
		}
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(enrollStudentList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	//统计百分比
	private String getPercent(Integer fnum, Integer znum) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		//可以设置精确几位小数
		df.setMaximumFractionDigits(2);
		//模式 例如四舍五入
		df.setRoundingMode(RoundingMode.HALF_UP);
		double accuracy_num = Double.valueOf(fnum) / Double.valueOf(znum)
				* 100;
		String percent = df.format(accuracy_num) + "%";
		if (znum == 0) {
			percent = "NaN";
		}
		return percent;
	}

	/**
	 * 根据招生批次ID查询招生老师
	 * @param batchId
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("根据招生批次ID查询招生老师")
	@ResponseBody
	@RequestMapping(value="/enrollteacher",method = RequestMethod.POST)
	public Object enrollteacher(int batchId,String schoolcode) {
		String returnJson = "";
		EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
		//EnrollTeacherExample.Criteria criteria = enrollTeacherExample.createCriteria();
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollTeacherExample.createCriteria().andBatchIdEqualTo(batchId).andSchoolCodeEqualTo(schoolcode);
		}
		List<EnrollTeacher> teacherList = enrollTeacherService.selectByExample(enrollTeacherExample);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(teacherList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	/**
	 * 根据年度查询招生批次
	 * @param year
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("根据年度查询招生批次")
	@ResponseBody
	@RequestMapping(value="/batchyear",method = RequestMethod.POST)
	public Object batchyear(int year,String schoolcode) {
		String returnJson = "";
		EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andBatchYearEqualTo(year);
		}
		List<EnrollBatch> batchList = enrollBatchService.selectByExample(enrollBatchExample);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(batchList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}
	/**
	 * 根据年度查询院系
	 * @param year
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("根据年度查询招院系")
	@ResponseBody
	@RequestMapping(value="/facultyear",method = RequestMethod.POST)
	public Object facultyear(int year,String schoolcode) {
		String returnJson = "";
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		UcenterFacultyExample.Criteria criteria=ucenterFacultyExample.createCriteria();
		if (StringUtils.isNotBlank(schoolcode)) {
//			criteria.andSchoolCodeEqualTo(schoolcode).andYearEqualTo(year);
			criteria.andSchoolCodeEqualTo(schoolcode);
		}
		//获取该学校院系列表
		List<UcenterFaculty> facultyList = ucenterFacultyService.selectByExample(ucenterFacultyExample);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(facultyList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	/**
	 * 根据年度查询专业信息
	 * @param year
	 * @param schoolcode
	 * @return
	 */
	@ApiOperation("根据年度查询专业信息")
	@ResponseBody
	@RequestMapping(value="/specialtyyear",method = RequestMethod.POST)
	public Object specialtyyear(int year,String schoolcode) {
		String returnJson = "";
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		if (StringUtils.isNotBlank(schoolcode)) {
//			ucenterSpecialtyExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andYearEqualTo(year);
			ucenterSpecialtyExample.createCriteria().andSchoolCodeEqualTo(schoolcode);
		}
		List<UcenterSpecialty> specialtyList = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(specialtyList);
		returnJson = jsonArray.toString();
		return  returnJson;
	}

	// 根据code编码查询行政区划表中的名称
	public String getAreaName(String dicCode)
	{
		String name = "";
		if (StringUtils.isNotBlank(dicCode))
		{
			UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
			ucenterAreaExample.createCriteria().andAreaCodeEqualTo(dicCode);
			UcenterArea dicBean = ucenterAreaService.selectFirstByExample(ucenterAreaExample);
			name = dicBean == null ? "" : dicBean.getAreaName();
		}
		return name;
	}
	/**
	 * 查询字典数据
	 */
	public void setdata(ModelMap modelMap,String schoolcode) {
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		UcenterSpecialtyExample.Criteria criteria = ucenterSpecialtyExample.createCriteria();
		if (StringUtils.isNotBlank(schoolcode)) {
			//criteria.andSchoolCodeEqualTo(schoolcode).andYearEqualTo(Integer.parseInt(DateUtil.getYear()));
			criteria.andSchoolCodeEqualTo(schoolcode);
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
		ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(BaseConstants.Dict.ENROLLSTATUS).andStatusEqualTo(BaseConstants.Status.NORMAL);
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
		int year=Integer.parseInt(DateUtil.getYear());
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andBatchYearEqualTo(year);
			//招生批次
			batchList = enrollBatchService.selectByExample(enrollBatchExample);
			enrollTeacherExample.createCriteria().andSchoolCodeEqualTo(schoolcode);
			//招生老师
			teacherList = enrollTeacherService.selectByExample(enrollTeacherExample);
		}
		modelMap.put("batchList",batchList);
		//modelMap.put("teacherList",teacherList);
	}
	/**
	 * 年度信息
	 */
	public void setdata1(ModelMap modelMap,String schoolcode) {
		EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
		UcenterFacultyExample ucenterFacultyExample=new UcenterFacultyExample();
		List<EnrollBatch> batchList=null;
		List<UcenterFaculty> facultylist=null;
		int year=Integer.parseInt(DateUtil.getYear());
		if (StringUtils.isNotBlank(schoolcode)) {
			enrollBatchExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andBatchYearEqualTo(year);
			ucenterFacultyExample.createCriteria().andSchoolCodeEqualTo(schoolcode).andYearEqualTo(year);
			//招生批次
			batchList = enrollBatchService.selectByExample(enrollBatchExample);
			facultylist=ucenterFacultyService.selectByExample(ucenterFacultyExample);
		}
		modelMap.put("batchList",batchList);
		modelMap.put("facultylist",facultylist);
		Integer year1=0;
		Integer byear=0;
		List list = new ArrayList();
		Calendar date = Calendar.getInstance();
		String yeardate = String.valueOf(date.get(Calendar.YEAR));
		try {
			year1 = Integer.valueOf(yeardate).intValue();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		for(int i=0;i<5;i++){
			byear=year1-i;
			list.add(byear);
		}
		modelMap.put("list", list);
	}

	@ApiOperation(value = "打印通知书验证")
	@RequestMapping(value = "/yzPrint/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object yzPrint(@PathVariable("ids") String ids) {
		int count = 0;
		int num=0;
		String id[]=ids.split("-");
		for(int i=0;i<id.length;i++){
			int id1=Integer.parseInt(id[i]);
			EnrollStudent enrollStudent=enrollStudentService.selectByPrimaryKey(id1);
			String schoolCode=enrollStudent.getSchoolCode();
			EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
			EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
			criteria.andYearEqualTo(Integer.parseInt(DateUtil.getYear())).andSchoolCodeEqualTo(schoolCode).andStatusEqualTo(BaseConstants.Status.NORMAL);
			EnrollChargedetail enrollChargedetail=enrollChargedetailService.selectFirstByExample(enrollChargedetailExample);
			if(enrollChargedetail!=null){
				if(BaseConstants.EnrollStatus.YJLQ.equals(enrollStudent.getEnrollStatus()) && BaseConstants.FeeStatus.YJJF.equals(enrollStudent.getFeeStatus())) {
					_log.info(">>>>> 启用缴费打印通知书 <<<<<" );
				}else{
					num++;
				}
			}else{
				if(BaseConstants.EnrollStatus.YJLQ.equals(enrollStudent.getEnrollStatus())) {
					_log.info(">>>>> 停用缴费打印通知书 <<<<<" );
				}else{
					num++;
				}
			}
		}
		if(num>0){
			count=0;
		}else{
			count=count+1;
		}
		if(count>0){
			return new EnrollResult(EnrollResultConstant.SUCCESS,count);
		}else{
			return new EnrollResult(EnrollResultConstant.FAILED,count);
		}
	}

	@ApiOperation(value = "批量打印")
	@RequestMapping(value = "/reportPrint/{ids}",method = RequestMethod.GET)
	public String reportPrint(@PathVariable("ids") String ids,Model model,HttpServletRequest request){

		List<String> objlist=new ArrayList();

		String id[]=ids.split("-");
		for(int i=0;i<id.length;i++){
			int id1=Integer.parseInt(id[i]);
			EnrollStudent enrollStudent=enrollStudentService.selectByPrimaryKey(id1);
			objlist.add(enrollStudent.getNoticeUrl());
		}
		model.addAttribute("objlist", objlist);
		return "/manage/enroll/print-reports.jsp";
	}


	@ApiOperation("招生管理首页")
	@RequiresPermissions("enroll:ylqstudent:read")
	@RequestMapping(value = "/pre_index", method = RequestMethod.GET)
	public String ylqadmitted(ModelMap modelMap,HttpServletRequest request){
		String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
		setdata(modelMap, schoolcode);

		_log.info(">>>>> 学校code <<<<<" + schoolcode);
		modelMap.put("schoolcode", schoolcode);
		return "/manage/ylqenroll/index.jsp";
	}

	@ApiOperation(value = "导入学生")
	@RequiresPermissions("enroll:student:create")
	@RequestMapping(value = "/upload" , method = RequestMethod.GET)
	public String upload(ModelMap modelMap, @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode) {
		modelMap.put("schoolCode", schoolCode);
		modelMap.put("baseModel", BaseConstants.ImportModel.BASEMODEL_ENROLL_STUDENT);//模板名称定义
		return "/manage/upload/upload.jsp";
	}
}
