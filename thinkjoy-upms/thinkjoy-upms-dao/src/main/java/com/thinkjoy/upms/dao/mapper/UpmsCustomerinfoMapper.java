package com.thinkjoy.upms.dao.mapper;

import com.thinkjoy.upms.dao.model.UpmsCustomerinfo;
import com.thinkjoy.upms.dao.model.UpmsCustomerinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpmsCustomerinfoMapper {
    long countByExample(UpmsCustomerinfoExample example);

    int deleteByExample(UpmsCustomerinfoExample example);

    int deleteByPrimaryKey(Integer customerId);

    int insert(UpmsCustomerinfo record);

    int insertSelective(UpmsCustomerinfo record);

    List<UpmsCustomerinfo> selectByExample(UpmsCustomerinfoExample example);

    UpmsCustomerinfo selectByPrimaryKey(Integer customerId);

    int updateByExampleSelective(@Param("record") UpmsCustomerinfo record, @Param("example") UpmsCustomerinfoExample example);

    int updateByExample(@Param("record") UpmsCustomerinfo record, @Param("example") UpmsCustomerinfoExample example);

    int updateByPrimaryKeySelective(UpmsCustomerinfo record);

    int updateByPrimaryKey(UpmsCustomerinfo record);
}