package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterFamily;
import com.thinkjoy.ucenter.dao.model.UcenterFamilyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterFamilyMapper {
    long countByExample(UcenterFamilyExample example);

    int deleteByExample(UcenterFamilyExample example);

    int deleteByPrimaryKey(Integer family);

    int insert(UcenterFamily record);

    int insertSelective(UcenterFamily record);

    List<UcenterFamily> selectByExample(UcenterFamilyExample example);

    UcenterFamily selectByPrimaryKey(Integer family);

    int updateByExampleSelective(@Param("record") UcenterFamily record, @Param("example") UcenterFamilyExample example);

    int updateByExample(@Param("record") UcenterFamily record, @Param("example") UcenterFamilyExample example);

    int updateByPrimaryKeySelective(UcenterFamily record);

    int updateByPrimaryKey(UcenterFamily record);
}