package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterPost;
import com.thinkjoy.ucenter.dao.model.UcenterPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterPostMapper {
    long countByExample(UcenterPostExample example);

    int deleteByExample(UcenterPostExample example);

    int deleteByPrimaryKey(Integer postId);

    int insert(UcenterPost record);

    int insertSelective(UcenterPost record);

    List<UcenterPost> selectByExample(UcenterPostExample example);

    UcenterPost selectByPrimaryKey(Integer postId);

    int updateByExampleSelective(@Param("record") UcenterPost record, @Param("example") UcenterPostExample example);

    int updateByExample(@Param("record") UcenterPost record, @Param("example") UcenterPostExample example);

    int updateByPrimaryKeySelective(UcenterPost record);

    int updateByPrimaryKey(UcenterPost record);
}