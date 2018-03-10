package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollTemplateMapper;
import com.thinkjoy.enrollment.dao.model.EnrollTemplate;
import com.thinkjoy.enrollment.dao.model.EnrollTemplateExample;
import com.thinkjoy.enrollment.rpc.api.EnrollTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* EnrollTemplateService实现
* Created by user on 2017/11/21.
*/
@Service
@Transactional
@BaseService
public class EnrollTemplateServiceImpl extends BaseServiceImpl<EnrollTemplateMapper, EnrollTemplate, EnrollTemplateExample> implements EnrollTemplateService {

    private static Logger _log = LoggerFactory.getLogger(EnrollTemplateServiceImpl.class);

    @Autowired
    EnrollTemplateMapper enrollTemplateMapper;

    @Autowired
    EnrollTemplateService enrollTemplateService;

    @Override
    public void tempOPT(String templateId, String schoolcode) {

        EnrollTemplateExample enrollTemplateExample=new EnrollTemplateExample();
        EnrollTemplateExample.Criteria criteria=enrollTemplateExample.createCriteria();
        criteria.andSchoolCodeEqualTo(schoolcode);

        EnrollTemplate enrollTemplate=new EnrollTemplate();
        enrollTemplate.setStatus("1");
        //修改学校所有的模板状态为未设置;
        enrollTemplateService.updateByExampleSelective(enrollTemplate, enrollTemplateExample);



        EnrollTemplateExample enrollTemplateExample1=new EnrollTemplateExample();
        EnrollTemplateExample.Criteria criteria1=enrollTemplateExample1.createCriteria();
        criteria1.andTemplateIdEqualTo(Integer.parseInt(templateId));

        EnrollTemplate enrollTemplate1=new EnrollTemplate();
        enrollTemplate1.setStatus("0");
        //只修改当前选择的未已设置;
        enrollTemplateService.updateByExampleSelective(enrollTemplate1,enrollTemplateExample1);

    }
}