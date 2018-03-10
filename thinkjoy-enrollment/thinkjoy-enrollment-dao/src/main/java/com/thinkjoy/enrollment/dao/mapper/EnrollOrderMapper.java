package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollOrder;
import com.thinkjoy.enrollment.dao.model.EnrollOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollOrderMapper {
    long countByExample(EnrollOrderExample example);

    int deleteByExample(EnrollOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EnrollOrder record);

    int insertSelective(EnrollOrder record);

    List<EnrollOrder> selectByExample(EnrollOrderExample example);

    EnrollOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EnrollOrder record, @Param("example") EnrollOrderExample example);

    int updateByExample(@Param("record") EnrollOrder record, @Param("example") EnrollOrderExample example);

    int updateByPrimaryKeySelective(EnrollOrder record);

    int updateByPrimaryKey(EnrollOrder record);
}