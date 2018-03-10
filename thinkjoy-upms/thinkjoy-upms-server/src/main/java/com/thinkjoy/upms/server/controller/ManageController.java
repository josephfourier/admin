package com.thinkjoy.upms.server.controller;

import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.upms.dao.model.*;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import com.thinkjoy.upms.rpc.api.UpmsSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台controller
 * Created by  on 2017/01/19.
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController extends BaseController {

	private static Logger _log = LoggerFactory.getLogger(ManageController.class);

	@Autowired
	private UpmsSystemService upmsSystemService;

	@Autowired
	private UpmsApiService upmsApiService;

	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request,ModelMap modelMap) {
		// 已注册系统
		UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
		upmsSystemExample.createCriteria()
				.andStatusEqualTo((byte) 1);
		List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(upmsSystemExample);

		// 当前登录用户权限
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
		List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());

		//如果是学校管理员，去掉统一权限管理系统
		if(upmsUser!=null&&upmsUser.getManagerType().equals(BaseConstants.ManagerType.SCHMANAGER)){
			UpmsSystem upmsSystemtemp=null;
			for(UpmsSystem upmsSystem:upmsSystems){
				if(upmsSystem.getSystemId()==1){//统一权限管理管理系统的systemid是1
					upmsSystemtemp=upmsSystem;
					break;
				}
			}
			upmsSystems.remove(upmsSystemtemp);
			modelMap.put("managerType",upmsUser.getManagerType());
		}
		modelMap.put("upmsSystems", upmsSystems);

		//如果是单点认证返回请求，查询对应功能名称
		Object backUrl=request.getSession().getAttribute("backurl");
		if(backUrl!=null){
			request.getSession().removeAttribute("backurl");
			modelMap.put("backUrl", backUrl.toString());
			String tempbackUrl=backUrl.toString().substring(backUrl.toString().indexOf("//")+2);
			tempbackUrl=tempbackUrl.substring(tempbackUrl.indexOf("/"));
			for(UpmsPermission upmsPermission:upmsPermissions){
				if(tempbackUrl.equals(upmsPermission.getUri())){
					modelMap.put("backUrl_title", upmsPermission.getName());
				}
			}
			request.removeAttribute("backUrl");
		}

		//过滤出有权限的系统列表
//		//1.临时
		if(upmsPermissions!=null&&upmsPermissions.size()>0){
			List<UpmsPermission> upmsSystemsPermission=new ArrayList<UpmsPermission>();

			for(UpmsPermission upmsPermission:upmsPermissions){
				if(upmsPermission.getType()==0){
					upmsSystemsPermission.add(upmsPermission);
				}
			}
			//过滤掉系统级权限
			upmsPermissions.removeAll(upmsSystemsPermission);
			modelMap.put("upmsSystemsPermission", upmsSystemsPermission);
		}

		modelMap.put("upmsPermissions", upmsPermissions);


		return "/manage/index.jsp";
	}

}