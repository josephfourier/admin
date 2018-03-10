package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsProject;
import com.thinkjoy.ams.dao.model.AmsProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsProjectMapper {
    long countByExample(AmsProjectExample example);

    int deleteByExample(AmsProjectExample example);

    int deleteByPrimaryKey(Integer projectId);

    int insert(AmsProject record);

    int insertSelective(AmsProject record);

    List<AmsProject> selectByExample(AmsProjectExample example);

    AmsProject selectByPrimaryKey(Integer projectId);

    int updateByExampleSelective(@Param("record") AmsProject record, @Param("example") AmsProjectExample example);

    int updateByExample(@Param("record") AmsProject record, @Param("example") AmsProjectExample example);

    int updateByPrimaryKeySelective(AmsProject record);

    int updateByPrimaryKey(AmsProject record);

    int deleteById(String id);
}