package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsProjectApp;
import com.thinkjoy.ams.dao.model.AmsProjectAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsProjectAppMapper {
    long countByExample(AmsProjectAppExample example);

    int deleteByExample(AmsProjectAppExample example);

    int deleteByPrimaryKey(@Param("appId") Integer appId, @Param("projectId") Integer projectId);

    int insert(AmsProjectApp record);

    int insertSelective(AmsProjectApp record);

    List<AmsProjectApp> selectByExample(AmsProjectAppExample example);

    int updateByExampleSelective(@Param("record") AmsProjectApp record, @Param("example") AmsProjectAppExample example);

    int updateByExample(@Param("record") AmsProjectApp record, @Param("example") AmsProjectAppExample example);
}