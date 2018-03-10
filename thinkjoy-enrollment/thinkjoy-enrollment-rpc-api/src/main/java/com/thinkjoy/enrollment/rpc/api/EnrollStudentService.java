package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.enrollment.dao.model.EnrollFamily;
import com.thinkjoy.enrollment.dao.model.EnrollSpecialtychangeLog;
import com.thinkjoy.enrollment.dao.model.EnrollStudent;
import com.thinkjoy.enrollment.dao.model.EnrollStudentExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.EnrollStudentDto;

import java.util.List;
import java.util.Map;

/**
 * EnrollStudentService接口
 * Created by user on 2017/11/2.
 */
public interface EnrollStudentService extends BaseService<EnrollStudent, EnrollStudentExample> {
    int updateByPrimaryKeySelective(EnrollStudent enrollStudent, EnrollSpecialtychangeLog spechangelog);

    int luquAndcreateNotice(EnrollStudent enrollStudent);

    int yluquAndcreateNotice(EnrollStudent enrollStudent);

    int insertSelective(EnrollStudent enrollStudent, EnrollFamily enrollFamily);

    List<EnrollStudentDto> selectfacultyMap(String schoolCode, String batchYear, String enrollStatus);

    List<EnrollStudentDto> selectspecialtyMap(String schoolCode, String batchYear, String facultyCode, String enrollStatus);

    List<EnrollStudentDto> selectteacherMap(String schoolCode, String batchYear, String batchId, String enrollStatus);

    List<EnrollStudentDto> selectfromplaceMap(String schoolCode, String batchYear, String enrollStatus);

    List<EnrollStudentDto> selectMbatchMap(String schoolCode, String batchYear, String sex);

    EnrollStudentDto selectWbatchMap(String schoolCode, String batchYear, Integer batchId, String sex, String enrollStatus);

    int deleteStudent(String ids);

    /**
     * 导入招生学生
     * @param enrollStudent
     * @param errorMap
     * @param tableValid
     * @param rowNum
     * @param errorNum
     * @return
     */
    Map<String, String> importEnrollStudent(EnrollStudent enrollStudent, Map<String, String> errorMap, Table tableValid, int rowNum, long errorNum);

}