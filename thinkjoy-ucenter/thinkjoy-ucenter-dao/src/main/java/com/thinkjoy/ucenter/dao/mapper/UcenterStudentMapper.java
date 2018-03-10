package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterStudentMapper {
    long countByExample(UcenterStudentExample example);

    int deleteByExample(UcenterStudentExample example);

    int deleteByPrimaryKey(Integer studentId);

    int insert(UcenterStudent record);

    int insertSelective(UcenterStudent record);

    List<UcenterStudent> selectByExample(UcenterStudentExample example);

    UcenterStudent selectByPrimaryKey(Integer studentId);

    int updateByExampleSelective(@Param("record") UcenterStudent record, @Param("example") UcenterStudentExample example);

    int updateByExample(@Param("record") UcenterStudent record, @Param("example") UcenterStudentExample example);

    int updateByPrimaryKeySelective(UcenterStudent record);

    int updateByPrimaryKey(UcenterStudent record);
}