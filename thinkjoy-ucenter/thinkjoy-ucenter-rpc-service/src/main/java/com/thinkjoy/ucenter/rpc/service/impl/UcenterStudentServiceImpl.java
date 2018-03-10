package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.util.AESUtil;
import com.thinkjoy.common.util.StringUtil;
import com.thinkjoy.common.util.UUIDTool;
import com.thinkjoy.common.util.xmlutil.Table;
import com.thinkjoy.exception.BusindessException;
import com.thinkjoy.ucenter.dao.mapper.UcenterStudentMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserIdentityMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserMapper;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterSchoolService;
import com.thinkjoy.ucenter.rpc.api.UcenterStudentService;
import com.thinkjoy.ucenter.rpc.api.UcenterUserService;
import com.thinkjoy.ucenter.rpc.exception.DuplicatePropertyException;
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
import java.util.*;

/**
 * UcenterStudentService实现
 * Created by user on 2017/9/20.
 */
@Service
@Transactional
@BaseService
public class UcenterStudentServiceImpl extends BaseServiceImpl<UcenterStudentMapper, UcenterStudent, UcenterStudentExample> implements UcenterStudentService {

    private static Logger _log = LoggerFactory.getLogger(UcenterStudentServiceImpl.class);

    @Autowired
    UcenterStudentMapper ucenterStudentMapper;

    @Autowired
    UcenterUserService ucenterUserService;

    @Autowired
    UcenterUserMapper ucenterUserMapper;

    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;


    @Autowired
    UcenterSchoolService ucenterSchoolService;

    @Override
    public int insertStudent(UcenterStudent ucenterStudent, String schoolCode) throws Exception {
        UcenterUser userBeforeCreateStudent = ucenterUserService.createUserBeforeCreateStudent(ucenterStudent, schoolCode);
        if (userBeforeCreateStudent != null) {
            ucenterStudent.setUserId(userBeforeCreateStudent.getUserId());
        } else {
            throw new UcenterBusinessException("添加学生账号以及身份失败!");
        }

        int i = ucenterStudentMapper.insertSelective(ucenterStudent);

        return i;
    }

    @Override
    public int updateStudent(UcenterStudent ucenterStudent) throws Exception {
        int count = 0;

        try {
            //更新学生账户信息
            UcenterUser ucenterUser = ucenterUserMapper.selectByPrimaryKey(ucenterStudent.getUserId());
            if (ucenterUser == null) {
                throw new UcenterBusinessException("无账户信息");
            }

            ucenterUser.setStudentCode(ucenterStudent.getStudentCode());
            ucenterUser.setEmail(ucenterStudent.getMail());
            //为迎新的需求,不为空才更新
            if (StringUtils.isNotBlank(ucenterStudent.getExamineeNumber())) {
                ucenterUser.setExamineeNumber(ucenterStudent.getExamineeNumber());
            }
            ucenterUser.setPhone(ucenterStudent.getPhone());
            ucenterUser.setIdcard(ucenterStudent.getIdcard());
            ucenterUser.setSex(ucenterStudent.getSex());
            ucenterUser.setFullname(ucenterStudent.getStudentName());
            ucenterUser.setSalt("");
            ucenterUser.setStatus(ucenterStudent.getStatus());
            int count1 = ucenterUserMapper.updateByPrimaryKey(ucenterUser);
            count = ucenterStudentMapper.updateByPrimaryKeySelective(ucenterStudent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UcenterBusinessException("更新学生失败!");
        }

        return count;
    }

    @Override
    public int deleteStudent(String ids) throws Exception {

        int i = 0;
        try {
            // 删除学生时需要同时删除学生的账户信息
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            String[] idArray = ids.split("-");
            ArrayList<Integer> idvalues = new ArrayList<>();
            ArrayList<Integer> uidvalues = new ArrayList<>();
            if (idArray != null && idArray.length > 0) {
                for (String i2 : idArray) {
                    int i1 = Integer.parseInt(i2);
                    UcenterStudent ucenterStudent = ucenterStudentMapper.selectByPrimaryKey(i1);
                    uidvalues.add(ucenterStudent.getUserId());
                    idvalues.add(i1);
                }
            }

            //删除学生
            UcenterStudentExample ucenterStudentExample = new UcenterStudentExample();
            ucenterStudentExample.createCriteria().andStudentIdIn(idvalues);
            int i1 = ucenterStudentMapper.deleteByExample(ucenterStudentExample);

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



    /**
     * 学生信息导入
     * 1、验证学生信息是否重复
     * 2、新增学生前先给学生新增账户，如果不存在直接新增，如果身份证，电话能对应上
     * @param ucenterStudent 学生实体
     * @param ucenterSchool
     * @param errorMap
     * @param tableValid
     * @param rowNum
     * @param errorNum
     * @return
     */
    @Override
    public Map<String, String> importUcenterStudent(UcenterStudent ucenterStudent, UcenterSchool ucenterSchool, Map<String, String> errorMap, Table tableValid, int rowNum,long errorNum) {
        if (errorMap==null){
            errorMap=new HashMap<>();
        }
        //验证学生信息是否重复 身份证号,电话号
        UcenterStudentExample ucenterStudentExample=new UcenterStudentExample();
        ucenterStudentExample.or().andIdcardEqualTo(ucenterStudent.getIdcard());
        ucenterStudentExample.or().andPhoneEqualTo(ucenterStudent.getPhone());
        List<UcenterStudent> students=selectByExample(ucenterStudentExample);
        if(students!=null&&students.size()>0){
            if(students.size()==1){
                if(students.get(0).getPhone().equals(ucenterStudent.getPhone())){
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "手机号码已存在");
                }
                if(students.get(0).getIdcard().equals(ucenterStudent.getIdcard())){
                    errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("idcard")), "身份证号已存在");
                }
            }else{
                errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "手机号码，身份证号出现重复");
            }
        }

