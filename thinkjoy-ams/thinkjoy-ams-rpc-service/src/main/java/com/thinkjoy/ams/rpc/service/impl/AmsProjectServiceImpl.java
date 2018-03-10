package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.ams.dao.mapper.AmsProjectSchoolagencyMapper;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagencyExample;
import com.thinkjoy.ams.rpc.api.AmsProjectAppService;
import com.thinkjoy.ams.rpc.api.AmsProjectSchoolagencyService;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsProjectMapper;
import com.thinkjoy.ams.dao.model.AmsProject;
import com.thinkjoy.ams.dao.model.AmsProjectExample;
import com.thinkjoy.ams.rpc.api.AmsProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * AmsProjectService实现
 * Created by shuzheng on 2017/7/27.
 */
@Service
@Transactional
@BaseService
public class AmsProjectServiceImpl extends BaseServiceImpl<AmsProjectMapper, AmsProject, AmsProjectExample> implements AmsProjectService {

    private static Logger _log = LoggerFactory.getLogger(AmsProjectServiceImpl.class);

    @Autowired
    private AmsProjectSchoolagencyMapper amsProjectSchoolagencyMapper;

    @Autowired
    private AmsProjectSchoolagencyService amsProjectSchoolagencyService;

    @Autowired
    private AmsProjectAppService amsProjectAppService;


    /**
     * 获取项目已关联的组织机构
     *
     * @param pid 项目id
     */
    @Override
    public List<String> getAmsProjectRelatedCodes(Integer pid) {
        List<String> codes = new ArrayList<>();

        AmsProjectSchoolagencyExample amsProjectSchoolagencyExample = new AmsProjectSchoolagencyExample();
        amsProjectSchoolagencyExample.createCriteria().andProjectIdEqualTo(pid);
        List<AmsProjectSchoolagency> amsProjectSchoolagencies = amsProjectSchoolagencyMapper.selectByExample(amsProjectSchoolagencyExample);

        for (AmsProjectSchoolagency a : amsProjectSchoolagencies) {
            codes.add(a.getRelationCode());
        }

        return codes;
    }

    @Override
    public int relateAgencyAndApp(JSONArray datas, String[] appId, Integer pid) {

        int count = amsProjectSchoolagencyService.addAmsProjectSchoolagency(datas, pid);

        int count1 = amsProjectAppService.addProjectApp(appId, String.valueOf(pid));

        return count + count1;
    }

}