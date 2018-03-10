package com.thinkjoy.enrollment.admin.controller;

import com.thinkjoy.common.base.*;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import com.thinkjoy.enrollment.dao.model.EnrollTeacher;
import com.thinkjoy.enrollment.dao.model.EnrollTeacherExample;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.enrollment.rpc.api.EnrollTeacherService;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import com.thinkjoy.ucenter.dao.model.UcenterTeacher;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAreaService;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentService;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherService;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批次控制器
 * Created by wangcheng on 17/7/21.
 */
@Api(value = "招生老师管理", description = "招生老师管理")
@Controller
@RequestMapping("/manage/teacher")
public class EnrollTeacherController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(EnrollTeacherController.class);

    @Autowired
    private EnrollTeacherService enrollTeacherService;

    @Autowired
    private EnrollBatchService enrollBatchService;

    @Autowired
    private UcenterTeacherService ucenterTeacherService;

    @Autowired
    private UcenterDepartmentService ucenterDepartmentService;

    @Autowired
    private UcenterAreaService ucenterAreaService;

    @Override
    protected EnrollTeacherService getService() {
        return enrollTeacherService;
    }

    @ApiOperation(value = "招生老师首页")
    @RequiresPermissions("enroll:teacher:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            throw new BusindessException("学校编码为空!");
        }
        model.addAttribute("relationCode", relationCode);
        return "/manage/teacher/index.jsp";
    }

    @ApiOperation(value = "招生老师列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("enroll:teacher:read")
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "search_batchYear") String search_batchYear,
            @RequestParam(required = false, value = "search_teacherCode") String search_teacherCode,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order,
            HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }

        EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
        EnrollTeacherExample.Criteria criteria = enrollTeacherExample.createCriteria();

        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            enrollTeacherExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(relationCode)) {
            criteria.andSchoolCodeEqualTo(relationCode.trim());
        }
        if (StringUtils.isNotBlank(search_batchYear)) {
            criteria.andBatchYearEqualTo(Integer.valueOf(search_batchYear));
        }
        if (StringUtils.isNotBlank(search_teacherCode)) {
            criteria.andTeacherCodeEqualTo(search_teacherCode.trim());
        }


        criteria.andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<EnrollTeacher> enrollbatchs = enrollTeacherService.selectByExampleForOffsetPage(enrollTeacherExample, offset, limit);

        UcenterAreaExample ucenterAreaExample = new UcenterAreaExample();
        StringBuilder stringBuilder = new StringBuilder();

        for (EnrollTeacher et : enrollbatchs) {
            String area = et.getArea();

            if (StringUtils.isNotBlank(area)) {
                String[] split = area.split("-");
                if (split != null && split.length == 3) {
                    //省
                    ucenterAreaExample.clear();
                    ucenterAreaExample.createCriteria().andAreaCodeEqualTo(split[0]);
                    UcenterArea sheng = ucenterAreaService.selectFirstByExample(ucenterAreaExample);

                    //市
                    ucenterAreaExample.clear();
                    ucenterAreaExample.createCriteria().andAreaCodeEqualTo(split[1]);
                    UcenterArea shi = ucenterAreaService.selectFirstByExample(ucenterAreaExample);

                    stringBuilder
                            .append(sheng.getAreaName())
                            .append("-")
                            .append(shi.getAreaName())
                            .append("(");
                    if (StringUtils.isNotBlank(split[2])) {
                        String[] split1 = split[2].split(",");
                        if (split1 != null && split1.length > 0) {
                            for (int i = 0 ;i < split1.length; i++ ) {
                                //区/县
                                ucenterAreaExample.clear();
                                ucenterAreaExample.createCriteria().andAreaCodeEqualTo(split1[i]);
                                UcenterArea xian = ucenterAreaService.selectFirstByExample(ucenterAreaExample);
								if(xian!=null){
									stringBuilder.append(xian.getAreaName());
								}
                                if (i != split1.length-1){
                                    stringBuilder.append(",");
                                }else {
                                    stringBuilder.append(")");
                                }
                            }
                        }

                    }

                }
            }
            et.setArea(stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        long total = enrollTeacherService.countByExample(enrollTeacherExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", enrollbatchs);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增招生老师")
    @RequiresPermissions("enroll:teacher:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model, HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            throw new BusindessException("学校编码为空!");
        }

        if (StringUtils.isNotBlank(relationCode)) {
            //获取批次列表
            EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
            enrollBatchExample.createCriteria()
                    .andSchoolCodeEqualTo(relationCode)
                    .andYearEqualTo(Integer.parseInt(DateUtil.getYear()))
                    .andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<EnrollBatch> enrollBatches = enrollBatchService.selectByExample(enrollBatchExample);
            //获取用户中心老师列表
            UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
            ucenterTeacherExample.createCriteria()
                    .andSchoolCodeEqualTo(relationCode)
                    .andStatusEqualTo(BaseConstants.Status.NORMAL);
            List<UcenterTeacher> ucenterTeachers = ucenterTeacherService.selectByExample(ucenterTeacherExample);
            model.addAttribute("enrollBatches", enrollBatches);
            model.addAttribute("ucenterTeachers", ucenterTeachers);
        }
        return "/manage/teacher/create.jsp";
    }


    @ApiOperation(value = "新增招生老师")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("enroll:teacher:create")
    @ResponseBody
    public Object create(@Valid EnrollTeacher enrollTeacher,
                         BindingResult bindingResult,
                         HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }
        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }

        EnrollBatch enrollBatch = enrollBatchService.selectByPrimaryKey(enrollTeacher.getBatchId());
        UcenterTeacher ucenterTeacher = ucenterTeacherService.selectByPrimaryKey(enrollTeacher.getTeacherId());

        //字段重复校验