        //当前数据， 如果处理后错误数大于处理前错误数 说明当前数据有错误，结束当前循环
        if(errorMap!=null&&errorMap.size()>errorNum){
            return errorMap;
        }

        //新增学生前先给学生新增账户
        UcenterUserExample ucenterUserExample=new UcenterUserExample();
        ucenterUserExample.or().andIdcardEqualTo(ucenterStudent.getIdcard());
        ucenterUserExample.or().andUsernameEqualTo(ucenterStudent.getPhone());
        List<UcenterUser> ucenterUsers=ucenterUserService.selectByExample(ucenterUserExample);
        UcenterUser ucenterUser=null;
        if(ucenterUsers==null||ucenterUsers.size()==0){
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
                ucenterUser.setExamineeNumber(ucenterStudent.getExamineeNumber());
                ucenterUser.setPhone(ucenterStudent.getPhone());
                ucenterUser.setIdcard(ucenterStudent.getIdcard());
                ucenterUser.setSex(ucenterStudent.getSex());
                ucenterUser.setCreator(ucenterStudent.getCreator());
                ucenterUser.setCtime(ucenterStudent.getCtime());
                ucenterUser.setFullname(ucenterStudent.getStudentName());
                ucenterUser.setSalt("");
                ucenterUser.setStatus(ucenterStudent.getStatus());
                ucenterUser.setYear(ucenterStudent.getYear());
                ucenterUser.setSchoolCode(ucenterStudent.getSchoolCode());
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
            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.STUDENT);
            ucenterUserIdentity.setRelationCode(ucenterSchool.getSchoolCode());
            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
        }else{
            if(ucenterUsers.size()==1){
                ucenterUser=ucenterUsers.get(0);
                if(ucenterUser.getUsername().equals(ucenterStudent.getPhone())&&ucenterUser.getIdcard().equals(ucenterStudent.getIdcard())){
                    UcenterUserExample updateUcenterUserExample=new UcenterUserExample();
                    updateUcenterUserExample.createCriteria().andIdcardEqualTo(ucenterStudent.getIdcard()).andUsernameEqualTo(ucenterStudent.getPhone());
                    //需要更新的字段
                    UcenterUser ucenterUser2 = new UcenterUser();

                    // 默认用电话号码作为登录名
                    // TODO 确认账号生成规则
//                ucenterUser2.setUsername(ucenterStudent.getPhone());
//                ucenterUser2.setUid(UUIDTool.getUUID());
//                ucenterUser2.setPassword(AESUtil.AESEncode(idCard.substring(idCard.length() - 6, idCard.length())));
//                ucenterUser2.setSalt("");

                    ucenterUser2.setEmail(ucenterStudent.getMail());
                    ucenterUser2.setStudentCode(ucenterStudent.getStudentCode());

                    ucenterUser2.setSex(ucenterStudent.getSex());
                    ucenterUser2.setCreator(ucenterStudent.getCreator());
                    ucenterUser2.setCtime(ucenterStudent.getCtime());
                    ucenterUser2.setFullname(ucenterStudent.getStudentName());
                    ucenterUser2.setStatus(ucenterStudent.getStatus());
                    ucenterUser2.setYear(ucenterStudent.getYear());
                    ucenterUser2.setSchoolCode(ucenterStudent.getSchoolCode());

                    if(StringUtils.isNotBlank(ucenterStudent.getExamineeNumber())&&StringUtils.isNotBlank(ucenterUser.getExamineeNumber())&&!ucenterUser.getExamineeNumber().equals(ucenterStudent.getExamineeNumber())){
                        //考生号与系统已有考生号不对应，不生成账号
                        errorMap.put(StringUtil.generatorXY(rowNum, tableValid.getPositonBycolName("examineeNumber")), "考生号与系统已有考生号不对应");
                    }else{
                        if(StringUtils.isNotBlank(ucenterStudent.getExamineeNumber())&&StringUtils.isBlank(ucenterUser.getExamineeNumber())){
                            ucenterUser2.setExamineeNumber(ucenterStudent.getExamineeNumber());
                        }
                        ucenterUserMapper.updateByExampleSelective(ucenterUser2,updateUcenterUserExample);

                        UcenterUserIdentityExample ucenterUserIdentityExample=new UcenterUserIdentityExample();
                        ucenterUserIdentityExample.createCriteria().andUserIdEqualTo(ucenterUser.getUserId()).andUsertypeIdEqualTo(BaseConstants.UserType.STUDENT).andRelationCodeEqualTo(ucenterSchool.getSchoolCode());
                        long count=ucenterUserIdentityMapper.countByExample(ucenterUserIdentityExample);
                        if(count<=0){//如果没有身份数据，新增
                            //保存用户身份
                            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
                            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
                            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.STUDENT);
                            ucenterUserIdentity.setRelationCode(ucenterSchool.getSchoolCode());
                            ucenterUserIdentity.setRelationName(ucenterSchool.getSchoolName());
                            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
                        }
                    }
                }else{
                    if(ucenterUser.getUsername().equals(ucenterStudent.getPhone())&&!ucenterUser.getIdcard().equals(ucenterStudent.getIdcard())){
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("phone")), "系统存在电话号码相同，身份证号不同的用户");
                    }else{
                        errorMap.put(StringUtil.generatorXY(rowNum,tableValid.getPositonBycolName("idcard")), "系统存在身份证号相同，电话号码不同的用户");
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
            ucenterStudent.setUserId(ucenterUser.getUserId());
        }

        //新增学生检查
        int count = ucenterStudentMapper.insertSelective(ucenterStudent);

        return errorMap;
    }

    @Override
    public void syszjyaccount(UcenterStudent ucenterStudent) {

            UcenterUser ucenterUser = new UcenterUser();
            ucenterUser.setUsername(ucenterStudent.getPhone());
            ucenterUser.setUid(UUIDTool.getUUID());
            ucenterUser.setPassword(AESUtil.AESEncode(
                    ucenterStudent.getIdcard().substring(
                            ucenterStudent.getIdcard().length() - 6, ucenterStudent.getIdcard().length())));
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

            //保存账号信息已处理
            int count = ucenterUserService.insertSelective(ucenterUser);


            UcenterUserIdentity ucenterUserIdentity = new UcenterUserIdentity();
            ucenterUserIdentity.setUserId(ucenterUser.getUserId());
            ucenterUserIdentity.setUsertypeId(BaseConstants.UserType.STUDENT);
            ucenterUserIdentity.setRelationCode(ucenterStudent.getSchoolCode());

            UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
            ucenterSchoolExample.createCriteria().andSchoolCodeEqualTo(ucenterStudent.getSchoolCode());
            List<UcenterSchool> ucenterSchools = ucenterSchoolService.selectByExample(ucenterSchoolExample);

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(ucenterSchools)) {
                ucenterUserIdentity.setRelationName(ucenterSchools.get(0).getSchoolName());
            }
            //保存身份信息一处理
            int i = ucenterUserIdentityMapper.insertSelective(ucenterUserIdentity);
            //保存学生信息

            //学生姓名
            //手机号
            // 身份证号
            //性别
            //民族
            //**院系
            //**专业
            //**班级
            //入学年份
            //学号
            ucenterStudent.setUserId(ucenterUser.getUserId());
            ucenterStudentMapper.insertSelective(ucenterStudent);
            //保存家庭关系信息

    }
}