package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollTeacher;
import com.thinkjoy.enrollment.dao.model.EnrollTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollTeacherMapper {
    long countByExample(EnrollTeacherExample example);

    int deleteByExample(EnrollTeacherExample example);

    int deleteByPrimaryKey(Integer enrollteacherId);

    int insert(EnrollTeacher record);

    int insertSelective(EnrollTeacher record);

    List<EnrollTeacher> selectByExample(EnrollTeacherExample example);

    EnrollTeacher selectByPrimaryKey(Integer enrollteacherId);

    int updateByExampleSelective(@Param("record") EnrollTeacher record, @Param("example") EnrollTeacherExample example);

    int updateByExample(@Param("record") EnrollTeacher record, @Param("example") EnrollTeacherExample example);

    int updateByPrimaryKeySelective(EnrollTeacher record);

    int updateByPrimaryKey(EnrollTeacher record);
}