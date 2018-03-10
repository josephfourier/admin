package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsProjectSchoolagencyMapper;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagencyExample;
import com.thinkjoy.ams.rpc.api.AmsProjectSchoolagencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* AmsProjectSchoolagencyService实现
* Created by shuzheng on 2017/7/27.
*/
@Service
@Transactional
@BaseService
public class AmsProjectSchoolagencyServiceImpl extends BaseServiceImpl<AmsProjectSchoolagencyMapper, AmsProjectSchoolagency, AmsProjectSchoolagencyExample> implements AmsProjectSchoolagencyService {

    private static Logger _log = LoggerFactory.getLogger(AmsProjectSchoolagencyServiceImpl.class);

    @Autowired
    AmsProjectSchoolagencyMapper amsProjectSchoolagencyMapper;

    @Override
    public int addAmsProjectSchoolagency(JSONArray datas, Integer pid){

        List<String> deleteCodes = new ArrayList<>();

        for(int i = 0; i<datas.size(); i++){
            JSONObject jsonObject = datas.getJSONObject(i);

            if (!jsonObject.getBoolean("checked")){
                //过滤取消的节点
                deleteCodes.add(jsonObject.getString("code"));
            }else {
                //新增添加的节点
                AmsProjectSchoolagency amsProjectSchoolagency = new AmsProjectSchoolagency();
                amsProjectSchoolagency.setProjectId(pid);
                amsProjectSchoolagency.setRelationCode(jsonObject.getString("code"));
                amsProjectSchoolagencyMapper.insertSelective(amsProjectSchoolagency);
            }
        }

        //删除取消的节点
        if (deleteCodes.size() > 0){
            AmsProjectSchoolagencyExample amsProjectSchoolagencyExample = new AmsProjectSchoolagencyExample();
            amsProjectSchoolagencyExample.createCriteria()
                    .andProjectIdEqualTo(pid)
                    .andRelationCodeIn(deleteCodes);
            amsProjectSchoolagencyMapper.deleteByExample(amsProjectSchoolagencyExample);
        }

        return datas.size();
    }

}