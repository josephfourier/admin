package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBus;
import com.thinkjoy.ucenter.dao.model.UcenterDepartmentBusExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.BusInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterDepartmentBusMapper {
    long countByExample(UcenterDepartmentBusExample example);

    int deleteByExample(UcenterDepartmentBusExample example);

    int deleteByPrimaryKey(Integer departmentbusId);

    int insert(UcenterDepartmentBus record);

    int insertSelective(UcenterDepartmentBus record);

    List<UcenterDepartmentBus> selectByExample(UcenterDepartmentBusExample example);

    UcenterDepartmentBus selectByPrimaryKey(Integer departmentbusId);

    int updateByExampleSelective(@Param("record") UcenterDepartmentBus record, @Param("example") UcenterDepartmentBusExample example);

    int updateByExample(@Param("record") UcenterDepartmentBus record, @Param("example") UcenterDepartmentBusExample example);

    int updateByPrimaryKeySelective(UcenterDepartmentBus record);

    int updateByPrimaryKey(UcenterDepartmentBus record);

    List<BusInfo> selectBusInfoByBusIdAndTeacherLevel(Map<String, String> map);
}