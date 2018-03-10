package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollChargeitem;
import com.thinkjoy.enrollment.dao.model.EnrollChargeitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollChargeitemMapper {
    long countByExample(EnrollChargeitemExample example);

    int deleteByExample(EnrollChargeitemExample example);

    int deleteByPrimaryKey(Integer itemId);

    int insert(EnrollChargeitem record);

    int insertSelective(EnrollChargeitem record);

    List<EnrollChargeitem> selectByExample(EnrollChargeitemExample example);

    EnrollChargeitem selectByPrimaryKey(Integer itemId);

    int updateByExampleSelective(@Param("record") EnrollChargeitem record, @Param("example") EnrollChargeitemExample example);

    int updateByExample(@Param("record") EnrollChargeitem record, @Param("example") EnrollChargeitemExample example);

    int updateByPrimaryKeySelective(EnrollChargeitem record);

    int updateByPrimaryKey(EnrollChargeitem record);
}