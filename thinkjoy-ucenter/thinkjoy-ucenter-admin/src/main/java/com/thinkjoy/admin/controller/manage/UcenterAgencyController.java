package com.thinkjoy.admin.controller.manage;

import com.baidu.unbiz.fluentvalidator.*;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.validator.LengthValidator;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
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

import java.util.*;

/**
 * Created by xufei on 2017/7/25.
 */
@Controller
@RequestMapping("/manage/agency")
@Api(value ="组织机构管理",description = "组织机构管理")
public class UcenterAgencyController extends BaseController {
    private static Logger _log= LoggerFactory.getLogger(UcenterAgencyController.class);

    @Autowired
    private UcenterAgencyService uenterAgencyService;

    @Autowired
    private UcenterAreaService ucenterAreaService;

	@Autowired
	private UpmsUserService upmsUserService;

    @ApiOperation(value = "组织机构首页")
    @RequiresPermissions("ucenter:agency:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/agency/index.jsp";
    }

    @ApiOperation(value = "组织机构列表")
    @RequiresPermissions("ucenter:agency:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "0", value = "pagencyCode") String pagencyCode,
            @RequestParam(required = false, defaultValue = "", value = "areaType") String areaType,
            @RequestParam(required = false, defaultValue = "", value = "status") String status,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
        UcenterAgencyExample.Criteria criteria=ucenterAgencyExample.createCriteria();
        if (!"0".equals(pagencyCode)) {
            criteria.andPagencyCodeEqualTo(pagencyCode);
        }
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterAgencyExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            ucenterAgencyExample.or().andAgencyNameLike("%" + search + "%");
        }

