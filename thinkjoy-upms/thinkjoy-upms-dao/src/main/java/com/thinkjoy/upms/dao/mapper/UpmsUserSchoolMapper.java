package com.thinkjoy.upms.dao.mapper;

import com.thinkjoy.upms.dao.model.UpmsUserSchool;
import com.thinkjoy.upms.dao.model.UpmsUserSchoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsUserSchoolMapper {
    long countByExample(UpmsUserSchoolExample example);

    int deleteByExample(UpmsUserSchoolExample example);

    int deleteByPrimaryKey(Integer userSchoolId);

    int insert(UpmsUserSchool record);

    int insertSelective(UpmsUserSchool record);

    List<UpmsUserSchool> selectByExample(UpmsUserSchoolExample example);

    UpmsUserSchool selectByPrimaryKey(Integer userSchoolId);

    int updateByExampleSelective(@Param("record") UpmsUserSchool record, @Param("example") UpmsUserSchoolExample example);

    int updateByExample(@Param("record") UpmsUserSchool record, @Param("example") UpmsUserSchoolExample example);

    int updateByPrimaryKeySelective(UpmsUserSchool record);

    int updateByPrimaryKey(UpmsUserSchool record);
}