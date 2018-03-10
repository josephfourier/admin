package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;

/**
* EnrollBatchService接口
* Created by user on 2017/11/2.
*/
public interface EnrollBatchService extends BaseService<EnrollBatch, EnrollBatchExample> {

    int insertBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto);

    int updateBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto);

    int deleteBatchAndPlan(String ids);
}