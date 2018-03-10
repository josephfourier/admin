package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterClass;
import com.thinkjoy.ucenter.dao.model.UcenterClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterClassMapper {
    long countByExample(UcenterClassExample example);

    int deleteByExample(UcenterClassExample example);

    int deleteByPrimaryKey(Integer classId);

    int insert(UcenterClass record);

    int insertSelective(UcenterClass record);

    List<UcenterClass> selectByExample(UcenterClassExample example);

    UcenterClass selectByPrimaryKey(Integer classId);

    int updateByExampleSelective(@Param("record") UcenterClass record, @Param("example") UcenterClassExample example);

    int updateByExample(@Param("record") UcenterClass record, @Param("example") UcenterClassExample example);

    int updateByPrimaryKeySelective(UcenterClass record);

    int updateByPrimaryKey(UcenterClass record);
}