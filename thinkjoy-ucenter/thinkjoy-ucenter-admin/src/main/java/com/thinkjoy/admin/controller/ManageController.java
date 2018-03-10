package com.thinkjoy.admin.controller;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.PropertiesFileUtil;
import com.thinkjoy.common.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 后台controller
 * Created by xufei
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台控制器", description = "后台管理")
public class ManageController extends BaseController {

	private static Logger _log = LoggerFactory.getLogger(ManageController.class);

	/**
	 * 后台首页
	 * @return
	 */
	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		String sso_serverUrl = PropertiesUtil.getInstance().getProperties().getProperty("thinkjoy.upms.sso.server.url");
		modelMap.put("sso_serverUrl",sso_serverUrl);
		return "/manage/index.jsp";
	}

}