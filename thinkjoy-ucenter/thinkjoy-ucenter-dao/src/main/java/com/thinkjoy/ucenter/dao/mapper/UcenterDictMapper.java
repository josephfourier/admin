package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterDict;
import com.thinkjoy.ucenter.dao.model.UcenterDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterDictMapper {
    long countByExample(UcenterDictExample example);

    int deleteByExample(UcenterDictExample example);

    int deleteByPrimaryKey(Integer dictId);

    int insert(UcenterDict record);

    int insertSelective(UcenterDict record);

    List<UcenterDict> selectByExample(UcenterDictExample example);

    UcenterDict selectByPrimaryKey(Integer dictId);

    int updateByExampleSelective(@Param("record") UcenterDict record, @Param("example") UcenterDictExample example);

    int updateByExample(@Param("record") UcenterDict record, @Param("example") UcenterDictExample example);

    int updateByPrimaryKeySelective(UcenterDict record);

    int updateByPrimaryKey(UcenterDict record);
}