package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsResources;
import com.thinkjoy.ams.dao.model.AmsResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsResourcesMapper {
    long countByExample(AmsResourcesExample example);

    int deleteByExample(AmsResourcesExample example);

    int deleteByPrimaryKey(Integer resourceId);

    int insert(AmsResources record);

    int insertSelective(AmsResources record);

    List<AmsResources> selectByExample(AmsResourcesExample example);

    AmsResources selectByPrimaryKey(Integer resourceId);

    int updateByExampleSelective(@Param("record") AmsResources record, @Param("example") AmsResourcesExample example);

    int updateByExample(@Param("record") AmsResources record, @Param("example") AmsResourcesExample example);

    int updateByPrimaryKeySelective(AmsResources record);

    int updateByPrimaryKey(AmsResources record);

    List<AmsResources> getResourcesByRoleId(Integer roleId);

    int deleteById(String id);
}