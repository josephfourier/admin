package com.thinkjoy.enrollment.admin.controller;

import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.common.base.BaseResult;
import com.thinkjoy.common.base.EnrollResult;
import com.thinkjoy.common.base.EnrollResultConstant;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.UserUtil;
import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.enrollment.rpc.api.EnrollChargedetailService;
import com.thinkjoy.enrollment.rpc.api.EnrollPlanService;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.rpc.api.UcenterSpecialtyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@Api(value = "批次管理", description = "批次管理")
@Controller
@RequestMapping("/manage/batch")
public class EnrollBatchController extends BaseController<EnrollBatch, EnrollBatchService> {

    private static Logger _log = LoggerFactory.getLogger(EnrollBatchController.class);

    @Autowired
    private EnrollBatchService enrollbatchService;

    @Override
    protected EnrollBatchService getService() {
        return enrollbatchService;
    }

    @Autowired
    private UcenterSpecialtyService ucenterSpecialtyService;

    @Autowired
    private EnrollChargedetailService enrollChargedetailService;

    @Autowired
    private EnrollPlanService enrollPlanService;

    @ApiOperation(value = "批次首页")
    @RequiresPermissions("enroll:batch:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/batch/index.jsp";
    }

    @ApiOperation(value = "批次列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:read")
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "search_batchYear") String search_batchYear,
            @RequestParam(required = false, value = "search_batchName") String search_batchName,
            @RequestParam(required = false, value = "order") String order,
            HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }

        EnrollBatchExample enrollbatchExample = new EnrollBatchExample();
        EnrollBatchExample.Criteria criteria = enrollbatchExample.createCriteria();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            enrollbatchExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(relationCode)) {
            criteria.andSchoolCodeEqualTo(relationCode);

        }
        if (StringUtils.isNotBlank(search_batchYear)) {
            criteria.andBatchYearEqualTo(Integer.valueOf(search_batchYear));
        }
        if (StringUtils.isNotBlank(search_batchName)) {
            criteria.andBatchNameEqualTo(search_batchName.trim());
        }
        //因原型存在禁用及启用操作，这里不过滤掉未启用的批次
        //criteria.andStatusEqualTo("1");
        List<EnrollBatch> enrollbatchs = enrollbatchService.selectByExampleForOffsetPage(enrollbatchExample, offset, limit);
        long total = enrollbatchService.countByExample(enrollbatchExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", enrollbatchs);
        result.put("total", total);
        return result;
    }


    @ApiOperation(value = "禁用批次")
    @RequiresPermissions("enroll:batch:update")
    @RequestMapping(value = "/disabled", method = RequestMethod.POST)
    @ResponseBody
    public Object disabledBatch(@RequestParam("batchId") int batchId) {
        EnrollBatchExample batchExample = new EnrollBatchExample();
        EnrollBatchExample.Criteria criteria = batchExample.createCriteria();
        criteria.andBatchIdEqualTo(batchId);

        EnrollBatch enrollBatch = new EnrollBatch();
        enrollBatch.setStatus("0");

        int code = -1;
        String msg = "禁用失败";

        int count = enrollbatchService.updateByExampleSelective(enrollBatch, batchExample);

        if (count > 0) { code = 0; msg = "禁用成功"; }
        return new BaseResult(code, msg, null);
    }


    @ApiOperation(value = "启用批次")
    @RequiresPermissions("enroll:batch:update")
    @RequestMapping(value = "/enabled", method = RequestMethod.POST)
    @ResponseBody
    public Object enabledBatch(@RequestParam("batchId") int batchId) {
        EnrollBatchExample batchExample = new EnrollBatchExample();
        EnrollBatchExample.Criteria criteria = batchExample.createCriteria();
        criteria.andBatchIdEqualTo(batchId);

        EnrollBatch enrollBatch = new EnrollBatch();
        enrollBatch.setStatus("1");

        int count = enrollbatchService.updateByExampleSelective(enrollBatch, batchExample);

        if (count > 0) return new BaseResult(0, "启用成功", null);

        return new BaseResult(-1, "启用成功", null);
    }



    @ApiOperation(value = "新增批次")
    @RequiresPermissions("enroll:batch:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        return "/manage/batch/create.jsp";
    }


    @ApiOperation(value = "新增批次")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("enroll:batch:create")
    @ResponseBody
    public Object create(@Valid EnrollBatch enrollbatch,
                         BindingResult bindingResult,
                         HttpServletRequest request,
                         SpecialtyInfoDto specialtyInfoDto) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        if (StringUtils.isBlank(relationCode)) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("学校编码为空!"));
        }

        //字段重复校验
        EnrollBatchExample enrollbatchExample = new EnrollBatchExample();
        enrollbatchExample.createCriteria()
                .andBatchNameEqualTo(enrollbatch.getBatchName())
                .andSchoolCodeEqualTo(relationCode)
                .andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
        int existCount = enrollbatchService.countByExample(enrollbatchExample);
        if (existCount > 0) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("批次名称重复!"));
        }

        enrollbatch.setCreator(UserUtil.getCurrentUser());
        enrollbatch.setCtime(System.currentTimeMillis());
        enrollbatch.setYear(Integer.parseInt(DateUtil.getYear()));
        enrollbatch.setSchoolCode(relationCode);
        int count = enrollbatchService.insertBatchAndPlan(enrollbatch, specialtyInfoDto);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "删除批次")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:delete")
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = enrollbatchService.deleteBatchAndPlan(ids);
        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改批次")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:update")
    public Object update(@PathVariable("id") String id,
                         Model model,
                         @ModelAttribute("model") EnrollBatch enrollBatch) {

        model.addAttribute("enrollbatch", enrollBatch);
        return "/manage/batch/update.jsp";
    }


    @ApiOperation(value = "修改批次")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @RequiresPermissions("enroll:batch:update")
    @ResponseBody
    public Object update(@PathVariable("id") String id,
                         @Valid @ModelAttribute("model") EnrollBatch enrollBatch,
                         BindingResult bindingResult,
                         SpecialtyInfoDto specialtyInfoDto) {

        //数据校验
        if (bindingResult.hasErrors()) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg(bindingResult));
        }

        //字段重复校验
        EnrollBatchExample enrollbatchExample = new EnrollBatchExample();
        enrollbatchExample.createCriteria()
                .andBatchIdNotEqualTo(enrollBatch.getBatchId())
                .andBatchNameEqualTo(enrollBatch.getBatchName())
                .andSchoolCodeEqualTo(enrollBatch.getSchoolCode())
                .andYearEqualTo(Integer.valueOf(DateUtil.getYear()));
        int existCount = enrollbatchService.countByExample(enrollbatchExample);
        if (existCount > 0) {
            return new EnrollResult(EnrollResultConstant.FAILED, getErrorMsg("批次名称重复!"));
        }

        int count = enrollbatchService.updateBatchAndPlan(enrollBatch, specialtyInfoDto);

        return new EnrollResult(EnrollResultConstant.SUCCESS, count);
    }

    /**
     * 为新增批次做数据初始化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSpecialtyInfo", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:read")
    @ResponseBody
    public Object getSpecialtyInfo(HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            throw new BusindessException("学校编码为空!");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("year", DateUtil.getYear());
        map.put("schoolCode", relationCode);
        List<SpecialtyInfoDto> chargeDetailPriceAndSpecialtyInfo =
                enrollChargedetailService.getChargeDetailPriceAndSpecialtyInfo(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", chargeDetailPriceAndSpecialtyInfo);
        return result;
    }

    /**
     * 为编辑批次做数据初始化
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUpdtingSpecialtyInfo/{id}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:read")
    @ResponseBody
    public Object getUpdtingSpecialtyInfo(@PathVariable(value = "id") int id,
                                          HttpServletRequest request) {

        String relationCode = (String) request.getSession().getAttribute("relationCode");
        if (StringUtils.isBlank(relationCode)) {
            throw new BusindessException("学校编码为空!");
        }
        EnrollBatch enrollBatch = enrollbatchService.selectByPrimaryKey(id);

        //获取计划人数信息
        EnrollPlanExample enrollPlanExample = new EnrollPlanExample();
        enrollPlanExample.createCriteria()
                .andYearEqualTo(Integer.valueOf(DateUtil.getYear()))
                .andSchoolCodeEqualTo(relationCode)
                .andBatchIdEqualTo(enrollBatch.getBatchId());
        List<EnrollPlan> enrollPlens = enrollPlanService.selectByExample(enrollPlanExample);

        HashMap<String, Object> map = new HashMap<>();
        map.put("year", DateUtil.getYear());
        map.put("schoolCode", relationCode);
        List<SpecialtyInfoDto> chargeDetailPriceAndSpecialtyInfo =
                enrollChargedetailService.getChargeDetailPriceAndSpecialtyInfo(map);

        for (SpecialtyInfoDto s : chargeDetailPriceAndSpecialtyInfo) {
            for (EnrollPlan p : enrollPlens) {
                if (s.getSpecialtyCode().equals(p.getSpecialtyCode())) {
                    s.setPlanNum(p.getPlanNum());
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("rows", chargeDetailPriceAndSpecialtyInfo);
        return result;
    }


    @ApiOperation(value = "复制批次")
    @RequestMapping(value = "/copy/{id}", method = RequestMethod.GET)
    @RequiresPermissions("enroll:batch:create")
    public Object copy(@PathVariable("id") String id,
                       Model model,
                       @ModelAttribute("model") EnrollBatch enrollBatch) {
        model.addAttribute("enrollbatch", enrollBatch);
        return "/manage/batch/copy.jsp";
    }

}
