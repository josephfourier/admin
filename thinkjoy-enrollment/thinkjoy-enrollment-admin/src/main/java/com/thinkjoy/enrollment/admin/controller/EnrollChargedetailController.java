package com.thinkjoy.enrollment.admin.controller;

import com.thinkjoy.common.base.*;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.enrollment.dao.model.*;
import com.thinkjoy.enrollment.dao.model.enrollDto.ChargedetailSpeciaaltyDto;
import com.thinkjoy.enrollment.rpc.api.EnrollChargedetailService;
import com.thinkjoy.enrollment.rpc.api.EnrollChargedetailSpecialtyService;
import com.thinkjoy.enrollment.rpc.api.EnrollChargeitemService;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.model.UcenterFaculty;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialtyExample;
import com.thinkjoy.ucenter.rpc.api.UcenterFacultyService;
import com.thinkjoy.ucenter.rpc.api.UcenterSpecialtyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
 * 缴费项目控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "缴费项目管理", description = "缴费项目管理")
@Controller
@RequestMapping("/manage/chargedetail")
public class EnrollChargedetailController extends BaseController<EnrollChargedetail, EnrollChargedetailService> {

	private static Logger _log = LoggerFactory.getLogger(EnrollChargedetailController.class);

	@Autowired
	private EnrollChargedetailService enrollChargedetailService;

	@Autowired
	private EnrollChargeitemService chargeitemService;

	@Autowired
	private UcenterSpecialtyService ucenterSpecialtyService;

	@Autowired
	private UcenterFacultyService ucenterFacultyService;

	@Autowired
	private EnrollChargedetailSpecialtyService enrollChargedetailSpecialtyService;

	@Override
	protected EnrollChargedetailService getService() {
		return enrollChargedetailService;
	}

	@ApiOperation(value = "缴费项目首页")
	@RequiresPermissions("enroll:chargedetail:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/manage/chargedetail/index.jsp";
	}

	@ApiOperation(value = "缴费项目列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@RequiresPermissions("enroll:chargedetail:read")
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

		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			enrollChargedetailExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(relationCode)) {
			criteria.andSchoolCodeEqualTo(relationCode);
		}

		//criteria.andStatusEqualTo("1");
		List<EnrollChargedetail> enrollChargedetails = enrollChargedetailService.selectByExampleForOffsetPage(enrollChargedetailExample, offset, limit);
		long total = enrollChargedetailService.countByExample(enrollChargedetailExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", enrollChargedetails);
		result.put("total", total);
		return result;
	}

	@ApiOperation(value = "禁用")
	@RequestMapping(value = "/disabled", method = RequestMethod.POST)
	@ResponseBody
	public Object disabledCharge(@RequestParam("detailId") int detailId) {
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
		criteria.andDetailIdEqualTo(detailId);

		EnrollChargedetail enrollChargedetail = new EnrollChargedetail();
		enrollChargedetail.setStatus("0");

		int code = -1;
		String msg = "禁用失败";

		int count = enrollChargedetailService.updateByExampleSelective(enrollChargedetail, enrollChargedetailExample);

		if (count > 0) { code = 0; msg = "禁用成功"; }
		return new BaseResult(code, msg, null);
	}


	@ApiOperation(value = "启用")
	@RequestMapping(value = "/enabled", method = RequestMethod.POST)
	@ResponseBody
	public Object enabledCharge(@RequestParam("detailId") int detailId) {
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		EnrollChargedetailExample.Criteria criteria = enrollChargedetailExample.createCriteria();
		criteria.andDetailIdEqualTo(detailId);

		EnrollChargedetail enrollChargedetail = new EnrollChargedetail();
		enrollChargedetail.setStatus("1");

		int count = enrollChargedetailService.updateByExampleSelective(enrollChargedetail, enrollChargedetailExample);

		if (count > 0) return new BaseResult(0, "启用成功", null);

		return new BaseResult(-1, "启用成功", null);
	}

	@ApiOperation(value = "新增缴费项目")
	@RequiresPermissions("enroll:chargedetail:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model, HttpServletRequest request) {

		String relationCode = (String) request.getSession().getAttribute("relationCode");
		if (StringUtils.isBlank(relationCode)) {
			throw new BusindessException("学校编码为空");
		}
		//获取缴费类别
		EnrollChargeitemExample enrollChargeitemExample = new EnrollChargeitemExample();
		enrollChargeitemExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL);
		List<EnrollChargeitem> enrollChargeitems = chargeitemService.selectByExample(enrollChargeitemExample);

