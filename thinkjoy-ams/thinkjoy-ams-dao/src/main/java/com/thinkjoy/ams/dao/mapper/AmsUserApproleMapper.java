package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsApprole;
import com.thinkjoy.ams.dao.model.AmsUserApprole;
import com.thinkjoy.ams.dao.model.AmsUserApproleExample;

import java.util.List;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface AmsUserApproleMapper {
    long countByExample(AmsUserApproleExample example);

    int deleteByExample(AmsUserApproleExample example);

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("usertypeId") Integer usertypeId, @Param("relationCode") String relationCode, @Param("approleId") Integer approleId);

    int insert(AmsUserApprole record);

    int insertSelective(AmsUserApprole record);

    List<AmsUserApprole> selectByExample(AmsUserApproleExample example);

    int updateByExampleSelective(@Param("record") AmsUserApprole record, @Param("example") AmsUserApproleExample example);

    int updateByExample(@Param("record") AmsUserApprole record, @Param("example") AmsUserApproleExample example);

    List<AmsApprole> getAppRolesByIdentity(@Param("userId") Integer userId,
                                           @Param("userType") Integer userType,
                                           @Param("relationCode") String relationCode);
}