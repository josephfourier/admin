package com.thinkjoy.upms.server.controller.manage;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.upms.dao.model.UpmsUserSchool;
import com.thinkjoy.upms.dao.model.UpmsUserSchoolExample;
import com.thinkjoy.upms.rpc.api.UpmsUserSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学校controller
 * Created by  on 2017/09/22.
 */
@Controller
@Api(value = "用户学校管理", description = "用户学校管理")
@RequestMapping("/manage/school")
public class UpmsSchoolController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsSchoolController.class);

    @Autowired
    private UpmsUserSchoolService upmsUserSchoolService;

    @ApiOperation(value = "用户学校首页")
    @RequiresPermissions("upms:school:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/school/index.jsp";
    }

    @ApiOperation(value = "用户学校列表")
    @RequiresPermissions("upms:school:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
		UpmsUserSchoolExample upmsUserSchoolExample = new UpmsUserSchoolExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			upmsUserSchoolExample.setOrderByClause(sort + " " + order);
        }
		List<UpmsUserSchool> rows = upmsUserSchoolService.selectByExampleForOffsetPage(upmsUserSchoolExample, offset, limit);
		long total = upmsUserSchoolService.countByExample(upmsUserSchoolExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }
}
