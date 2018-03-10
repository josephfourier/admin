package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterTeacher;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterTeacherDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterTeacherMapper {
    long countByExample(UcenterTeacherExample example);

    int deleteByExample(UcenterTeacherExample example);

    int deleteByPrimaryKey(Integer teacherId);

    int insert(UcenterTeacher record);

    int insertSelective(UcenterTeacher record);

    List<UcenterTeacher> selectByExample(UcenterTeacherExample example);

    UcenterTeacher selectByPrimaryKey(Integer teacherId);

    int updateByExampleSelective(@Param("record") UcenterTeacher record, @Param("example") UcenterTeacherExample example);

    int updateByExample(@Param("record") UcenterTeacher record, @Param("example") UcenterTeacherExample example);

    int updateByPrimaryKeySelective(UcenterTeacher record);

    int updateByPrimaryKey(UcenterTeacher record);

    List<UcenterTeacher> selectUcenterTeacherByPostId(Map<String, Object> map);

    List<UcenterTeacherDto> selectDataScopeUcenterTeacherInfoByAppIdAndRelationCode(Map<String, Object> map);

}