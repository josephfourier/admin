package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsApp;
import com.thinkjoy.ams.dao.model.AmsAppExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AmsAppMapper {
    long countByExample(AmsAppExample example);

    int deleteByExample(AmsAppExample example);

    int deleteByPrimaryKey(Integer appId);

    int insert(AmsApp record);

    int insertSelective(AmsApp record);

    List<AmsApp> selectByExample(AmsAppExample example);

    AmsApp selectByPrimaryKey(Integer appId);

    int updateByExampleSelective(@Param("record") AmsApp record, @Param("example") AmsAppExample example);

    int updateByExample(@Param("record") AmsApp record, @Param("example") AmsAppExample example);

    int updateByPrimaryKeySelective(AmsApp record);

    int updateByPrimaryKey(AmsApp record);

    List<AmsApp> getAppsByAgencyOrSchoolCode(Map<String, String> map);

    List<AmsApp> getUserAppByAgencyOrSchoolCode(Map<String, String> map);

    AmsApp getIsPersonalByClientId(String appId);
}