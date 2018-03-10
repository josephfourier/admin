package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherMapper;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterTeacherDto;

import java.util.List;
import java.util.Map;

/**
* 降级实现UcenterTeacherService接口
* Created by user on 2017/10/13.
*/
public class UcenterTeacherServiceMock extends BaseServiceMock<UcenterTeacherMapper, UcenterTeacher, UcenterTeacherExample> implements UcenterTeacherService {


    @Override
    public int insertTeacher(UcenterTeacher ucenterTeacher, String schoolCode, String specialtyCodes, String classIds, String depIds) throws Exception {
        return 0;
    }

    @Override
    public int updateTeacher(UcenterTeacher ucenterTeacher, String specialtyCodes, String classIds, String depIds) throws Exception {
        return 0;
    }

    @Override
    public int deleteTeacher(String ids) throws Exception {
        return 0;
    }

    @Override
    public Map<String, String> importUcenterTeacher(UcenterTeacher ucenterTeacher, List<UcenterClass> ucenterClasss, List<UcenterDepartment> deptList, UcenterSchool ucenterSchool, Map<String, String> errorMap, Table tableValid, int rowNum, long errorNum) {
        return null;
    }

    @Override
    public List<UcenterTeacher> selectUcenterTeacherByPostId(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<UcenterTeacherDto> selectDataScopeUcenterTeacherInfoByAppIdAndRelationCode(Map<String, Object> map) {
        return null;
    }
}
