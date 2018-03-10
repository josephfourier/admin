package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterSchoolyear;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolyearExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterSchoolyearMapper {
    long countByExample(UcenterSchoolyearExample example);

    int deleteByExample(UcenterSchoolyearExample example);

    int deleteByPrimaryKey(Integer yearId);

    int insert(UcenterSchoolyear record);

    int insertSelective(UcenterSchoolyear record);

    List<UcenterSchoolyear> selectByExample(UcenterSchoolyearExample example);

    UcenterSchoolyear selectByPrimaryKey(Integer yearId);

    int updateByExampleSelective(@Param("record") UcenterSchoolyear record, @Param("example") UcenterSchoolyearExample example);

    int updateByExample(@Param("record") UcenterSchoolyear record, @Param("example") UcenterSchoolyearExample example);

    int updateByPrimaryKeySelective(UcenterSchoolyear record);

    int updateByPrimaryKey(UcenterSchoolyear record);
}