		//获取关联专业
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL)
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
		List<UcenterSpecialty> ucenterSpecialties = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);

		//获取院系
		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		ucenterFacultyExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL)
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
		List<UcenterFaculty> ucenterFaculties = ucenterFacultyService.selectByExample(ucenterFacultyExample);

		model.addAttribute("enrollChargeitems", enrollChargeitems);
		model.addAttribute("ucenterSpecialties", ucenterSpecialties);
		model.addAttribute("ucenterFaculties", ucenterFaculties);
		model.addAttribute("schoolCode", relationCode);
		return "/manage/chargedetail/create.jsp";
	}


	@ApiOperation(value = "新增缴费项目")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@RequiresPermissions("enroll:chargedetail:create")
	@ResponseBody
	public Object create(@Valid EnrollChargedetail enrollChargedetail,
						 BindingResult bindingResult,
						 HttpServletRequest request,
						 @RequestParam(value = "specialtyCode", required = false) List<String> specialtyCodes) {

		String relationCode = (String) request.getSession().getAttribute("relationCode");

		//数据校验
		if (bindingResult.hasErrors()) {
			return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
		}

		if (StringUtils.isBlank(relationCode)) {
			return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
		}

		//字段重复校验
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		enrollChargedetailExample.createCriteria()
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()))
				.andDetailNameEqualTo(enrollChargedetail.getDetailName())
				.andSchoolCodeEqualTo(relationCode);
		int existCount = enrollChargedetailService.countByExample(enrollChargedetailExample);
		if (existCount > 0) {
			return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("缴费项目名称重复!"));
		}

		enrollChargedetail.setCreator(UserUtil.getCurrentUser());
		enrollChargedetail.setCtime(System.currentTimeMillis());
		enrollChargedetail.setYear(Integer.parseInt(DateUtil.getYear()));
		enrollChargedetail.setSchoolCode(relationCode);
		int count = enrollChargedetailService.insertChargeDetail(enrollChargedetail, specialtyCodes);
		return new EnrollResult(EnrollResultConstant.SUCCESS, count);
	}

	/**
	 * 查询专业费用是否重复
	 * @return
	 */
	@ApiOperation("查询专业费用是否重复")
	@ResponseBody
	@RequestMapping(value="/checkdetail",method = RequestMethod.POST)
	public String checkdetail(@Valid EnrollChargedetail enrollChargedetail,
							  HttpServletRequest request,
							  @RequestParam(value = "specialtyCode", required = false) List<String> specialtyCode) {
		int year=Integer.parseInt(DateUtil.getYear());
		String schoolCode = (String) request.getSession().getAttribute("relationCode");
		String itemName=null;
		String specialtyName=null;
		int itemId=enrollChargedetail.getItemId();
		Integer detailId=enrollChargedetail.getDetailId();
		ChargedetailSpeciaaltyDto chargesBean=null;
		ChargedetailSpeciaaltyDto chargespecialtyBean=null;
		StringBuffer detailName=new StringBuffer();
		if (CollectionUtils.isNotEmpty(specialtyCode)) {
			for (String code : specialtyCode) {
				if(detailId!=null){
					 chargesBean= enrollChargedetailSpecialtyService.selectchargespecialtyBean(schoolCode,year,itemId,code,detailId);
					if(chargesBean==null){
						chargespecialtyBean= enrollChargedetailSpecialtyService.selectchargeBean(schoolCode,year,itemId,code);
						if(chargespecialtyBean!=null){
							itemName=chargespecialtyBean.getItemName();
							specialtyName=chargespecialtyBean.getSpecialtyName();
							detailName.append(specialtyName+":"+itemName+";");
						}
					}
				}else{
					chargespecialtyBean= enrollChargedetailSpecialtyService.selectchargeBean(schoolCode, year, itemId, code);
					if(chargespecialtyBean!=null){
						itemName=chargespecialtyBean.getItemName();
						specialtyName=chargespecialtyBean.getSpecialtyName();
						detailName.append(specialtyName+":"+itemName+";");
					}
				}
			}
		}
		if(detailName.length()>0){
			return "{\"msg\":\"1\",\"detailName\":\""+detailName+"\"}";
		}
		return "{\"msg\":\"2\",\"action\":\"OK\"}";
	}

	@ApiOperation(value = "删除缴费项目")
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
	@RequiresPermissions("enroll:chargedetail:delete")
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = enrollChargedetailService.deleteChargeDetail(ids);
		return new EnrollResult(EnrollResultConstant.SUCCESS, count);
	}

	@ApiOperation(value = "修改缴费项目")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	@RequiresPermissions("enroll:chargedetail:update")
	public Object update(@PathVariable("id") String id,
						 Model model,
						 @ModelAttribute("model") EnrollChargedetail enrollChargedetail,
						 HttpServletRequest request) {

		String relationCode = (String) request.getSession().getAttribute("relationCode");
		if (StringUtils.isBlank(relationCode)) {
			throw new BusindessException("学校编码为空");
		}
		//获取关联专业信息
		EnrollChargedetailSpecialtyExample enrollChargedetailSpecialtyExample = new EnrollChargedetailSpecialtyExample();
		enrollChargedetailSpecialtyExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andDetailIdEqualTo(Integer.valueOf(id));
		List<EnrollChargedetailSpecialty> enrollChargedetailSpecialties = enrollChargedetailSpecialtyService.selectByExample(enrollChargedetailSpecialtyExample);

		//获取缴费类别
		EnrollChargeitemExample enrollChargeitemExample = new EnrollChargeitemExample();
		enrollChargeitemExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL);
		List<EnrollChargeitem> enrollChargeitems = chargeitemService.selectByExample(enrollChargeitemExample);


		//获取关联专业
		UcenterSpecialtyExample ucenterSpecialtyExample = new UcenterSpecialtyExample();
		ucenterSpecialtyExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL)
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
		List<UcenterSpecialty> ucenterSpecialties = ucenterSpecialtyService.selectByExample(ucenterSpecialtyExample);

		//获取院系
		UcenterFacultyExample ucenterFacultyExample = new UcenterFacultyExample();
		ucenterFacultyExample.createCriteria()
				.andSchoolCodeEqualTo(relationCode)
				.andStatusEqualTo(BaseConstants.Status.NORMAL)
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
		List<UcenterFaculty> ucenterFaculties = ucenterFacultyService.selectByExample(ucenterFacultyExample);

		model.addAttribute("ucenterSpecialties", ucenterSpecialties);
		model.addAttribute("ucenterFaculties", ucenterFaculties);
		model.addAttribute("enrollChargeitems", enrollChargeitems);
		model.addAttribute("enrollChargedetail", enrollChargedetail);
		model.addAttribute("enrollChargedetailSpecialties", enrollChargedetailSpecialties);
		return "/manage/chargedetail/update.jsp";
	}

	@ApiOperation(value = "修改缴费项目")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@RequiresPermissions("enroll:chargedetail:update")
	@ResponseBody
	public Object update(@PathVariable("id") int id,
						 @Valid @ModelAttribute("model") EnrollChargedetail enrollChargedetail,
						 BindingResult bindingResult,
						 @RequestParam(value = "specialtyCode", required = false) List<String> specialtyCodes) {

		//数据校验
		if (bindingResult.hasErrors()) {
			return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
		}

		//字段重复校验
		EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
		enrollChargedetailExample.createCriteria()
				.andDetailIdNotEqualTo(enrollChargedetail.getDetailId())
				.andYearEqualTo(Integer.valueOf(DateUtil.getYear()))
				.andDetailNameEqualTo(enrollChargedetail.getDetailName())
				.andSchoolCodeEqualTo(enrollChargedetail.getSchoolCode());
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		long time = System.currentTimeMillis();
		enrollChargedetail.setDetailId(id);
		enrollChargedetail.setCreator(username);
		enrollChargedetail.setCtime(time);
		int existCount = enrollChargedetailService.countByExample(enrollChargedetailExample);
		if (existCount > 0) {
			return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("缴费项目名称重复!"));
		}

		int count = enrollChargedetailService.updateChargeDetail(enrollChargedetail, specialtyCodes);

		return new EnrollResult(EnrollResultConstant.SUCCESS, count);
	}

}
