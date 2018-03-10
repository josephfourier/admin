package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollBatch;
import com.thinkjoy.enrollment.dao.model.EnrollBatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollBatchMapper {
    long countByExample(EnrollBatchExample example);

    int deleteByExample(EnrollBatchExample example);

    int deleteByPrimaryKey(Integer batchId);

    int insert(EnrollBatch record);

    int insertSelective(EnrollBatch record);

    List<EnrollBatch> selectByExample(EnrollBatchExample example);

    EnrollBatch selectByPrimaryKey(Integer batchId);

    int updateByExampleSelective(@Param("record") EnrollBatch record, @Param("example") EnrollBatchExample example);

    int updateByExample(@Param("record") EnrollBatch record, @Param("example") EnrollBatchExample example);

    int updateByPrimaryKeySelective(EnrollBatch record);

    int updateByPrimaryKey(EnrollBatch record);
}