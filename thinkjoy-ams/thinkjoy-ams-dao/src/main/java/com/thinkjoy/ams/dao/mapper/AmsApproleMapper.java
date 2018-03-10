package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsApprole;
import com.thinkjoy.ams.dao.model.AmsApproleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AmsApproleMapper {
    long countByExample(AmsApproleExample example);

    int deleteByExample(AmsApproleExample example);

    int deleteByPrimaryKey(Integer approleId);

    int insert(AmsApprole record);

    int insertSelective(AmsApprole record);

    List<AmsApprole> selectByExample(AmsApproleExample example);

    AmsApprole selectByPrimaryKey(Integer approleId);

    int updateByExampleSelective(@Param("record") AmsApprole record, @Param("example") AmsApproleExample example);

    int updateByExample(@Param("record") AmsApprole record, @Param("example") AmsApproleExample example);

    int updateByPrimaryKeySelective(AmsApprole record);

    int updateByPrimaryKey(AmsApprole record);

    int deleteById(String id);

    List<Integer> getAmsAppIdByRoleCode(Map<String, String> map);

    List<AmsApprole> getAppRolesByIdentity(@Param("userId") Integer userId,
                                           @Param("userType")Integer userType,
                                           @Param("relationCode")String relationCode);

    int updatePerpersonal(Map map);

    List<AmsApprole> getAmsApproleByIdentityAndAppId(Map map);
}