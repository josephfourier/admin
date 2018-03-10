package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.enrollment.dao.model.EnrollTemplate;
import com.thinkjoy.enrollment.dao.model.EnrollTemplateExample;

/**
* EnrollTemplateService接口
* Created by user on 2017/11/21.
*/
public interface EnrollTemplateService extends BaseService<EnrollTemplate, EnrollTemplateExample> {


   void tempOPT (String templateId,String schoolcode);
}