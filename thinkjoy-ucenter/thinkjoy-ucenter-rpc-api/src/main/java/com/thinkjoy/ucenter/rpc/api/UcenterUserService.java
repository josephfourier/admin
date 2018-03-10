package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterTeacher;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserAccountDto;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserInfoDto;

import java.util.List;
import java.util.Map;

/**
 * UcenterUserService接口
 * Created by xufei on 2017/7/26.
 */
public interface UcenterUserService extends BaseService<UcenterUser, UcenterUserExample> {
    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    List<UcenterUser> selectByIdentityRelationCodeForOffsetPage(Map<String, Object> map);

    /**
     * 计数
     *
     * @param map
     * @return
     */
    long countByIdentityRelationCodeForOffsetPage(Map<String, Object> map);

    /**
     * 新增学生之前先给其添加身份以及账户
     *
     * @param ucenterStudent
     * @param schoolCode
     * @return
     */
    UcenterUser createUserBeforeCreateStudent(UcenterStudent ucenterStudent, String schoolCode);

    /**
     * 新增老师之前先给其添加身份以及账户
     *
     * @param ucenterTeacher
     * @param schoolCode
     * @return
     */
    UcenterUser createUserBeforeCreateTeacher(UcenterTeacher ucenterTeacher, String schoolCode);

    /**
     * 新增老师之前先关联其与专业和班级的关系
     *
     * @param specialtyCodes
     * @param classIds
     * @param teacherId
     * @return
     */
    int createTeacherSpecialtyAndClass(Integer teacherId, String specialtyCodes, String classIds);

    /**
     * 获取用户账号信息
     *
     * @param map
     * @return
     */
    UserInfoDto getUserInfo(Map<String, Object> map);

    /**
     * 获取用户账号密码信息
     *
     * @param map
     * @return
     */
    UserAccountDto getUserAccount(Map<String, Object> map);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    UcenterUser selectuserbeanMap(String username);
}