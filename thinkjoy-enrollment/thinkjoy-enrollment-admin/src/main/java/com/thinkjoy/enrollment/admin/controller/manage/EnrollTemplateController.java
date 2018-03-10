package com.thinkjoy.enrollment.admin.controller.manage;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.enrollment.dao.model.EnrollTemplate;
import com.thinkjoy.enrollment.dao.model.EnrollTemplateExample;
import com.thinkjoy.enrollment.rpc.api.EnrollTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gengsongbo on 2017/11/3.
 */
@Controller
@RequestMapping("/manage/template")
@Api(value ="招生管理",description = "招生管理")
class EnrollTemplateController extends BaseController<EnrollTemplate, EnrollTemplateService> {


    private static Logger _log = (Logger) LoggerFactory.getLogger(EnrollTemplateController.class);


    @Autowired
    private EnrollTemplateService enrollTemplateService;



    @ApiOperation("招生管理首页")
    @RequiresPermissions("enroll:template:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap,HttpServletRequest request){
        String schoolcode= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("relationCode"));
        modelMap.put("schoolcode", schoolcode);
        return "/manage/template/index.jsp";
    }

    @ApiOperation("招生管理列表")
    @RequiresPermissions("enroll:template:read")
    @RequestMapping(value="/list",method =RequestMethod.GET )
    @ResponseBody
    public Object list(@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                       @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
                       @RequestParam(required = false, defaultValue = "", value = "status") String status,
                       @RequestParam(required = false, defaultValue = "", value = "schoolcode") String schoolcode,
                       @RequestParam(required = false, defaultValue = "", value = "search_year") String search_year,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order,ModelMap modelMap){
        EnrollTemplateExample enrollTemplateExample=new EnrollTemplateExample();
        EnrollTemplateExample.Criteria criteria=enrollTemplateExample.createCriteria();
        if(!"".equals(status)){
            criteria.andStatusEqualTo(status);
        }

        criteria.andSchoolCodeEqualTo(schoolcode);


        List<EnrollTemplate> rows=enrollTemplateService.selectByExampleForOffsetPage(enrollTemplateExample,offset,limit);
        long total = enrollTemplateService.countByExample(enrollTemplateExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "模板设置")
    @RequestMapping(value = "/tempOPT",method = RequestMethod.POST)
    @ResponseBody
    public EnrollResult tempOPT(ModelMap modelMap, HttpServletRequest request) {
        String templateId=request.getParameter("templateId");
        String schoolcode=request.getParameter("schoolcode");
        enrollTemplateService.tempOPT(templateId,schoolcode);
        return new EnrollResult(EnrollResultConstant.SUCCESS,0);

    }

    @ApiOperation(value = "模板上传")
    @RequiresPermissions("enroll:template:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        return "/manage/template/create.jsp";
    }
}
