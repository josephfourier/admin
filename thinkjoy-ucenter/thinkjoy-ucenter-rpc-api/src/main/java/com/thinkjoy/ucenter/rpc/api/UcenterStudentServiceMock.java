package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.mapper.UcenterStudentMapper;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterStudentExample;

import java.util.Map;

/**
* 降级实现UcenterStudentService接口
* Created by user on 2017/9/20.
*/
public class UcenterStudentServiceMock extends BaseServiceMock<UcenterStudentMapper, UcenterStudent, UcenterStudentExample> implements UcenterStudentService {


    @Override
    public int insertStudent(UcenterStudent ucenterStudent, String schoolCode) throws Exception {
        return 0;
    }

    @Override
    public int updateStudent(UcenterStudent ucenterStudent) throws Exception {
        return 0;
    }

    @Override
    public int deleteStudent(String ids) throws Exception {
        return 0;
    }

    @Override
    public Map<String, String> importUcenterStudent(UcenterStudent ucenterStudent, UcenterSchool ucenterSchool, Map<String, String> errorMap, Table tableValid, int rowNum, long errorNum) {
        return null;
    }

    @Override
    public void syszjyaccount(UcenterStudent ucenterStudent) {

    }


}
