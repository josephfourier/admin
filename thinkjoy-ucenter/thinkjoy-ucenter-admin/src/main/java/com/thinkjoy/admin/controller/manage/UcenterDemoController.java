package com.thinkjoy.admin.controller.manage;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
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
 * Created by jingqinghua on 17/9/19.
 */
@Controller
@RequestMapping("/manage/demo")
@Api(value = "行政区划管理", description = "行政区划管理")
public class UcenterDemoController extends BaseController {

    @Autowired
    private UcenterAreaService ucenterAreaService;

    @ApiOperation(value = "demo首页")
    @RequiresPermissions("ucenter:demo:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/demo/index.jsp";
    }

    @ApiOperation(value = "demo列表")
    @RequiresPermissions("ucenter:demo:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
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


}
