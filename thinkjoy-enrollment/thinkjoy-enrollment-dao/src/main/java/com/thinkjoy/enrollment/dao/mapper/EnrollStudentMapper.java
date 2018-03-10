package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollStudent;
import com.thinkjoy.enrollment.dao.model.EnrollStudentExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.EnrollStudentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnrollStudentMapper {
    long countByExample(EnrollStudentExample example);

    int deleteByExample(EnrollStudentExample example);

    int deleteByPrimaryKey(Integer studentId);

    int insert(EnrollStudent record);

    int insertSelective(EnrollStudent record);

    List<EnrollStudent> selectByExample(EnrollStudentExample example);

    EnrollStudent selectByPrimaryKey(Integer studentId);

    int updateByExampleSelective(@Param("record") EnrollStudent record, @Param("example") EnrollStudentExample example);

    int updateByExample(@Param("record") EnrollStudent record, @Param("example") EnrollStudentExample example);

    int updateByPrimaryKeySelective(EnrollStudent record);

    int updateByPrimaryKey(EnrollStudent record);

	List<EnrollStudentDto> selectfacultyMap(Map map);
	List<EnrollStudentDto> selectspecialtyMap(Map map);
	List<EnrollStudentDto> selectteacherMap(Map map);
	List<EnrollStudentDto> selectfromplaceMap(Map map);
	List<EnrollStudentDto> selectMbatchMap(Map map);
	EnrollStudentDto selectWbatchMap(Map map);


    int updateNoticePathByPrimary(EnrollStudent record);
}