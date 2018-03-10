package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirection;
import com.thinkjoy.ucenter.dao.model.UcenterTrainingdirectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterTrainingdirectionMapper {
    long countByExample(UcenterTrainingdirectionExample example);

    int deleteByExample(UcenterTrainingdirectionExample example);

    int deleteByPrimaryKey(Integer trdrId);

    int insert(UcenterTrainingdirection record);

    int insertSelective(UcenterTrainingdirection record);

    List<UcenterTrainingdirection> selectByExample(UcenterTrainingdirectionExample example);

    UcenterTrainingdirection selectByPrimaryKey(Integer trdrId);

    int updateByExampleSelective(@Param("record") UcenterTrainingdirection record, @Param("example") UcenterTrainingdirectionExample example);

    int updateByExample(@Param("record") UcenterTrainingdirection record, @Param("example") UcenterTrainingdirectionExample example);

    int updateByPrimaryKeySelective(UcenterTrainingdirection record);

    int updateByPrimaryKey(UcenterTrainingdirection record);
}