//        EnrollTeacherExample enrollTeacherExample = new EnrollTeacherExample();
//        int existCount = enrollTeacherService.countByExample(enrollTeacherExample);
//        if (existCount > 0) {
//            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("批次编码或批次名称重复!"));
//        }

        enrollTeacher.setBatchName(enrollBatch.getBatchName());
        enrollTeacher.setBatchYear(enrollBatch.getBatchYear());
        enrollTeacher.setTeacherCode(ucenterTeacher.getTeacherCode());
        enrollTeacher.setTeacherName(ucenterTeacher.getTeacherName());
        enrollTeacher.setPhone(ucenterTeacher.getPhone());
        enrollTeacher.setCreator(UserUtil.getCurrentUser());
        enrollTeacher.setCtime(System.currentTimeMillis());
        enrollTeacher.setYear(Integer.parseInt(DateUtil.getYear()));
        enrollTeacher.setSchoolCode(relationCode);
        int count = enrollTeacherService.insertSelective(enrollTeacher);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除招生老师")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:teacher:delete")
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = enrollTeacherService.deleteByPrimaryKeys(ids);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改招生老师")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:teacher:update")
    public Object update(@PathVariable("id") String id,
                         Model model,
                         @ModelAttribute("model") EnrollTeacher enrollTeacher,
                         HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            throw new BusindessException("学校编码为空!");
        }
        String area = enrollTeacher.getArea();

        if (StringUtils.isNotBlank(area)) {
            String[] split = area.split("-");
            if (split != null && split.length == 3) {
                model.addAttribute("ppareaCode", split[0]);
                model.addAttribute("pareaCode", split[1]);
                model.addAttribute("areaCode", split[2]);
            }
        }

        //获取批次列表
        EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
        enrollBatchExample.createCriteria()
                .andSchoolCodeEqualTo(relationCode)
                .andYearEqualTo(Integer.parseInt(DateUtil.getYear()))
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<EnrollBatch> enrollBatches = enrollBatchService.selectByExample(enrollBatchExample);
        //获取用户中心老师列表
        UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
        ucenterTeacherExample.createCriteria()
                .andSchoolCodeEqualTo(relationCode)
                .andStatusEqualTo(BaseConstants.Status.NORMAL);
        List<UcenterTeacher> ucenterTeachers = ucenterTeacherService.selectByExample(ucenterTeacherExample);


        model.addAttribute("enrollBatches", enrollBatches);
        model.addAttribute("ucenterTeachers", ucenterTeachers);
        model.addAttribute("enrollTeacher", enrollTeacher);

        return "/manage/teacher/update.jsp";
    }

    @ApiOperation(value = "修改招生老师")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("enroll:teacher:update")
    @ResponseBody
    public Object update(@PathVariable("id") int id,EnrollTeacher enrollTeacher,
                         BindingResult bindingResult) {

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
//        enrollbatchExample enrollbatchExample = new enrollbatchExample();
//        enrollbatchExample.createCriteria().andbatchCodeEqualTo(enrollbatch.getbatchCode()).andbatchIdNotEqualTo(Integer.parseInt(id));
//        enrollbatchExample.or().andbatchNameEqualTo(enrollbatch.getbatchName()).andbatchIdNotEqualTo(Integer.parseInt(id));
//        ;
//        int existCount = enrollbatchService.countByExample(enrollbatchExample);
//        if (existCount > 0) {
//            return new enrollResult(enrollResultConstant.FAILED, getErrorMsg("批次编码或批次名称重复!"));
//        }

        EnrollBatch enrollBatch = enrollBatchService.selectByPrimaryKey(enrollTeacher.getBatchId());
        UcenterTeacher ucenterTeacher = ucenterTeacherService.selectByPrimaryKey(enrollTeacher.getTeacherId());
		// 当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		long time = System.currentTimeMillis();
		enrollTeacher.setCtime(time);
		enrollTeacher.setCreator(username);
        enrollTeacher.setBatchName(enrollBatch.getBatchName());
        enrollTeacher.setBatchYear(enrollBatch.getBatchYear());
		enrollTeacher.setTeacherCode(ucenterTeacher.getTeacherCode());
		enrollTeacher.setTeacherName(ucenterTeacher.getTeacherName());
		enrollTeacher.setPhone(ucenterTeacher.getPhone());
		enrollTeacher.setEnrollteacherId(id);
        int count = enrollTeacherService.updateByPrimaryKeySelective(enrollTeacher);

        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "获取部门自关联树")
    @RequestMapping(value = "/getDepTree", method = RequestMethod.POST)
    @ResponseBody
    public Object getDepTree(@RequestParam(value = "schoolCode") String schoolCode) {
        return ucenterDepartmentService.getDepTree(schoolCode);
    }

}
