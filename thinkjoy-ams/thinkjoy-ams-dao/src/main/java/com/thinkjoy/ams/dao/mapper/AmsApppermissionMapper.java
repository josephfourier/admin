package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsApppermission;
import com.thinkjoy.ams.dao.model.AmsApppermissionExample;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface AmsApppermissionMapper {
    long countByExample(AmsApppermissionExample example);

    int deleteByExample(AmsApppermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(AmsApppermission record);

    int insertSelective(AmsApppermission record);

    List<AmsApppermission> selectByExample(AmsApppermissionExample example);

    AmsApppermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") AmsApppermission record, @Param("example") AmsApppermissionExample example);

    int updateByExample(@Param("record") AmsApppermission record, @Param("example") AmsApppermissionExample example);

    int updateByPrimaryKeySelective(AmsApppermission record);

    int updateByPrimaryKey(AmsApppermission record);

    int deleteById(String id);

    List<AmsApppermission> getAppPermissionByRelationCodeAndUserType(Map<String, String> map);

    List<AmsApppermission> getAppPermissionsByApproleId(@Param("set") Set<Integer> set);

}