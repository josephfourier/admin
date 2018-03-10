package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLog;
import com.thinkjoy.ucenter.dao.model.UcenterDatasyncLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterDatasyncLogMapper {
    long countByExample(UcenterDatasyncLogExample example);

    int deleteByExample(UcenterDatasyncLogExample example);

    int deleteByPrimaryKey(Integer datasyncId);

    int insert(UcenterDatasyncLog record);

    int insertSelective(UcenterDatasyncLog record);

    List<UcenterDatasyncLog> selectByExampleWithBLOBs(UcenterDatasyncLogExample example);

    List<UcenterDatasyncLog> selectByExample(UcenterDatasyncLogExample example);

    UcenterDatasyncLog selectByPrimaryKey(Integer datasyncId);

    int updateByExampleSelective(@Param("record") UcenterDatasyncLog record, @Param("example") UcenterDatasyncLogExample example);

    int updateByExampleWithBLOBs(@Param("record") UcenterDatasyncLog record, @Param("example") UcenterDatasyncLogExample example);

    int updateByExample(@Param("record") UcenterDatasyncLog record, @Param("example") UcenterDatasyncLogExample example);

    int updateByPrimaryKeySelective(UcenterDatasyncLog record);

    int updateByPrimaryKeyWithBLOBs(UcenterDatasyncLog record);

    int updateByPrimaryKey(UcenterDatasyncLog record);
}