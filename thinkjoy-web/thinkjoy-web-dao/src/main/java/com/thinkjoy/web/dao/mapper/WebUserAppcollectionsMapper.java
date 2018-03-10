package com.thinkjoy.web.dao.mapper;

import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.dao.model.WebUserAppcollectionsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WebUserAppcollectionsMapper {
    long countByExample(WebUserAppcollectionsExample example);

    int deleteByExample(WebUserAppcollectionsExample example);

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("usertypeId") Integer usertypeId, @Param("relationCode") String relationCode, @Param("appId") Integer appId);

    int insert(WebUserAppcollections record);

    int insertSelective(WebUserAppcollections record);

    List<WebUserAppcollections> selectByExample(WebUserAppcollectionsExample example);

    int updateByExampleSelective(@Param("record") WebUserAppcollections record, @Param("example") WebUserAppcollectionsExample example);

    int updateByExample(@Param("record") WebUserAppcollections record, @Param("example") WebUserAppcollectionsExample example);

    int batchDeleteByPrimarykey(Map<String, String> map);

    int batchInsert(List list);

}