package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterTeacherClass;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterTeacherClassMapper {
    long countByExample(UcenterTeacherClassExample example);

    int deleteByExample(UcenterTeacherClassExample example);

    int deleteByPrimaryKey(@Param("classId") Integer classId, @Param("teacherId") Integer teacherId);

    int insert(UcenterTeacherClass record);

    int insertSelective(UcenterTeacherClass record);

    List<UcenterTeacherClass> selectByExample(UcenterTeacherClassExample example);

    int updateByExampleSelective(@Param("record") UcenterTeacherClass record, @Param("example") UcenterTeacherClassExample example);

    int updateByExample(@Param("record") UcenterTeacherClass record, @Param("example") UcenterTeacherClassExample example);
}