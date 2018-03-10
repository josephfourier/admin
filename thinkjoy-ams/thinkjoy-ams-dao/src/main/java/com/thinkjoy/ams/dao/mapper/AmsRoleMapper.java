package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsRole;
import com.thinkjoy.ams.dao.model.AmsRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsRoleMapper {
    long countByExample(AmsRoleExample example);

    int deleteByExample(AmsRoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(AmsRole record);

    int insertSelective(AmsRole record);

    List<AmsRole> selectByExample(AmsRoleExample example);

    AmsRole selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") AmsRole record, @Param("example") AmsRoleExample example);

    int updateByExample(@Param("record") AmsRole record, @Param("example") AmsRoleExample example);

    int updateByPrimaryKeySelective(AmsRole record);

    int updateByPrimaryKey(AmsRole record);

    int deleteById(String id);
}