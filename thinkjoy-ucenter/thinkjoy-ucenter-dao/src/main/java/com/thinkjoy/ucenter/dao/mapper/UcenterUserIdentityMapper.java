package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterUserIdentity;
import com.thinkjoy.ucenter.dao.model.UcenterUserIdentityExample;
import java.util.List;

import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterUserIdentityDto;
import org.apache.ibatis.annotations.Param;

public interface UcenterUserIdentityMapper {
    long countByExample(UcenterUserIdentityExample example);

    int deleteByExample(UcenterUserIdentityExample example);

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("usertypeId") Integer usertypeId, @Param("relationCode") String relationCode);

    int insert(UcenterUserIdentity record);

    int insertSelective(UcenterUserIdentity record);

    List<UcenterUserIdentity> selectByExample(UcenterUserIdentityExample example);

    UcenterUserIdentity selectByPrimaryKey(@Param("userId") Integer userId, @Param("usertypeId") Integer usertypeId, @Param("relationCode") String relationCode);

    int updateByExampleSelective(@Param("record") UcenterUserIdentity record, @Param("example") UcenterUserIdentityExample example);

    int updateByExample(@Param("record") UcenterUserIdentity record, @Param("example") UcenterUserIdentityExample example);

    int updateByPrimaryKeySelective(UcenterUserIdentity record);

    int updateByPrimaryKey(UcenterUserIdentity record);

    List<UcenterUserIdentityDto> getUserIdentitesByUserLoginName(String userLoginName);

    List<UcenterUserIdentity> getUserIdentityRelationByUserId(Integer userId);

    int insertBatch(List<UcenterUserIdentity> record);
}