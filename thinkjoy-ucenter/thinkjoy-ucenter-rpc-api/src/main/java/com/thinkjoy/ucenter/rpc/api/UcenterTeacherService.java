package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterTeacherDto;

import java.util.List;
import java.util.Map;

/**
 * UcenterTeacherService接口
 *
 * @author user
 * @date 2017/10/13
 */
public interface UcenterTeacherService extends BaseService<UcenterTeacher, UcenterTeacherExample> {

    /**
     * 添加老师
     *
     * @param ucenterTeacher 老师实体
     * @param schoolCode     学校code
     * @param specialtyCodes 老师所带的专业
     * @param classIds       老师所带的班级
     * @return
     */
    int insertTeacher(UcenterTeacher ucenterTeacher,
                      String schoolCode,
                      String specialtyCodes,
                      String classIds,String depIds) throws Exception;

    /**
     * 更新老师
     *
     * @param ucenterTeacher
     * @param specialtyCodes
     * @param classIds
     * @return
     * @throws Exception
     */
    int updateTeacher(UcenterTeacher ucenterTeacher,
                      String specialtyCodes,
                      String classIds,String depIds) throws Exception;


    /**
     * 删除老师
     * @return
     * @throws Exception
     */
    int deleteTeacher(String ids) throws Exception;

    /**
     * 导入老师
     * @param ucenterTeacher
     * @param ucenterSchool
     * @param errorMap
     * @param tableValid
     * @param rowNum
     * @return
     */
    Map<String, String> importUcenterTeacher(UcenterTeacher ucenterTeacher,List<UcenterClass> ucenterClasss,List<UcenterDepartment> deptList,UcenterSchool ucenterSchool,Map<String, String> errorMap,Table tableValid,int rowNum,long errorNum);

    /**
     * 根据职务，查老师信息
     * @param map
     * @return
     */
    List<UcenterTeacher> selectUcenterTeacherByPostId(Map<String, Object> map);


    /**
     * 获取用户权限范围列表
     * @param map
     * @return
     */
    List<UcenterTeacherDto> selectDataScopeUcenterTeacherInfoByAppIdAndRelationCode(Map<String, Object> map);

}