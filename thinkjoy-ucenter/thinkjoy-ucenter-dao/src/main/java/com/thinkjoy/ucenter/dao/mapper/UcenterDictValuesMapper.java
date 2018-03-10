package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import com.thinkjoy.ucenter.dao.model.UcenterDictValuesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterDictValuesMapper {
    long countByExample(UcenterDictValuesExample example);

    int deleteByExample(UcenterDictValuesExample example);

    int deleteByPrimaryKey(Integer valueId);

    int insert(UcenterDictValues record);

    int insertSelective(UcenterDictValues record);

    List<UcenterDictValues> selectByExample(UcenterDictValuesExample example);

    UcenterDictValues selectByPrimaryKey(Integer valueId);

    int updateByExampleSelective(@Param("record") UcenterDictValues record, @Param("example") UcenterDictValuesExample example);

    int updateByExample(@Param("record") UcenterDictValues record, @Param("example") UcenterDictValuesExample example);

    int updateByPrimaryKeySelective(UcenterDictValues record);

    int updateByPrimaryKey(UcenterDictValues record);
}