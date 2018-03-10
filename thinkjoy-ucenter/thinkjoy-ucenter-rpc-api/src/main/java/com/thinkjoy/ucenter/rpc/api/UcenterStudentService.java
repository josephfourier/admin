package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterStudentExample;
import com.thinkjoy.ucenter.dao.model.UcenterTeacher;

import java.util.Map;

/**
* UcenterStudentService接口
* Created by user on 2017/9/20.
*/
public interface UcenterStudentService extends BaseService<UcenterStudent, UcenterStudentExample> {
    /**
     * 添加学生
     *
     * @param ucenterStudent 学生实体
     * @return
     */
    int insertStudent(UcenterStudent ucenterStudent, String schoolCode) throws Exception;

    /**
     * 更新学生
     * @param ucenterStudent
     * @return
     * @throws Exception
     */
    int updateStudent(UcenterStudent ucenterStudent) throws Exception;


    /**
     * 删除学生
     * @return
     * @throws Exception
     */
    int deleteStudent(String ids) throws Exception;

    /**
     * 导入学生
     *
     * @param ucenterStudent 学生实体
     * @return
     */
    Map<String, String> importUcenterStudent(UcenterStudent ucenterStudent,UcenterSchool ucenterSchool,Map<String, String> errorMap,Table tableValid,int rowNum,long errorNum);

    /**
     * 招生系统生成职教云账号接口
     *
     * @param
     * @return
     */
    void syszjyaccount(UcenterStudent ucenterStudent);
}