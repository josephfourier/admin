package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterSchoolMapper {
    long countByExample(UcenterSchoolExample example);

    int deleteByExample(UcenterSchoolExample example);

    int deleteByPrimaryKey(Integer schoolId);

    int insert(UcenterSchool record);

    int insertSelective(UcenterSchool record);

    List<UcenterSchool> selectByExample(UcenterSchoolExample example);

    UcenterSchool selectByPrimaryKey(Integer schoolId);

    int updateByExampleSelective(@Param("record") UcenterSchool record, @Param("example") UcenterSchoolExample example);

    int updateByExample(@Param("record") UcenterSchool record, @Param("example") UcenterSchoolExample example);

    int updateByPrimaryKeySelective(UcenterSchool record);

    int updateByPrimaryKey(UcenterSchool record);
	String selectMaxCodeByMap(Map map);

}