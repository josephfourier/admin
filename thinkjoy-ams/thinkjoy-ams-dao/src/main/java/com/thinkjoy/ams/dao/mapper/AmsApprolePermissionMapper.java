package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsApprolePermission;
import com.thinkjoy.ams.dao.model.AmsApprolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsApprolePermissionMapper {
    long countByExample(AmsApprolePermissionExample example);

    int deleteByExample(AmsApprolePermissionExample example);

    int deleteByPrimaryKey(@Param("permissionId") Integer permissionId, @Param("approleId") Integer approleId);

    int insert(AmsApprolePermission record);

    int insertSelective(AmsApprolePermission record);

    List<AmsApprolePermission> selectByExample(AmsApprolePermissionExample example);

    int updateByExampleSelective(@Param("record") AmsApprolePermission record, @Param("example") AmsApprolePermissionExample example);

    int updateByExample(@Param("record") AmsApprolePermission record, @Param("example") AmsApprolePermissionExample example);
}