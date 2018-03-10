package com.thinkjoy.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.ucenter.common.constant.UcenterResult;
import com.thinkjoy.ucenter.common.constant.UcenterResultConstant;
import com.thinkjoy.upms.common.constant.UpmsResult;
import com.thinkjoy.upms.common.constant.UpmsResultConstant;
import com.thinkjoy.upms.dao.model.UpmsCustomerinfo;
import com.thinkjoy.upms.dao.model.UpmsCustomerinfoExample;
import com.thinkjoy.upms.rpc.api.UpmsCustomerinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 客户信息controller
 * Created by  on 2017/10/31.
 */
@Controller
@Api(value = "客户信息管理", description = "客户信息管理")
@RequestMapping("/customer/customerinfo")
public class UpmsCustomerinfoController extends BaseController {

	private static Logger _log = LoggerFactory.getLogger(UpmsCustomerinfoController.class);

	@Autowired
	private UpmsCustomerinfoService upmsCustomerinfoService;

	@ApiOperation(value = "客户信息首页")
	@RequiresPermissions("upms:customerinfo:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/manage/customerinfo/index.jsp";
	}

	@ApiOperation(value = "客户信息列表")
	@RequiresPermissions("upms:customerinfo:read")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(
			@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
			@RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
			@RequestParam(required = false, defaultValue = "", value = "search") String search,
			@RequestParam(required = false, value = "sort") String sort,
			@RequestParam(required = false, value = "order") String order) {
		UpmsCustomerinfoExample upmsCustomerinfoExample = new UpmsCustomerinfoExample();
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			upmsCustomerinfoExample.setOrderByClause(sort + " " + order);
		}
		List<UpmsCustomerinfo> rows = upmsCustomerinfoService.selectByExampleForOffsetPage(upmsCustomerinfoExample, offset, limit);
		long total = upmsCustomerinfoService.countByExample(upmsCustomerinfoExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

//	@ApiOperation(value = "打开客户信息页面")
//	@RequiresPermissions("upms:customerinfo:create")
//	@RequestMapping(value = "custom/create", method = RequestMethod.GET)
//	public String create(ModelMap modelMap) {
//		return "/manage/customerinfo/create.jsp";
//	}

	@ApiOperation(value = "新增客户信息")
	@ResponseBody
	@RequestMapping(value = "/create",method = RequestMethod.GET)
	public Object create(UpmsCustomerinfo upmsCustomerinfo,
						 @RequestParam("callback") String callback,HttpServletRequest request) {
		ComplexResult result = FluentValidator.checkAll()
				.on(upmsCustomerinfo.getName())
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {

			return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
		}
		Date date = new Date();
		upmsCustomerinfo.setCtime(date);

		int count = upmsCustomerinfoService.insertSelective(upmsCustomerinfo);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(count);
		mappingJacksonValue.setJsonpFunction(callback);

		return mappingJacksonValue;
	}

	@ApiOperation(value = "删除客户信息")
	@RequiresPermissions("upms:customerinfo:delete")
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = upmsCustomerinfoService.deleteByPrimaryKeys(ids);
		return new UpmsResult(UpmsResultConstant.SUCCESS, count);
	}

	@ApiOperation(value = "打开修改客户信息页面")
	@RequiresPermissions("upms:customerinfo:update")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id, ModelMap modelMap) {
		UpmsCustomerinfo upmsCustomerinfo = upmsCustomerinfoService.selectByPrimaryKey(id);
		modelMap.put("upmsCustomerinfo", upmsCustomerinfo);
		return "/manage/customerinfo/update.jsp";
	}

	@ApiOperation("修改客户信息")
	@RequiresPermissions("upms:customerinfo:update")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("id") int id,
						 @RequestParam(required = false, defaultValue = "", value = "areaType") String areaType,
						 UpmsCustomerinfo upmsCustomerinfo) {
		ComplexResult result = FluentValidator.checkAll()
				.on(upmsCustomerinfo.getName())
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new UcenterResult(UcenterResultConstant.INVALID_LENGTH, result.getErrors());
		}
		Date date = new Date();
		upmsCustomerinfo.setCtime(date);
		int count = upmsCustomerinfoService.updateByPrimaryKeySelective(upmsCustomerinfo);
		return new UcenterResult(UcenterResultConstant.SUCCESS, count);
	}
}
