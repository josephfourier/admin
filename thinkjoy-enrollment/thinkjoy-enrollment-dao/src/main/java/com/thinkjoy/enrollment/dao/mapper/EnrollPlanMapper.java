package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollPlan;
import com.thinkjoy.enrollment.dao.model.EnrollPlanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnrollPlanMapper {
    long countByExample(EnrollPlanExample example);

    int deleteByExample(EnrollPlanExample example);

    int deleteByPrimaryKey(Integer planId);

    int insert(EnrollPlan record);

    int insertSelective(EnrollPlan record);

    List<EnrollPlan> selectByExample(EnrollPlanExample example);

    EnrollPlan selectByPrimaryKey(Integer planId);

    int updateByExampleSelective(@Param("record") EnrollPlan record, @Param("example") EnrollPlanExample example);

    int updateByExample(@Param("record") EnrollPlan record, @Param("example") EnrollPlanExample example);

    int updateByPrimaryKeySelective(EnrollPlan record);

    int updateByPrimaryKey(EnrollPlan record);
	Integer selectcountMap(Map map);
}