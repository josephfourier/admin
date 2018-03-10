package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterSpecialty;
import com.thinkjoy.ucenter.dao.model.UcenterSpecialtyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterSpecialtyMapper {
    long countByExample(UcenterSpecialtyExample example);

    int deleteByExample(UcenterSpecialtyExample example);

    int deleteByPrimaryKey(Integer specialtyId);

    int insert(UcenterSpecialty record);

    int insertSelective(UcenterSpecialty record);

    List<UcenterSpecialty> selectByExample(UcenterSpecialtyExample example);

    UcenterSpecialty selectByPrimaryKey(Integer specialtyId);

    int updateByExampleSelective(@Param("record") UcenterSpecialty record, @Param("example") UcenterSpecialtyExample example);

    int updateByExample(@Param("record") UcenterSpecialty record, @Param("example") UcenterSpecialtyExample example);

    int updateByPrimaryKeySelective(UcenterSpecialty record);

    int updateByPrimaryKey(UcenterSpecialty record);

	UcenterSpecialty selectspecialtybeanMap(Map map);

	UcenterSpecialty selectspecialtyNobeanMap(Map map);
}