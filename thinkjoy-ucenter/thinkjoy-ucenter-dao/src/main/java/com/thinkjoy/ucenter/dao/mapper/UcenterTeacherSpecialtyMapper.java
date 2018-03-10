package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterTeacherSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherSpecialtyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterTeacherSpecialtyMapper {
    long countByExample(UcenterTeacherSpecialtyExample example);

    int deleteByExample(UcenterTeacherSpecialtyExample example);

    int deleteByPrimaryKey(@Param("teacherId") Integer teacherId, @Param("specialtyCode") String specialtyCode);

    int insert(UcenterTeacherSpecialty record);

    int insertSelective(UcenterTeacherSpecialty record);

    List<UcenterTeacherSpecialty> selectByExample(UcenterTeacherSpecialtyExample example);

    int updateByExampleSelective(@Param("record") UcenterTeacherSpecialty record, @Param("example") UcenterTeacherSpecialtyExample example);

    int updateByExample(@Param("record") UcenterTeacherSpecialty record, @Param("example") UcenterTeacherSpecialtyExample example);
}