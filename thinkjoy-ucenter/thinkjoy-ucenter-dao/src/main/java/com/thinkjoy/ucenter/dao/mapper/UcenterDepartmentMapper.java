package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterDepartment;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterDepartmentMapper {
    long countByExample(UcenterDepartmentExample example);

    int deleteByExample(UcenterDepartmentExample example);

    int deleteByPrimaryKey(Integer departmentId);

    int insert(UcenterDepartment record);

    int insertSelective(UcenterDepartment record);

    List<UcenterDepartment> selectByExample(UcenterDepartmentExample example);

    UcenterDepartment selectByPrimaryKey(Integer departmentId);

    int updateByExampleSelective(@Param("record") UcenterDepartment record, @Param("example") UcenterDepartmentExample example);

    int updateByExample(@Param("record") UcenterDepartment record, @Param("example") UcenterDepartmentExample example);

    int updateByPrimaryKeySelective(UcenterDepartment record);

    int updateByPrimaryKey(UcenterDepartment record);
}