package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollBatchMapper;
import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;

/**
* 降级实现EnrollBatchService接口
* Created by user on 2017/11/2.
*/
public class EnrollBatchServiceMock extends BaseServiceMock<EnrollBatchMapper, EnrollBatch, EnrollBatchExample> implements EnrollBatchService {

    @Override
    public int insertBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto) {
        return 0;
    }

    @Override
    public int updateBatchAndPlan(EnrollBatch enrollBatch, SpecialtyInfoDto specialtyInfoDto) {
        return 0;
    }

    @Override
    public int deleteBatchAndPlan(String ids) {
        return 0;
    }
}
