package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargedetailMapper;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetail;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;

import java.util.List;
import java.util.Map;

/**
* 降级实现EnrollChargedetailService接口
* Created by user on 2017/11/10.
*/
public class EnrollChargedetailServiceMock extends BaseServiceMock<EnrollChargedetailMapper, EnrollChargedetail, EnrollChargedetailExample> implements EnrollChargedetailService {

    @Override
    public int insertChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes) {
        return 0;
    }

    @Override
    public int updateChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes) {
        return 0;
    }

    @Override
    public int deleteChargeDetail(String ids) {
        return 0;
    }

    @Override
    public List<SpecialtyInfoDto> getChargeDetailPriceAndSpecialtyInfo(Map<String, Object> map) {
        return null;
    }
}
