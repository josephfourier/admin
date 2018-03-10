package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLog;
import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollSpecialtychangeLogMapper {
    long countByExample(EnrollSpecialtychangeLogExample example);

    int deleteByExample(EnrollSpecialtychangeLogExample example);

    int deleteByPrimaryKey(Integer changeId);

    int insert(EnrollSpecialtychangeLog record);

    int insertSelective(EnrollSpecialtychangeLog record);

    List<EnrollSpecialtychangeLog> selectByExample(EnrollSpecialtychangeLogExample example);

    EnrollSpecialtychangeLog selectByPrimaryKey(Integer changeId);

    int updateByExampleSelective(@Param("record") EnrollSpecialtychangeLog record, @Param("example") EnrollSpecialtychangeLogExample example);

    int updateByExample(@Param("record") EnrollSpecialtychangeLog record, @Param("example") EnrollSpecialtychangeLogExample example);

    int updateByPrimaryKeySelective(EnrollSpecialtychangeLog record);

    int updateByPrimaryKey(EnrollSpecialtychangeLog record);
}