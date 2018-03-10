package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetail;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;

import java.util.List;
import java.util.Map;

/**
* EnrollChargedetailService接口
* Created by user on 2017/11/10.
*/
public interface EnrollChargedetailService extends BaseService<EnrollChargedetail, EnrollChargedetailExample> {

    int insertChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes);

    int updateChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes);

    int deleteChargeDetail(String ids);

    List<SpecialtyInfoDto> getChargeDetailPriceAndSpecialtyInfo(Map<String, Object> map);

}