package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsPerprojectApp;
import com.thinkjoy.ams.dao.model.AmsPerprojectAppExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AmsPerprojectAppMapper {
    long countByExample(AmsPerprojectAppExample example);

    int deleteByExample(AmsPerprojectAppExample example);

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("usertypeId") Integer usertypeId, @Param("relationCode") String relationCode, @Param("appId") Integer appId);

    int insert(AmsPerprojectApp record);

    int insertSelective(AmsPerprojectApp record);

    List<AmsPerprojectApp> selectByExample(AmsPerprojectAppExample example);

    int updateByExampleSelective(@Param("record") AmsPerprojectApp record, @Param("example") AmsPerprojectAppExample example);

    int updateByExample(@Param("record") AmsPerprojectApp record, @Param("example") AmsPerprojectAppExample example);

    int updatePersonal(Map map);
}