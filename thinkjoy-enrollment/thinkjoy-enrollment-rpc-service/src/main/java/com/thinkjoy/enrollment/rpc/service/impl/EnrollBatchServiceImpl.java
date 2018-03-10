package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollBatchMapper;
import com.thinkjoy.enrollment.dao.mapper.EnrollPlanMapper;
import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.PlanDto;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;
import com.thinkjoy.enrollment.rpc.api.EnrollBatchService;
import com.thinkjoy.exception.BusindessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * EnrollBatchService实现
 * Created by user on 2017/11/2.
 */
@Service
@Transactional
@BaseService
public class EnrollBatchServiceImpl extends BaseServiceImpl<EnrollBatchMapper, EnrollBatch, EnrollBatchExample> implements EnrollBatchService {

    private static Logger _log = LoggerFactory.getLogger(EnrollBatchServiceImpl.class);

    @Autowired
    EnrollBatchMapper enrollBatchMapper;

    @Autowired
    EnrollPlanMapper enrollPlanMapper;

    @Override
    public int insertBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto) {

        int i = enrollBatchMapper.insertSelective(enrollBatch);

        assert enrollBatch.getBatchId() != null;

        List<PlanDto> planDtos = specialtyInfoDto.getPlanDtos();

        if (CollectionUtils.isNotEmpty(planDtos)) {

            for (PlanDto p : planDtos) {
                if (p.getPlanNum() == null || StringUtils.isBlank(p.getSpecialtyCode())) {
                    throw new BusindessException("招生计划数据出错,planNum或specialtyCode为空");
                }
                EnrollPlan enrollPlan = new EnrollPlan();
                enrollPlan.setBatchId(enrollBatch.getBatchId());
                enrollPlan.setSpecialtyCode(p.getSpecialtyCode());
                enrollPlan.setPlanNum(p.getPlanNum());

                enrollPlan.setCtime(System.currentTimeMillis());
                enrollPlan.setCreator(enrollBatch.getCreator());
                enrollPlan.setDescription(enrollBatch.getDescription());
                enrollPlan.setYear(enrollBatch.getYear());
                enrollPlan.setSchoolCode(enrollBatch.getSchoolCode());

                enrollPlanMapper.insertSelective(enrollPlan);
            }

        }

        return i;
    }

    @Override
    public int updateBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto) {
        int i = enrollBatchMapper.updateByPrimaryKeySelective(enrollBatch);

        assert enrollBatch.getBatchId() != null;

        List<PlanDto> planDtos = specialtyInfoDto.getPlanDtos();

        EnrollPlanExample enrollPlanExample = new EnrollPlanExample();
        enrollPlanExample.createCriteria()
                .andBatchIdEqualTo(enrollBatch.getBatchId())
                .andYearEqualTo(enrollBatch.getYear())
                .andSchoolCodeEqualTo(enrollBatch.getSchoolCode());

        int i1 = enrollPlanMapper.deleteByExample(enrollPlanExample);

        if (CollectionUtils.isNotEmpty(planDtos)) {

            for (PlanDto p : planDtos) {
                if (p.getPlanNum() == null || StringUtils.isBlank(p.getSpecialtyCode())) {
                    continue;
                }
                EnrollPlan enrollPlan = new EnrollPlan();
                enrollPlan.setBatchId(enrollBatch.getBatchId());
                enrollPlan.setSpecialtyCode(p.getSpecialtyCode());
                enrollPlan.setPlanNum(p.getPlanNum());

                enrollPlan.setCtime(System.currentTimeMillis());
                enrollPlan.setCreator(enrollBatch.getCreator());
                enrollPlan.setDescription(enrollBatch.getDescription());
                enrollPlan.setYear(enrollBatch.getYear());
                enrollPlan.setSchoolCode(enrollBatch.getSchoolCode());

                enrollPlanMapper.insertSelective(enrollPlan);
            }
        }
        return i;
    }

    @Override
    public int deleteBatchAndPlan(String ids) {

        if (StringUtils.isBlank(ids)) {
            return 0;
        }

        String[] idArray = ids.split("-");
        List<Integer> idList = new ArrayList<>();
        for (String idStr : idArray) {
            idList.add(Integer.parseInt(idStr));
        }

        int i = 0;
        try {
            //删除招生计划
            EnrollPlanExample enrollPlanExample = new EnrollPlanExample();
            enrollPlanExample.createCriteria()
                    .andBatchIdIn(idList);
            int i1 = enrollPlanMapper.deleteByExample(enrollPlanExample);

            //删除批次
            EnrollBatchExample enrollBatchExample = new EnrollBatchExample();
            enrollBatchExample.createCriteria()
                    .andBatchIdIn(idList);
            i = enrollBatchMapper.deleteByExample(enrollBatchExample);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {
                    //String res = handleException(e);
                    throw new RuntimeException("请先删除关联数据!");
                }
            }
            if (e instanceof DataIntegrityViolationException) {
                throw new RuntimeException("请先删除关联数据!");
            }
        }

        return i;
    }

}