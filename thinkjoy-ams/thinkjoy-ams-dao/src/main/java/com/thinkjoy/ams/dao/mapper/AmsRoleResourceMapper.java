package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.ams.dao.model.AmsRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsRoleResourceMapper {
    long countByExample(AmsRoleResourceExample example);

    int deleteByExample(AmsRoleResourceExample example);

    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId);

    int insert(AmsRoleResource record);

    int insertSelective(AmsRoleResource record);

    List<AmsRoleResource> selectByExample(AmsRoleResourceExample example);

    int updateByExampleSelective(@Param("record") AmsRoleResource record, @Param("example") AmsRoleResourceExample example);

    int updateByExample(@Param("record") AmsRoleResource record, @Param("example") AmsRoleResourceExample example);
}