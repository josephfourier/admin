package com.thinkjoy.admin.controller.manage;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLog;
import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLogExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDatasyncLogService;
import com.thinkjoy.upms.common.constant.UpmsResult;
import com.thinkjoy.upms.common.constant.UpmsResultConstant;
import com.thinkjoy.upms.dao.model.UpmsUser;
import com.thinkjoy.upms.rpc.api.UpmsApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by xufei on 2017/10/19.
 */
@Controller
@RequestMapping("/manage/datasyncLog")
@Api(value = "数据同步日志管理", description = "数据同步日志管理")
public class UcenterDatasyncLogController  extends BaseController {
    private static Logger _log = LoggerFactory.getLogger(UcenterDatasyncLogController.class);
    @Autowired
    private UcenterDatasyncLogService ucenterDatasyncLogService;
    @Autowired
    private UpmsApiService upmsApiService;

    @ApiOperation(value = "数据同步日志首页")
    @RequiresPermissions("ucenter:datasyncLog:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("upmsUser", getCurrentUpmsUser());
        return "/manage/datasyncLog/index.jsp";
    }

    @ApiOperation(value = "数据同步日志列表")
    @RequiresPermissions("ucenter:datasyncLog:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "", value = "datasyncSystem") String datasyncSystem,
            @RequestParam(required = false, defaultValue = "", value = "schoolCode") String schoolCode,
            @RequestParam(required = false, defaultValue = "", value = "schoolName") String schoolName,
            @RequestParam(required = false, defaultValue = "", value = "datasyncTime") String datasyncTime,
            @RequestParam(required = false, defaultValue = "", value = "creator") String creator,
            @RequestParam(required = false, defaultValue = "", value = "status") String status,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {

        UcenterDatasyncLogExample ucenterDatasyncLogExample=new UcenterDatasyncLogExample();
        UcenterDatasyncLogExample.Criteria criteria= ucenterDatasyncLogExample.createCriteria();

        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            ucenterDatasyncLogExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(datasyncSystem)) {
            criteria.andDatasyncSystemLike("%" + datasyncSystem + "%");
        }
        if (StringUtils.isNotBlank(schoolName)) {
            criteria.andSchoolNameLike("%" + schoolName + "%");
        }
        if (StringUtils.isNotBlank(schoolCode)) {
            criteria.andSchoolCodeEqualTo(schoolCode);
        }
        if(!"".equals(datasyncTime)){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=null;
            try {
                date=simpleDateFormat.parse(datasyncTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            criteria.andDatasyncTimeEqualTo(date);
        }
        if (StringUtils.isNotBlank(creator)) {
            criteria.andCreatorLike("%" + creator + "%");
        }
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }

        List<UcenterDatasyncLog> rows=ucenterDatasyncLogService.selectByExampleWithBLOBsForOffsetPage(ucenterDatasyncLogExample, offset, limit);
        long total = ucenterDatasyncLogService.countByExample(ucenterDatasyncLogExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "数据同步日志删除")
    @RequiresPermissions("ucenter:datasyncLog:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) throws Exception {
        int count = ucenterDatasyncLogService.deleteByPrimaryKeys(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

    /**
     * 获取当前登录用户
     */
    private UpmsUser getCurrentUpmsUser() {
        //获取当前登录的管理员用户
        UpmsUser upmsUser = (UpmsUser) SecurityUtils.getSubject().getSession().getAttribute("upmsUser");
        if (upmsUser == null) {
            upmsUser = upmsApiService.selectUpmsUserByUsername(UserUtil.getCurrentUser());
            SecurityUtils.getSubject().getSession().setAttribute("upmsUser", upmsUser);
        }

        return upmsUser;
    }
}
