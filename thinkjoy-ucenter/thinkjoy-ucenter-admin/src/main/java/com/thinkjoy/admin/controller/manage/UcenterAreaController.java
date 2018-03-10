package com.thinkjoy.admin.controller.manage;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.dao.model.UpmsUserExample;
import com.thinkjoy.upms.rpc.api.UpmsPermissionService;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xufei on 2017/7/25.
 */
@Controller
@RequestMapping("/manage/area")
@Api(value = "行政区划管理", description = "行政区划管理")
public class UcenterAreaController extends BaseController {
    private static Logger _log = LoggerFactory.getLogger(UcenterAreaController.class);
    @Autowired
    private UcenterAreaService ucenterAreaService;

    @Autowired
    private UpmsPermissionService upmsPermissionService;

	@Autowired
	private UpmsUserService upmsUserService;

	@Autowired
	private UcenterAgencyService uenterAgencyService;

    @ApiOperation(value = "行政区划首页")
    @RequiresPermissions("ucenter:area:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/area/index.jsp";
    }

    @ApiOperation(value = "行政区划列表")
    @RequiresPermissions("ucenter:area:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
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

        List<UcenterArea> rows=ucenterAreaService.selectByExampleForOffsetPage(ucenterAreaExample,offset,limit);
        long total = ucenterAreaService.countByExample(ucenterAreaExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }




    @ApiOperation(value = "获取行政区")
    @RequestMapping(value = "/getAreaTree", method = RequestMethod.POST)
    @ResponseBody
    public Object getAreaTree(){

        return ucenterAreaService.getAreaTree();
    }

	@ApiOperation(value = "获取行政区")
	@RequestMapping(value = "/getPowerTree", method = RequestMethod.POST)
	@ResponseBody
	public Object getPowerTree(){
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUserExample upmsUserExample = new UpmsUserExample();
		upmsUserExample.createCriteria().andUsernameEqualTo(username);
		UpmsUser upmsUser=upmsUserService.selectFirstByExample(upmsUserExample);
		if(upmsUser.getManagerType().equals(BaseConstants.ManagerType.AREAMANAGER)){
			UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
			ucenterAgencyExample.createCriteria().andStatusEqualTo(UcenterConstant.Status.NORMAL).andAreaTypeEqualTo(UcenterConstant.AreaType.SHENG);
			UcenterAgency agencybean=uenterAgencyService.selectFirstByExample(ucenterAgencyExample);
			return ucenterAreaService.getschoolTree(agencybean.getAreaCode());
		}
		return ucenterAreaService.getAreaTree();
	}

    @ApiOperation(value = "获取树")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id) {
        return upmsPermissionService.getTreeByRoleId(id);
    }
}