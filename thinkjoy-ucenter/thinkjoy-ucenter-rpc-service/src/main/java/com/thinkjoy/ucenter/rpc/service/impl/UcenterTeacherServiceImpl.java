package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.DateUtil;
import com.thinkjoy.common.util.StringUtil;
import com.thinkjoy.common.util.UUIDTool;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.ucenter.dao.mapper.*;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UcenterTeacherDto;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import com.thinkjoy.ucenter.rpc.exception.UcenterBusinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UcenterTeacherService实现
 * Created by user on 2017/10/13.
 */
@Service
@Transactional
@BaseService
public class UcenterTeacherServiceImpl extends BaseServiceImpl<UcenterTeacherMapper, UcenterTeacher, UcenterTeacherExample> implements UcenterTeacherService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTeacherServiceImpl.class);

    @Autowired
    UcenterTeacherMapper ucenterTeacherMapper;

    @Autowired
    UcenterUserService ucenterUserService;

    @Autowired
    UcenterTeacherSpecialtyMapper ucenterTeacherSpecialtyMapper;

    @Autowired
    UcenterTeacherClassMapper ucenterTeacherClassMapper;

    @Autowired
    UcenterUserMapper ucenterUserMapper;

    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;

    @Autowired
    UcenterTeacherPostMapper  ucenterTeacherPostMapper;

    @Override
    public int insertTeacher(UcenterTeacher ucenterTeacher, String schoolCode, String specialtyCodes, String classIds,String  depIds) throws Exception {
        UcenterUser userBeforeCreateTeacher = ucenterUserService.createUserBeforeCreateTeacher(ucenterTeacher, schoolCode);

        if (userBeforeCreateTeacher != null) {
            ucenterTeacher.setUserId(userBeforeCreateTeacher.getUserId());
        } else {
            throw new UcenterBusinessException("添加老师账号以及身份失败!");
        }

        ucenterTeacherMapper.insertSelective(ucenterTeacher);

        if (ucenterTeacher.getTeacherId() == null) {
            throw new UcenterBusinessException("添加老师时没有返回主键!");
        }

        int count = ucenterUserService.createTeacherSpecialtyAndClass(ucenterTeacher.getTeacherId(), specialtyCodes, classIds);


        String dep[]=depIds.split(",");
        if(dep.length>0){
            //先清除之前的部门关系,再次保存
            UcenterTeacherPostExample ucenterTeacherPostExampleDel=new UcenterTeacherPostExample();
            ucenterTeacherPostExampleDel.createCriteria().andTeacherIdEqualTo(ucenterTeacher.getTeacherId());
            ucenterTeacherPostMapper.deleteByExample(ucenterTeacherPostExampleDel);
        for(int i=0;i<dep.length;i++){
            UcenterTeacherPost ucenterTeacherPost=new UcenterTeacherPost();
            ucenterTeacherPost.setDepartmentId(Integer.parseInt(dep[i].toString()));
            ucenterTeacherPost.setTeacherId(ucenterTeacher.getTeacherId());
            int relationcount=ucenterTeacherPostMapper.insert(ucenterTeacherPost);
        }
        }

        return count;
    }

    @Override
    public int updateTeacher(UcenterTeacher ucenterTeacher, String specialtyCodes, String classIds,String depIds) throws Exception {

        int count1 = 0;
        int count = 0;
        try {
            UcenterTeacherSpecialtyExample ucenterTeacherSpecialtyExample = new UcenterTeacherSpecialtyExample();
            ucenterTeacherSpecialtyExample.createCriteria().andTeacherIdEqualTo(ucenterTeacher.getTeacherId());
            ucenterTeacherSpecialtyMapper.deleteByExample(ucenterTeacherSpecialtyExample);

            UcenterTeacherClassExample ucenterTeacherClassExample = new UcenterTeacherClassExample();
            ucenterTeacherClassExample.createCriteria().andTeacherIdEqualTo(ucenterTeacher.getTeacherId());
            ucenterTeacherClassMapper.deleteByExample(ucenterTeacherClassExample);

            ucenterUserService.createTeacherSpecialtyAndClass(ucenterTeacher.getTeacherId(), specialtyCodes, classIds);

            //更新老师账户信息
            UcenterUser ucenterUser = ucenterUserMapper.selectByPrimaryKey(ucenterTeacher.getUserId());
            if (ucenterUser == null) {
                throw new UcenterBusinessException("无账户信息");
            }
            ucenterUser.setEmail(ucenterTeacher.getMail());
            ucenterUser.setPhone(ucenterTeacher.getPhone());
            ucenterUser.setIdcard(ucenterTeacher.getIdcard());
            ucenterUser.setSex(ucenterTeacher.getSex());
            ucenterUser.setFullname(ucenterTeacher.getTeacherName());
            ucenterUser.setSalt("");
            ucenterUser.setStatus(ucenterTeacher.getStatus());
            count1 = ucenterUserMapper.updateByPrimaryKey(ucenterUser);
            count = ucenterTeacherMapper.updateByPrimaryKeySelective(ucenterTeacher);

            //更新部门信息
            String dep[]=depIds.split(",");
            if(dep.length>0){
                //先清除之前的部门关系,再次保存
                UcenterTeacherPostExample ucenterTeacherPostExampleDel=new UcenterTeacherPostExample();
                ucenterTeacherPostExampleDel.createCriteria().andTeacherIdEqualTo(ucenterTeacher.getTeacherId());
                ucenterTeacherPostMapper.deleteByExample(ucenterTeacherPostExampleDel);
                for(int i=0;i<dep.length;i++){
                    UcenterTeacherPost ucenterTeacherPost=new UcenterTeacherPost();
                    ucenterTeacherPost.setDepartmentId(Integer.parseInt(dep[i].toString()));
                    ucenterTeacherPost.setTeacherId(ucenterTeacher.getTeacherId());
                    int relationcount=ucenterTeacherPostMapper.insert(ucenterTeacherPost);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new UcenterBusinessException("更新老师失败!");
        }
        return count;
    }

    @Override
    public int deleteTeacher(String ids) throws Exception {

        int i = 0;
        try {
            // 删除老师时需要同时删除老师的账户信息
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            String[] idArray = ids.split("-");
            ArrayList<Integer> idvalues = new ArrayList<>();
            ArrayList<Integer> uidvalues = new ArrayList<>();
            if (idArray != null && idArray.length > 0) {
                for (String i2 : idArray) {
                    int i1 = Integer.parseInt(i2);
                    UcenterTeacher ucenterTeacher = ucenterTeacherMapper.selectByPrimaryKey(i1);
                    uidvalues.add(ucenterTeacher.getUserId());
                    idvalues.add(i1);
                }
            }


            //删除人员部门关系
            UcenterTeacherPostExample ucenterTeacherPostExampleDel=new UcenterTeacherPostExample();
            ucenterTeacherPostExampleDel.createCriteria().andTeacherIdIn(idvalues);
            ucenterTeacherPostMapper.deleteByExample(ucenterTeacherPostExampleDel);

            //删除老师
            UcenterTeacherExample ucenterTeacherExample = new UcenterTeacherExample();
            ucenterTeacherExample.createCriteria().andTeacherIdIn(idvalues);
            int i1 = ucenterTeacherMapper.deleteByExample(ucenterTeacherExample);

            //删除身份
            UcenterUserIdentityExample ucenterUserIdentityExample = new UcenterUserIdentityExample();
            ucenterUserIdentityExample.createCriteria().andUserIdIn(uidvalues);
            ucenterUserIdentityMapper.deleteByExample(ucenterUserIdentityExample);

            //删除账号
            UcenterUserExample ucenterUserExample = new UcenterUserExample();
            ucenterUserExample.createCriteria().andUserIdIn(uidvalues);
            i = ucenterUserMapper.deleteByExample(ucenterUserExample);



        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {

                    String res = handleException(e);
                    throw new RuntimeException(res);
                }
            }
        }
        return i;
    }

    @Override
    public Map<String, String> importUcenterTeacher(UcenterTeacher ucenterTeacher, List<UcenterClass> ucenterClasss, List<UcenterDepartment> deptList,UcenterSchool ucenterSchool, Map<String, String> errorMap, Table tableValid, int rowNum,long errorNum) {
        if (errorMap==null){
            errorMap=new HashMap<>();
        }
        int year= DateUtil.getCurrentYear();

        //验证老师信息是否重复 身份证号,电话号
        UcenterTeacherExample ucenterTeacherExample=new UcenterTeacherExample();
        ucenterTeacherExample.or().andIdcardEqualTo(ucenterTeacher.getIdcard());
        ucenterTeacherExample.or().andPhoneEqualTo(ucenterTeacher.getPhone());
        List<UcenterTeacher> teachers=selectByExample(ucenterTeacherExample);
        if(teachers!=null&&teachers.size()>0){
            if(teachers.size()==1){
                if(teachers.get(0).getPhone().equals(ucenterTeacher.getPhone())) {
                    errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("phone")), "手机号码已存在");
                }
                if(teachers.get(0).getIdcard().equals(ucenterTeacher.getIdcard())){
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("idcard")), "身份证号已存在");
                }
            }else{
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "电话号码，身份证号出现重复");
            }
        }

        //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
        if(errorMap!=null&&errorMap.size()>errorNum){
            return errorMap;
        }


        //新增学生前先给学生新增账户
        UcenterUserExample ucenterUserExample=new UcenterUserExample();
        ucenterUserExample.or().andIdcardEqualTo(ucenterTeacher.getIdcard());
        ucenterUserExample.or().andUsernameEqualTo(ucenterTeacher.getPhone());
        List<UcenterUser> ucenterUsers=ucenterUserService.selectByExample(ucenterUserExample);

        //通过一身份证号，电话号码确认是不是同一个用户
        UcenterUser ucenterUser=null;
        if(ucenterUsers==null||ucenterUsers.size()==0){
            try {
                ucenterUser = new UcenterUser();
                // 默认用身份证号作为登录名
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
                ucenterUser.setCtime(ucenterTeacher.getCtime());
                ucenterUser.setFullname(ucenterTeacher.getTeacherName());
                ucenterUser.setSalt("");
                ucenterUser.setStatus(ucenterTeacher.getStatus());
                ucenterUser.setYear(year);
                ucenterUser.setSchoolCode(ucenterTeacher.getSchoolCode());
                int count = ucenterUserMapper.insertSelective(ucenterUser);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof DuplicateKeyException) {
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "手机号对应账号已存在");
                }else{
                    throw e;
                }
                return errorMap;
            }

            //保存用户身份
            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.TEACHER);
            ucenterUserIdentity.setRelationCode(ucenterSchool.getSchoolCode());
            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
        }else{
            if(ucenterUsers.size()==1){
                ucenterUser=ucenterUsers.get(0);
                if(ucenterUser.getUsername().equals(ucenterTeacher.getPhone())&&ucenterUser.getIdcard().equals(ucenterTeacher.getIdcard())){
                    UcenterUserExample updateUcenterUserExample=new UcenterUserExample();
                    updateUcenterUserExample.createCriteria().andIdcardEqualTo(ucenterTeacher.getIdcard()).andUsernameEqualTo(ucenterTeacher.getPhone());
                    //需要更新的字段
                    UcenterUser ucenterUser2 = new UcenterUser();

                    // 默认用电话号码作为登录名
                    ucenterUser2.setEmail(ucenterTeacher.getMail());

                    ucenterUser2.setSex(ucenterTeacher.getSex());
                    ucenterUser2.setCreator(ucenterTeacher.getCreator());
                    ucenterUser2.setCtime(ucenterTeacher.getCtime());
                    ucenterUser2.setFullname(ucenterTeacher.getTeacherName());
                    ucenterUser2.setStatus(ucenterTeacher.getStatus());
                    ucenterUser2.setYear(year);
                    ucenterUser2.setSchoolCode(ucenterTeacher.getSchoolCode());
                    ucenterUserMapper.updateByExampleSelective(ucenterUser2,updateUcenterUserExample);

                    UcenterUserIdentityExample ucenterUserIdentityExample=new UcenterUserIdentityExample();
                    ucenterUserIdentityExample.createCriteria().andUserIdEqualTo(ucenterUser.getUserId()).andUsertypeIdEqualTo(BaseConstants.UserType.TEACHER).andRelationCodeEqualTo(ucenterSchool.getSchoolCode());
                    long count=ucenterUserIdentityMapper.countByExample(ucenterUserIdentityExample);
                    if(count<=0){//如果没有身份数据，新增
                        //保存用户身份
                        UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
                        ucenterUserIdentity.setUserId(ucenterUser.getUserId());
                        ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.TEACHER);
                        ucenterUserIdentity.setRelationCode(ucenterSchool.getSchoolCode());
                        ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
                        int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
                    }
                }else{

                    if(ucenterUser.getUsername().equals(ucenterTeacher.getPhone())&&!ucenterUser.getIdcard().equals(ucenterTeacher.getIdcard())){
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "系统存在电话号码相同，身份证号不同的用户");
                    }else{
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "系统存在身份证号相同，电话号码不同的用户");
                    }
                }
            }else{
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "系统中存在电话号码或身份证号重复用户");
            }
        }

        //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
        if(errorMap!=null&&errorMap.size()>errorNum){
            return errorMap;
        }

        if (ucenterUser != null){
            ucenterTeacher.setUserId(ucenterUser.getUserId());
        }

        //新增学生检查
        int count = ucenterTeacherMapper.insertSelective(ucenterTeacher);


        //新增教师部门关系

        if(deptList.size()>0){
            for(UcenterDepartment obj:deptList){
                UcenterTeacherPost ucenterTeacherPost=new UcenterTeacherPost();
                ucenterTeacherPost.setDepartmentId(obj.getDepartmentId());
                ucenterTeacherPost.setTeacherId(ucenterTeacher.getTeacherId());
                ucenterTeacherPostMapper.insert(ucenterTeacherPost);
            }
        }

        //老师和班级关系
        if(ucenterClasss!=null&&ucenterClasss.size()>0){
            for(int i=0;i<ucenterClasss.size();i++){
                UcenterTeacherClass ucenterTeacherClass=new UcenterTeacherClass();
                ucenterTeacherClass.setClassId(ucenterClasss.get(i).getClassId());
                ucenterTeacherClass.setTeacherId(ucenterTeacher.getTeacherId());
                int count1=ucenterTeacherClassMapper.insertSelective(ucenterTeacherClass);
            }
        }

        return errorMap;
    }

    @Override
    public List<UcenterTeacher> selectUcenterTeacherByPostId(Map<String, Object> map) {
        return ucenterTeacherMapper.selectUcenterTeacherByPostId(map);
    }

    @Override
    public List<UcenterTeacherDto> selectDataScopeUcenterTeacherInfoByAppIdAndRelationCode(Map<String, Object> map) {
        return ucenterTeacherMapper.selectDataScopeUcenterTeacherInfoByAppIdAndRelationCode(map);
    }

}