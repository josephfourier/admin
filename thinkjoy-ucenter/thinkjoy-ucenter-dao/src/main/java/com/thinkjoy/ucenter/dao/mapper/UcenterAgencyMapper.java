package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterAgencyMapper {
    long countByExample(UcenterAgencyExample example);

    int deleteByExample(UcenterAgencyExample example);

    int deleteByPrimaryKey(Integer agencyId);

    int insert(UcenterAgency record);

    int insertSelective(UcenterAgency record);

    List<UcenterAgency> selectByExample(UcenterAgencyExample example);

    UcenterAgency selectByPrimaryKey(Integer agencyId);

    int updateByExampleSelective(@Param("record") UcenterAgency record, @Param("example") UcenterAgencyExample example);

    int updateByExample(@Param("record") UcenterAgency record, @Param("example") UcenterAgencyExample example);

    int updateByPrimaryKeySelective(UcenterAgency record);

    int updateByPrimaryKey(UcenterAgency record);

    String selectMaxCodeByMap(Map map);

}