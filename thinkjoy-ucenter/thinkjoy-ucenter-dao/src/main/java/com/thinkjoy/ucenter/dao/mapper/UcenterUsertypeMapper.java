package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterUsertype;
import com.thinkjoy.ucenter.dao.model.UcenterUsertypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UcenterUsertypeMapper {
    long countByExample(UcenterUsertypeExample example);

    int deleteByExample(UcenterUsertypeExample example);

    int deleteByPrimaryKey(Integer usertypeId);

    int insert(UcenterUsertype record);

    int insertSelective(UcenterUsertype record);

    List<UcenterUsertype> selectByExample(UcenterUsertypeExample example);

    UcenterUsertype selectByPrimaryKey(Integer usertypeId);

    int updateByExampleSelective(@Param("record") UcenterUsertype record, @Param("example") UcenterUsertypeExample example);

    int updateByExample(@Param("record") UcenterUsertype record, @Param("example") UcenterUsertypeExample example);

    int updateByPrimaryKeySelective(UcenterUsertype record);

    int updateByPrimaryKey(UcenterUsertype record);

    List<UcenterUsertype> getall();

}