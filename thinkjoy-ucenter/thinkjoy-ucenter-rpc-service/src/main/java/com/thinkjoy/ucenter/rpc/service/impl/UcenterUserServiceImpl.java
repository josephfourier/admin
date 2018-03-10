package com.thinkjoy.ucenter.rpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.UUIDTool;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.mapper.*;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserAccountDto;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserInfoDto;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import com.thinkjoy.ucenter.rpc.exception.DuplicatePropertyException;
import com.thinkjoy.ucenter.rpc.exception.UcenterBusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UcenterUserService实现
 * Created by xufei on 2017/7/26.
 */
@Service
@Transactional
@BaseService
public class UcenterUserServiceImpl extends BaseServiceImpl<UcenterUserMapper, UcenterUser, UcenterUserExample> implements UcenterUserService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserServiceImpl.class);

    @Autowired
    UcenterUserMapper ucenterUserMapper;

    @Autowired
    UcenterSchoolMapper ucenterSchoolMapper;

    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;

    @Autowired
    UcenterTeacherSpecialtyMapper ucenterTeacherSpecialtyMapper;

    @Autowired
    UcenterTeacherClassMapper ucenterTeacherClassMapper;

    @Override
    public List<UcenterUser> selectByIdentityRelationCodeForOffsetPage(Map<String, Object> map) {
        PageHelper.offsetPage(Integer.parseInt(map.get("offset").toString()), Integer.parseInt(map.get("limit").toString()), false);
        return ucenterUserMapper.selectByIdentityRelationCodeForOffsetPage(map);
    }

    @Override
    public long countByIdentityRelationCodeForOffsetPage(Map<String, Object> map) {
        return ucenterUserMapper.countByIdentityRelationCodeForOffsetPage(map);
    }

    @Override
    public UcenterUser createUserBeforeCreateStudent(UcenterStudent ucenterStudent, String schoolCode) {

        UcenterUser ucenterUser = null;
        try {
            ucenterUser = new UcenterUser();
            // 默认用身份证号作为登录名
            ucenterUser.setUsername(ucenterStudent.getPhone());
            ucenterUser.setUid(UUIDTool.getUUID());
            //身份证后六位
            ucenterUser.setPassword(AESUtil.AESEncode(
                    ucenterStudent.getIdcard().substring(
                            ucenterStudent.getIdcard().length() - 6, ucenterStudent.getIdcard().length())));
            ucenterUser.setEmail(ucenterStudent.getMail());
            ucenterUser.setStudentCode(ucenterStudent.getStudentCode());
            //为迎新的需求,不为空才更新
            if (StringUtils.isNotBlank(ucenterStudent.getExamineeNumber())) {
                ucenterUser.setExamineeNumber(ucenterStudent.getExamineeNumber());
            }
            ucenterUser.setPhone(ucenterStudent.getPhone());
            ucenterUser.setIdcard(ucenterStudent.getIdcard());
            ucenterUser.setSex(ucenterStudent.getSex());
            ucenterUser.setCreator(ucenterStudent.getCreator());
            ucenterUser.setCtime(System.currentTimeMillis());
            ucenterUser.setFullname(ucenterStudent.getStudentName());
            ucenterUser.setSalt("");
            ucenterUser.setStatus(ucenterStudent.getStatus());
            ucenterUser.setYear(ucenterStudent.getYear());
            ucenterUser.setSchoolCode(ucenterStudent.getSchoolCode());
            int count = ucenterUserMapper.insertSelective(ucenterUser);

            //保存用户身份
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.STUDENT);
            ucenterUserIdentity.setRelationCode(schoolCode);

            UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
            ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(schoolCode);
            List<UcenterSchool> ucenterSchools = ucenterSchoolMapper.selectByExample(ucenterSchoolExample);

            if (CollectionUtils.isNotEmpty(ucenterSchools)) {
                ucenterUserIdentity.setRelationName(ucenterSchools.get(0).getSchoolName());
            }
            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new DuplicatePropertyException("手机号重复");
            } else if (e instanceof IndexOutOfBoundsException) {
                throw new UcenterBusinessException("身份证格式不正确!");
            } else {
                throw e;
            }
        }

        return ucenterUser;
    }

    @Override
    public UcenterUser createUserBeforeCreateTeacher(UcenterTeacher ucenterTeacher, String schoolCode) {
        UcenterUser ucenterUser = null;
        try {
            ucenterUser = new UcenterUser();
            //默认用手机号作为登录名
            ucenterUser.setUsername(ucenterTeacher.getPhone());
            ucenterUser.setUid(UUIDTool.getUUID());
            //身份证后六位
            ucenterUser.setPassword(AESUtil.AESEncode(
                    ucenterTeacher.getIdcard().substring(
                            ucenterTeacher.getIdcard().length() - 6, ucenterTeacher.getIdcard().length())));
            ucenterUser.setEmail(ucenterTeacher.getMail());
            ucenterUser.setPhone(ucenterTeacher.getPhone());
            ucenterUser.setIdcard(ucenterTeacher.getIdcard());
            ucenterUser.setSex(ucenterTeacher.getSex());
            ucenterUser.setCreator(ucenterTeacher.getCreator());
            ucenterUser.setCtime(System.currentTimeMillis());
            ucenterUser.setFullname(ucenterTeacher.getTeacherName());
            ucenterUser.setSalt("");
            ucenterUser.setStatus(ucenterTeacher.getStatus());
            ucenterUser.setSchoolCode(ucenterTeacher.getSchoolCode());
            int count = ucenterUserMapper.insertSelective(ucenterUser);


            // 保存用户身份
            // 应用权限个性化默认为非个性化
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.TEACHER);
            ucenterUserIdentity.setRelationCode(schoolCode);

            UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
            ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(schoolCode);
            List<UcenterSchool> ucenterSchools = ucenterSchoolMapper.selectByExample(ucenterSchoolExample);

            if (CollectionUtils.isNotEmpty(ucenterSchools)) {
                ucenterUserIdentity.setRelationName(ucenterSchools.get(0).getSchoolName());
            }
            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new DuplicatePropertyException("手机号重复");
            } else if (e instanceof IndexOutOfBoundsException) {
                throw new UcenterBusinessException("身份证格式不正确!");
            } else {
                throw e;
            }
        }
        return ucenterUser;
    }

    @Override
    public int createTeacherSpecialtyAndClass(Integer teacherId, String specialtyCodes, String classIds) {
        String[] sp ={};
        String[] sc ={};
        if(StringUtils.isNotBlank(specialtyCodes)){
            sp=specialtyCodes.split(",");
            if (sp != null && sp.length >= 1) {
                for (String s : sp) {
                    UcenterTeacherSpecialty ucenterTeacherSpecialty = new UcenterTeacherSpecialty();
                    ucenterTeacherSpecialty.setTeacherId(teacherId);
                    ucenterTeacherSpecialty.setSpecialtyCode(s);
                    ucenterTeacherSpecialtyMapper.insertSelective(ucenterTeacherSpecialty);
                }
            }
        }

        if(StringUtils.isNotBlank(classIds)){
            sc = classIds.split(",");
            if (sc != null && sc.length >= 1) {
                for (String s : sc) {
                    UcenterTeacherClass ucenterTeacherClass = new UcenterTeacherClass();
                    ucenterTeacherClass.setTeacherId(teacherId);
                    ucenterTeacherClass.setClassId(Integer.parseInt(s));
                    ucenterTeacherClassMapper.insertSelective(ucenterTeacherClass);
                }
            }
        }

        return sp.length + sc.length;
    }

