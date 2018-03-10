package com.thinkjoy.enrollment.admin.controller.manage;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.enrollment.rpc.api.EnrollSpecialtychangeLogService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gengsongbo on 2017/11/15.
 */
@Controller
@RequestMapping("/manage/spechangelog")
@Api(value ="专业变更日志",description = "专业变更日志")
class EnrollSpechangelogController extends BaseController<EnrollStudent, EnrollStudentService> {
	private static Logger _log = (Logger) LoggerFactory.getLogger(EnrollSpechangelogController.class);

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
	private EnrollSpecialtychangeLogService enrollSpecialtychangeLogService;

	@Override
	protected EnrollStudentService getService() {
		return enrollStudentService;
	}

	@ApiOperation("专业变更日志首页")
	@RequiresPermissions("enroll:spechangelog:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap,HttpServletRequest request){
		String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
		setdata(modelMap,schoolcode);

		//_log.info(">>>>> 学校code <<<<<"+schoolcode);
		modelMap.put("schoolcode", schoolcode);
		return "/manage/spechangelog/index.jsp";
	}

	@ApiOperation("专业变更日志列表")
	@RequiresPermissions("enroll:spechangelog:read")
	@RequestMapping(value="/list",method =RequestMethod.GET )
	@ResponseBody
	public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
					   @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
					   @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "schoolcode") String schoolcode,
					   @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_batchName") String serarch_batchName,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_studentName") String serarch_studentName,
					   @RequestParam(required = false, value = "sort") String sort,
					   @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
		EnrollSpecialtychangeLogExample enrollSpecialtychangeLogExample=new EnrollSpecialtychangeLogExample();
		EnrollSpecialtychangeLogExample.Criteria criteria=enrollSpecialtychangeLogExample.createCriteria();
		if(!"".equals(status)){
			criteria.andStatusEqualTo(status);
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			enrollSpecialtychangeLogExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(schoolcode)) {
			criteria.andSchoolCodeEqualTo(schoolcode);
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

		List<EnrollSpecialtychangeLog> rows=enrollSpecialtychangeLogService.selectByExampleForOffsetPage(enrollSpecialtychangeLogExample,offset,limit);
		long total = enrollSpecialtychangeLogService.countByExample(enrollSpecialtychangeLogExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
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

	@ApiOperation(value = "删除专业变更日志")
	@RequiresPermissions("enroll:spechangelog:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = enrollSpecialtychangeLogService.deleteByPrimaryKeys(ids);
		return new EnrollResult(EnrollResultConstant.SUCCESS,count);
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
