package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterSchoolMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
/**
* UcenterSchoolService实现
* Created by xufei on 2017/7/26.
*/
@Service
@Transactional
@BaseService
public class UcenterSchoolServiceImpl extends BaseServiceImpl<UcenterSchoolMapper, UcenterSchool, UcenterSchoolExample> implements UcenterSchoolService {

    private static Logger _log = LoggerFactory.getLogger(UcenterSchoolServiceImpl.class);

    @Autowired
    UcenterSchoolMapper ucenterSchoolMapper;

    @Override
    public String getNextCodeByAreaCode(String agencyCode, String areaCode) {
        Map map=new HashMap<String,Object>();
        map.put("areaCode", areaCode);
        String tempagencyCode=ucenterSchoolMapper.selectMaxCodeByMap(map);
        if(null==tempagencyCode||tempagencyCode.equals("0")){
            return areaCode+"0001";
        }
        return String.valueOf(Long.parseLong(tempagencyCode)+1);
    }
}