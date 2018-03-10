package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.AmsProjectSchoolagency;
import com.thinkjoy.ams.dao.model.AmsProjectSchoolagencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmsProjectSchoolagencyMapper {
    long countByExample(AmsProjectSchoolagencyExample example);

    int deleteByExample(AmsProjectSchoolagencyExample example);

    int deleteByPrimaryKey(@Param("projectId") Integer projectId, @Param("relationCode") String relationCode);

    int insert(AmsProjectSchoolagency record);

    int insertSelective(AmsProjectSchoolagency record);

    List<AmsProjectSchoolagency> selectByExample(AmsProjectSchoolagencyExample example);

    int updateByExampleSelective(@Param("record") AmsProjectSchoolagency record, @Param("example") AmsProjectSchoolagencyExample example);

    int updateByExample(@Param("record") AmsProjectSchoolagency record, @Param("example") AmsProjectSchoolagencyExample example);
}