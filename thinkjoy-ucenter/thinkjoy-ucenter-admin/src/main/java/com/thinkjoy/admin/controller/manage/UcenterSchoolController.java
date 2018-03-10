package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/7/31.
 */
@Controller
@RequestMapping("/manage/school")
@Api(value ="学校管理",description = "学校管理")
public class UcenterSchoolController extends BaseController {
    private static Logger _log = (Logger) LoggerFactory.getLogger(UcenterSchoolController.class);

    @Autowired
    private UcenterSchoolService ucenterSchoolService;
	@Autowired
	private UcenterAgencyService uenterAgencyService;
	@Autowired
	private UpmsUserService upmsUserService;
	@Autowired
	private UcenterAreaService ucenterAreaService;


    @ApiOperation("学校管理首页")
    @RequiresPermissions("ucenter:school:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		modelMap.put("upmsUser",upmsUser);
		return "/manage/school/index.jsp";
    }

    @ApiOperation("学校管理列表")
    @RequiresPermissions("ucenter:school:read")
    @RequestMapping(value="/list",method =RequestMethod.GET )
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "search") String search,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
					   @RequestParam(required = false, defaultValue = "", value = "agencyCode") String agencyCode,
					   @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
					   @RequestParam(required = false, defaultValue = "", value = "areaLevel") String areaLevel,
					   @RequestParam(required = false, defaultValue = "", value = "userType") String userType,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_schoolCode") String serarch_schoolCode,
					   @RequestParam(required = false, defaultValue = "", value = "serarch_schoolName") String serarch_schoolName,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
        UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
        UcenterSchoolExample.Criteria criteria=ucenterSchoolExample.createCriteria();
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterSchoolExample.setOrderByClause(sort + " " + order);
        }
		if (StringUtils.isNotBlank(serarch_schoolCode)) {
			criteria.andSchoolCodeLike("%" + serarch_schoolCode + "%");
		}
		if (StringUtils.isNotBlank(serarch_schoolName)) {
			criteria.andSchoolNameLike("%" + serarch_schoolName + "%");
		}
		if (userType.equals(BaseConstants.ManagerType.SCHMANAGER)) {//学校管理员
			if (StringUtils.isNotBlank(schoolCode)) {
				criteria.andSchoolCodeEqualTo(schoolCode);
			}
		}else if(userType.equals(BaseConstants.ManagerType.AREAMANAGER)){//区域管理员
				if (StringUtils.isNotBlank(agencyCode)) {
					criteria.andAgencyCodeEqualTo(agencyCode);
				}
		}else{
			if (StringUtils.isNotBlank(agencyCode)) {//超级管理员
				criteria.andAgencyCodeEqualTo(agencyCode);
			}
		}

        List<UcenterSchool> rows=ucenterSchoolService.selectByExampleForOffsetPage(ucenterSchoolExample,offset,limit);
        long total = ucenterSchoolService.countByExample(ucenterSchoolExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);

        return result;
    }

    @ApiOperation(value = "打开新增学校页面")
    @RequiresPermissions("ucenter:school:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(required = false,defaultValue = "",value = "agencyCode")String agencyCode,
						 @RequestParam(required = false,defaultValue = "",value = "areaLevel")String areaLevel,
						 @RequestParam(required = false,defaultValue = "",value = "userType")String userType,
						 @RequestParam(required = false,defaultValue = "",value = "schoolCode")String schoolCode,
						 ModelMap modelMap) {
		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		ucenterAreaExample.createCriteria()
				.andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
		modelMap.put("ucenterAreas", ucenterAreas);
		UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
		ucenterAgencyExample.createCriteria().andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
		modelMap.put("ucenterAgencys", ucenterAgencys);
		modelMap.put("areaLevel", areaLevel);
        modelMap.put("agencyCode", agencyCode);
		modelMap.put("userType", userType);
		modelMap.put("schoolCode", schoolCode);
        return "/manage/school/create.jsp";
    }

    @ApiOperation(value = "新增学校")
    @RequiresPermissions("ucenter:school:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterSchool ucenterSchool,
						 @RequestParam(required = false,defaultValue = "",value = "ppagencyCode")String ppagencyCode,
						 HttpServletRequest request){
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterSchool.getSchoolName(), new LengthValidator(1, 128, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}

		String ppareaCode=request.getParameter("ppareaCode");
		String pareaCode=request.getParameter("pareaCode");
		String areaCode=request.getParameter("areaCode");

		ucenterSchool.setAreaType(UcenterConstant.AreaType.QU_XIAN);
		ucenterSchool.setPagencyCodes(ucenterSchool.getAgencyCode()+","+ppagencyCode);
		UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
		ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(ucenterSchool.getAgencyCode());
		List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
		if(ucenterAgencys.size()>0){
			ucenterSchool.setAreaCode(ucenterAgencys.get(0).getAreaCode());
			ucenterSchool.setAreaCodes(ppareaCode+","+pareaCode+","+areaCode);
			ucenterSchool.setPagencyCodes(ucenterSchool.getAgencyCode()+","+ucenterAgencys.get(0).getPagencyCodes());
		}

        //生成学校编码
        ucenterSchool.setSchoolCode(ucenterSchoolService.getNextCodeByAreaCode(ucenterSchool.getAgencyCode(),ucenterSchool.getAreaCode()));
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterSchool.setCreator(username);
		long time = System.currentTimeMillis();
        ucenterSchool.setCtime(time);
        ucenterSchool.setOrders(time);
        int count =ucenterSchoolService.insertSelective(ucenterSchool);
        return new UcenterResult(UcenterResultConstant.SUCCESS,count);
    }

	@ApiOperation(value = "验证学校是否存在")
	@RequestMapping(value = "/isExitSchoollNameInSchoolTable",method = RequestMethod.POST)
	@ResponseBody
	public boolean IsExitSchoollNameInSchoolTable(
			@RequestParam(required = true,defaultValue = "-1",value = "sid") String sid,
			@RequestParam(required = true,defaultValue = "",value = "schoolName") String schoolName){

		boolean isExit=false;
		UcenterSchoolExample ucenterSchoolExample=new UcenterSchoolExample();
		ucenterSchoolExample.createCriteria()
				.andSchoolNameEqualTo(schoolName)
				.andSchoolIdNotEqualTo(Integer.parseInt(sid));
		Integer info= ucenterSchoolService.countByExample(ucenterSchoolExample);
		if(info>0){
			isExit= true;
		}
		return  isExit;

	}

    @ApiOperation(value = "删除学校")
    @RequiresPermissions("ucenter:school:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = ucenterSchoolService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS,count);
    }

    @ApiOperation(value = "打开修改学校页面")
    @RequiresPermissions("ucenter:school:update")
    @RequestMapping(value = "/update/{id}/{userType}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id,@PathVariable("userType") String userType, ModelMap modelMap) {
		//省级行政区划列表
		UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
		ucenterAreaExample.createCriteria()
				.andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
		modelMap.put("ucenterAreas", ucenterAreas);

		UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
		ucenterAgencyExample.createCriteria().andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
		List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
		modelMap.put("ucenterAgencys",ucenterAgencys);
		modelMap.put("userType",userType);
        UcenterSchool ucenterSchool=ucenterSchoolService.selectByPrimaryKey(id);
        modelMap.put("ucenterSchool",ucenterSchool);

		//当前组织机构对应的上级的上级组织机构编码(省级)
		String pagencyCodes[]=ucenterSchool.getPagencyCodes().split(",");
		if(pagencyCodes.length==2){
			modelMap.put("ppagencyCode",pagencyCodes[1]);
		}else{
			modelMap.put("ppagencyCode",0);
		}
		//当前组织机构对应的上级行政区划码(市级)
		//当前组织机构对应的上级的上级行政区划码(省级)
		String areaCodes[]=ucenterSchool.getAreaCodes().split(",");

		if(areaCodes.length==3){
			modelMap.put("ppareaCode",areaCodes[0]);
			modelMap.put("pareaCode",areaCodes[1]);
			modelMap.put("areaCode",areaCodes[2]);
		}else{
			modelMap.put("pareaCode",0);
			modelMap.put("ppareaCode",0);
			modelMap.put("areaCode",0);
		}
        return "/manage/school/update.jsp";
    }

    @ApiOperation("修改学校")
    @RequiresPermissions("ucenter:school:update")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,
                         @RequestParam(required = false,defaultValue = "",value = "areaType")String areaType,
                         UcenterSchool ucenterSchool, HttpServletRequest request){
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterSchool.getSchoolName(), new LengthValidator(1, 128, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

		String ppareaCode=request.getParameter("ppareaCode");
		String pareaCode=request.getParameter("pareaCode");
		String areaCode=request.getParameter("areaCode");

		UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
		ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(ucenterSchool.getAgencyCode());
		List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
		ucenterSchool.setAreaCodes(ppareaCode+","+pareaCode+","+areaCode);
		ucenterSchool.setPagencyCodes(ucenterSchool.getAgencyCode()+","+ucenterAgencys.get(0).getPagencyCodes());

		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		ucenterSchool.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterSchool.setCtime(time);
        ucenterSchool.setOrders(time);
        ucenterSchool.setSchoolId(id);
        int count = ucenterSchoolService.updateByPrimaryKeySelective(ucenterSchool);
        return new UcenterResult(UcenterResultConstant.SUCCESS,count);
    }
}
