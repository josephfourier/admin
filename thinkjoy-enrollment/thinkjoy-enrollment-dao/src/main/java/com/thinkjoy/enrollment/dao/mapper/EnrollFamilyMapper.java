package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollFamily;
import com.thinkjoy.enrollment.dao.model.EnrollFamilyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollFamilyMapper {
    long countByExample(EnrollFamilyExample example);

    int deleteByExample(EnrollFamilyExample example);

    int deleteByPrimaryKey(Integer family);

    int insert(EnrollFamily record);

    int insertSelective(EnrollFamily record);

    List<EnrollFamily> selectByExample(EnrollFamilyExample example);

    EnrollFamily selectByPrimaryKey(Integer family);

    int updateByExampleSelective(@Param("record") EnrollFamily record, @Param("example") EnrollFamilyExample example);

    int updateByExample(@Param("record") EnrollFamily record, @Param("example") EnrollFamilyExample example);

    int updateByPrimaryKeySelective(EnrollFamily record);

    int updateByPrimaryKey(EnrollFamily record);
}