//    @Override
//    public int createTeacherSpecialtyAndClass(Integer teacherId, String specialtyCodes, String classIds) {
//
//        if (StringUtils.isBlank(specialtyCodes) || StringUtils.isBlank(classIds)) {
//            return 0;
//        }
//
//        String[] sp = specialtyCodes.split(",");
//        String[] sc = classIds.split(",");
//
//        if (sp != null && sp.length >= 1) {
//            for (String s : sp) {
//                UcenterTeacherSpecialty ucenterTeacherSpecialty = new UcenterTeacherSpecialty();
//                ucenterTeacherSpecialty.setTeacherId(teacherId);
//                ucenterTeacherSpecialty.setSpecialtyCode(s);
//                ucenterTeacherSpecialtyMapper.insertSelective(ucenterTeacherSpecialty);
//            }
//        }
//
//        if (sc != null && sc.length >= 1) {
//            for (String s : sc) {
//                UcenterTeacherClass ucenterTeacherClass = new UcenterTeacherClass();
//                ucenterTeacherClass.setTeacherId(teacherId);
//                ucenterTeacherClass.setClassId(Integer.parseInt(s));
//                ucenterTeacherClassMapper.insertSelective(ucenterTeacherClass);
//            }
//        }
//
//        return sp.length + sc.length;
//    }

    @Override
    public UserInfoDto getUserInfo(Map<String, Object> map) {
        return ucenterUserMapper.getUserInfo(map);
    }

    @Override
    public UserAccountDto getUserAccount(Map<String, Object> map) {
        return ucenterUserMapper.getUserAccount(map);
    }

    @Override
    public UcenterUser selectuserbeanMap(String username) {
        Map map = new HashMap<String, Object>();
        map.put("username", username);
        UcenterUser ucenterUserbean = ucenterUserMapper.selectuserbeanMap(map);
        return ucenterUserbean;
    }

}