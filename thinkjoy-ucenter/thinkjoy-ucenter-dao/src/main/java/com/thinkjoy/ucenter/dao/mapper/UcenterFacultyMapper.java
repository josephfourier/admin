package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterFaculty;
import com.thinkjoy.ucenter.dao.model.UcenterFacultyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterFacultyMapper {
    long countByExample(UcenterFacultyExample example);

    int deleteByExample(UcenterFacultyExample example);

    int deleteByPrimaryKey(Integer facultyId);

    int insert(UcenterFaculty record);

    int insertSelective(UcenterFaculty record);

    List<UcenterFaculty> selectByExample(UcenterFacultyExample example);

    UcenterFaculty selectByPrimaryKey(Integer facultyId);

    int updateByExampleSelective(@Param("record") UcenterFaculty record, @Param("example") UcenterFacultyExample example);

    int updateByExample(@Param("record") UcenterFaculty record, @Param("example") UcenterFacultyExample example);

    int updateByPrimaryKeySelective(UcenterFaculty record);

    int updateByPrimaryKey(UcenterFaculty record);

	String selectMaxCodeByMap(Map map);

	UcenterFaculty selectfacultybeanMap(Map map);
}