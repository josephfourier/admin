package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterTeacherPost;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPostExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UcenterTeacherPostMapper {
    long countByExample(UcenterTeacherPostExample example);

    int deleteByExample(UcenterTeacherPostExample example);

    int deleteByPrimaryKey(@Param("teacherId") Integer teacherId, @Param("postId") Integer postId, @Param("departmentId") Integer departmentId);

    int insert(UcenterTeacherPost record);

    int insertSelective(UcenterTeacherPost record);

    List<UcenterTeacherPost> selectByExample(UcenterTeacherPostExample example);

    int updateByExampleSelective(@Param("record") UcenterTeacherPost record, @Param("example") UcenterTeacherPostExample example);

    int updateByExample(@Param("record") UcenterTeacherPost record, @Param("example") UcenterTeacherPostExample example);

    int updateByPrimaryKeySelective(UcenterTeacherPost record);

    int updateSelective(@Param("postId") Integer postId);


    int updateByTeacherId(@Param("teacherId") Integer teacherId);
}