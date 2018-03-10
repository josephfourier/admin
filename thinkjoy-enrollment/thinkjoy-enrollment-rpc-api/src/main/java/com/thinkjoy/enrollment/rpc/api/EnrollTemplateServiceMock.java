package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollTemplateMapper;
import com.thinkjoy.enrollment.dao.model.EnrollTemplate;
import com.thinkjoy.enrollment.dao.model.EnrollTemplateExample;

/**
* 降级实现EnrollTemplateService接口
* Created by user on 2017/11/21.
*/
public class EnrollTemplateServiceMock extends BaseServiceMock<EnrollTemplateMapper, EnrollTemplate, EnrollTemplateExample> implements EnrollTemplateService {

    @Override
    public void tempOPT(String templateId, String schoolcode) {

    }
}
