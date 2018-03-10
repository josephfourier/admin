package com.thinkjoy.admin.controller.manage;

import com.thinkjoy.ucenter.dao.model.UcenterDict;
import com.thinkjoy.ucenter.dao.model.UcenterDictExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictService;
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
@RequestMapping("/manage/dict")
@Api(value ="字典管理",description = "字典管理")
public class UcenterDictController {

    @Autowired
    private UcenterDictService ucenterDictService;

    @ApiOperation(value = "字典首页")
    @RequiresPermissions("ucenter:dict:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/dict/index.jsp";
    }

    @ApiOperation(value = "字典列表")
    @RequiresPermissions("ucenter:dict:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UcenterDictExample ucenterDictExample= new UcenterDictExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterDictExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            ucenterDictExample.or().andDictNameLike("%" + search + "%");
        }

        List<UcenterDict> rows=ucenterDictService.selectByExampleForOffsetPage(ucenterDictExample,offset,limit);
        long total = ucenterDictService.countByExample(ucenterDictExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }


}
