package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UcenterAreaMapper {
    long countByExample(UcenterAreaExample example);

    int deleteByExample(UcenterAreaExample example);

    int deleteByPrimaryKey(Integer areaId);

    int insert(UcenterArea record);

    int insertSelective(UcenterArea record);

    List<UcenterArea> selectByExample(UcenterAreaExample example);

    UcenterArea selectByPrimaryKey(Integer areaId);

    int updateByExampleSelective(@Param("record") UcenterArea record, @Param("example") UcenterAreaExample example);

    int updateByExample(@Param("record") UcenterArea record, @Param("example") UcenterAreaExample example);

    int updateByPrimaryKeySelective(UcenterArea record);

    int updateByPrimaryKey(UcenterArea record);

    List<UcenterArea> getAreaTree();

	List<UcenterArea> getschoolTree();
}