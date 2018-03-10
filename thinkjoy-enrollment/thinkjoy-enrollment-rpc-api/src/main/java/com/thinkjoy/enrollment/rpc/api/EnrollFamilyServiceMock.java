package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollFamilyMapper;
import com.thinkjoy.enrollment.dao.model.EnrollFamily;
import com.thinkjoy.enrollment.dao.model.EnrollFamilyExample;

/**
* 降级实现EnrollFamilyService接口
* Created by user on 2017/12/25.
*/
public class EnrollFamilyServiceMock extends BaseServiceMock<EnrollFamilyMapper, EnrollFamily, EnrollFamilyExample> implements EnrollFamilyService {

}
