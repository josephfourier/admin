package com.thinkjoy.admin.controller.manage;

import com.thinkjoy.ucenter.dao.model.UcenterUsertype;
import com.thinkjoy.ucenter.dao.model.UcenterUsertypeExample;
import com.thinkjoy.ucenter.rpc.api.UcenterUsertypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * Created by xufei on 2017/7/25.
 */
@Controller
@RequestMapping("/manage/userType")
@Api(value ="用户类型管理",description = "用户类型管理")
public class UcenterUserTypeController {

    @Autowired
    private UcenterUsertypeService ucenterUsertypeService;

    @ApiOperation(value = "用户类型首页")
    @RequiresPermissions("ucenter:userType:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/userType/index.jsp";
    }

    @ApiOperation(value = "用户类型列表")
    @RequiresPermissions("ucenter:userType:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "0", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UcenterUsertypeExample ucenterUsertypeExample =new UcenterUsertypeExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterUsertypeExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            ucenterUsertypeExample.or().andUsertypeNameLike("%" + search + "%");
        }

        List<UcenterUsertype> rows=ucenterUsertypeService.selectByExampleForOffsetPage(ucenterUsertypeExample,offset,limit);
        long total = ucenterUsertypeService.countByExample(ucenterUsertypeExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }


}