        List<UcenterAgency> rows=uenterAgencyService.selectByExampleForOffsetPage(ucenterAgencyExample, offset, limit);
        long total = uenterAgencyService.countByExample(ucenterAgencyExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

	@ApiOperation(value = "组织机构树")
	@RequestMapping(value = "/agencytree", method = RequestMethod.POST)
	@RequiresPermissions("ucenter:agency:read")
	@ResponseBody
	public Object agency(){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		String managerType=upmsUser.getManagerType();
		String relationCode=upmsUser.getRelationCode();

		if (StringUtils.isNotBlank(managerType) && managerType.equals(BaseConstants.ManagerType.AREAMANAGER)){
			UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
			ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(relationCode);
			List<UcenterAgency> ucenterAgencies = uenterAgencyService.selectByExample(ucenterAgencyExample);
			return uenterAgencyService.getAgencyAndChilds(ucenterAgencies);//区域管理员树

		}
		return uenterAgencyService.getAgencyByProjectId(Collections.EMPTY_LIST);//超级管理员树
	}

	@ApiOperation(value = "组织机构学校树")
	@RequestMapping(value = "/schooltree", method = RequestMethod.POST)
	@RequiresPermissions("ucenter:agency:read")
	@ResponseBody
	public Object school(){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		String managerType=upmsUser.getManagerType();
		String relationCode=upmsUser.getRelationCode();

		if (StringUtils.isNotBlank(managerType) && managerType.equals(BaseConstants.ManagerType.AREAMANAGER)){
			UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
			ucenterAgencyExample.createCriteria().andAgencyCodeEqualTo(relationCode);
			List<UcenterAgency> ucenterAgencies = uenterAgencyService.selectByExample(ucenterAgencyExample);
			return uenterAgencyService.getschoolAndChilds(ucenterAgencies);//区域管理员树
		}
		return uenterAgencyService.getSchoolAgencyByProjectId(new ArrayList());//超级管理员树
	}

    @ApiOperation(value = "新增机构")
    @RequiresPermissions("ucenter:agency:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
        ucenterAreaExample.createCriteria()
                .andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
        List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
        modelMap.put("ucenterAreas", ucenterAreas);

        UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
        ucenterAgencyExample.createCriteria().andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
        List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
        modelMap.put("ucenterAgencys", ucenterAgencys);
        return "/manage/agency/create.jsp";
    }

    @ApiOperation(value = "新增机构")
    @RequiresPermissions("ucenter:agency:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(UcenterAgency ucenterAgency,
                         @RequestParam(required = false, defaultValue = "", value = "ppagencyCode") String ppagencyCode,
                         @RequestParam(required = false, defaultValue = "", value = "ppareaCode") String ppareaCode,
                         @RequestParam(required = false, defaultValue = "", value = "pareaCode") String pareaCode) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterAgency.getAgencyName(), new LengthValidator(1, 64, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }
        if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
            ucenterAgency.setAreaCodes(pareaCode + "," + ppareaCode);
            ucenterAgency.setIsParent("false");
            ucenterAgency.setPagencyCodes(ucenterAgency.getPagencyCode() + "," + ppagencyCode);
            ucenterAgency.setAgencyCode(uenterAgencyService.getNextCodeByAgency(ucenterAgency.getAreaType(), ucenterAgency.getPagencyCode()));
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHI)) {
            ucenterAgency.setAreaCodes(ppareaCode);
            ucenterAgency.setAreaCode(pareaCode);
            ucenterAgency.setIsParent("true");
            ucenterAgency.setPagencyCodes(ppagencyCode);
            ucenterAgency.setPagencyCode(ppagencyCode);
            ucenterAgency.setAgencyCode(uenterAgencyService.getNextCodeByAgency(ucenterAgency.getAreaType(), ucenterAgency.getPagencyCode()));
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHENG)){
            ucenterAgency.setAreaCode(ppareaCode);
            ucenterAgency.setIsParent("true");
            ucenterAgency.setAgencyCode(uenterAgencyService.getNextCodeByAgency(ucenterAgency.getAreaType(), null));
        }

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterAgency.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterAgency.setCtime(time);
        ucenterAgency.setOrders(time);

        int count = uenterAgencyService.insertSelective(ucenterAgency);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除机构")
    @RequiresPermissions("ucenter:agency:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) throws Exception {
        String id[]=ids.split("-");
        boolean flag=false;
        String tempids="";
        for(int i=0;i<id.length;i++){
            UcenterAgency ucenterAgency = uenterAgencyService.selectByPrimaryKey(Integer.parseInt(id[i]));
            if(ucenterAgency.getIsParent().equals("true")){
                UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
                ucenterAgencyExample.createCriteria().andPagencyCodeEqualTo(ucenterAgency.getAgencyCode());
                List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
                if(ucenterAgencys.size()>0){
                    if(tempids.equals("")){
                        tempids=id[i];
                    }else{
                        tempids=tempids+","+id[i];
                    }
                    flag=true;
                }
            }
        }
        if(flag){
            ValidationResult result=new ValidationResult();
            ValidationError error=new ValidationError();
            error.setErrorMsg("有下级节点，不可删除编号:"+tempids);
            result.addError(error);
            return new UcenterResult(UcenterResultConstant.SUBORDINATE_NODE, result.getErrors());
        }

        int count = uenterAgencyService.deleteByPrimaryKeys(ids);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改机构")
    @RequiresPermissions("ucenter:agency:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        //省级行政区划列表
        UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
        ucenterAreaExample.createCriteria()
                .andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
        List<UcenterArea> ucenterAreas= ucenterAreaService.selectByExample(ucenterAreaExample);
        modelMap.put("ucenterAreas", ucenterAreas);
        //省级组织机构列表
        UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
        ucenterAgencyExample.createCriteria().andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
        List<UcenterAgency> ucenterAgencys=uenterAgencyService.selectByExample(ucenterAgencyExample);
        modelMap.put("ucenterAgencys", ucenterAgencys);
        //当前需要修改的组织机构信息
        UcenterAgency ucenterAgency = uenterAgencyService.selectByPrimaryKey(id);
        modelMap.put("ucenterAgency", ucenterAgency);
        if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHENG)){
            //当前组织机构对应的上级组织机构编码(省级)
            modelMap.put("ppagencyCode", ucenterAgency.getPagencyCode());
            //当前组织机构对应的行政区划码(省级)
            modelMap.put("ppareaCode",ucenterAgency.getAreaCode());
            modelMap.put("pareaCode", 0);
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHI)) {
            //当前组织机构对应的上级组织机构编码(省级)
            modelMap.put("ppagencyCode", ucenterAgency.getPagencyCode());
            //当前组织机构对应的行政区划码(市级)
            modelMap.put("pareaCode", ucenterAgency.getAreaCode());
            //当前组织机构的对应的上级行政区划码(省级)
            modelMap.put("ppareaCode",ucenterAgency.getAreaCodes());
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
            //当前组织机构对应的上级的上级组织机构编码(省级)
            String pagencyCodes[]=ucenterAgency.getPagencyCodes().split(",");
            if(pagencyCodes.length==2){
                modelMap.put("ppagencyCode",pagencyCodes[1]);
            }else{
                modelMap.put("ppagencyCode",0);
            }
            //当前组织机构对应的上级行政区划码(市级)
            //当前组织机构对应的上级的上级行政区划码(省级)
              String areaCodes[]=ucenterAgency.getAreaCodes().split(",");
            if(areaCodes.length==2){
                modelMap.put("pareaCode",areaCodes[0]);
                modelMap.put("ppareaCode",areaCodes[1]);
            }else{
                modelMap.put("pareaCode",0);
                modelMap.put("ppareaCode",0);
            }
        }

        return "/manage/agency/update.jsp";
    }

    @ApiOperation(value = "修改机构")
    @RequiresPermissions("ucenter:agency:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,
                         @RequestParam(required = false, defaultValue = "", value = "ppagencyCode") String ppagencyCode,
                         @RequestParam(required = false, defaultValue = "", value = "ppareaCode") String ppareaCode,
                         @RequestParam(required = false, defaultValue = "", value = "pareaCode") String pareaCode,
                         UcenterAgency ucenterAgency) {
        ComplexResult result = FluentValidator.checkAll()
                .on(ucenterAgency.getAgencyName(), new LengthValidator(1, 64, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
        }

        if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
            ucenterAgency.setAreaCodes(pareaCode + "," + ppareaCode);
            ucenterAgency.setIsParent("false");
            ucenterAgency.setPagencyCodes(ucenterAgency.getPagencyCode() + "," + ppagencyCode);
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHI)) {
            ucenterAgency.setAreaCodes(ppareaCode);
            ucenterAgency.setAreaCode(pareaCode);
            ucenterAgency.setIsParent("true");
            ucenterAgency.setPagencyCodes(ppagencyCode);
            ucenterAgency.setPagencyCode(ppagencyCode);
        }else if(ucenterAgency.getAreaType().equals(UcenterConstant.AreaType.SHENG)){
            ucenterAgency.setAreaCode(ppareaCode);
            ucenterAgency.setIsParent("true");
        }

        // 当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        ucenterAgency.setCreator(username);
        long time = System.currentTimeMillis();
        ucenterAgency.setCtime(time);
        ucenterAgency.setOrders(time);

        ucenterAgency.setAgencyId(id);
        int count = uenterAgencyService.updateByPrimaryKeySelective(ucenterAgency);
        return new UcenterResult(UcenterResultConstant.SUCCESS, count);
    }
}