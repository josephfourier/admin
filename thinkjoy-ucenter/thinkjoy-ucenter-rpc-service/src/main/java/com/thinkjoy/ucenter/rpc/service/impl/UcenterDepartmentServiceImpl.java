package com.thinkjoy.ucenter.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherPostMapper;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterDepartmentService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
class UcenterDepartmentServiceImpl extends BaseServiceImpl<UcenterDepartmentMapper, UcenterDepartment, UcenterDepartmentExample> implements UcenterDepartmentService {

    private static Logger _log = LoggerFactory.getLogger(UcenterDepartmentServiceImpl.class);

    @Autowired
    UcenterDepartmentMapper ucenterDepartmentMapper;

    @Autowired
    UcenterTeacherMapper ucenterTeacherMapper;

    @Autowired
    UcenterTeacherPostMapper ucenterTeacherPostMapper;


    @Override
    public JSONArray getDepTree(String schoolCode) {
        JSONArray ja=new JSONArray();
        UcenterDepartmentExample ucenterDepartmentExample=new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode);
        List<UcenterDepartment> list= ucenterDepartmentMapper.selectByExample(ucenterDepartmentExample);
        for(UcenterDepartment obj:list){
            JSONObject jb=new JSONObject();
            jb.put("id",obj.getDepartmentId());
            jb.put("pId",obj.getParentId());
            jb.put("name",obj.getDepartmentName());
            jb.put("open","true");
            jb.put("status",obj.getStatus());
            ja.add(jb);
        }
        return ja;
    }

    @Override
    public JSONArray getDepTreeByTeacherId(String schoolCode, String teacherId) {
        JSONArray ja=new JSONArray();
        UcenterDepartmentExample ucenterDepartmentExample=new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode);
        UcenterTeacherPostExample ucenterTeacherPostExample=new UcenterTeacherPostExample();
        ucenterTeacherPostExample.createCriteria().andTeacherIdEqualTo(Integer.parseInt(teacherId));
        List<UcenterTeacherPost> listobj=ucenterTeacherPostMapper.selectByExample(ucenterTeacherPostExample);
        List<UcenterDepartment> list= ucenterDepartmentMapper.selectByExample(ucenterDepartmentExample);
        for(UcenterDepartment obj:list){
            JSONObject jb=new JSONObject();
            jb.put("id",obj.getDepartmentId());
            jb.put("pId",obj.getParentId());
            jb.put("name",obj.getDepartmentName());
            jb.put("open","true");
            jb.put("status", obj.getStatus());
            ja.add(jb);

            for(UcenterTeacherPost teacherPost:listobj){
                if(obj.getDepartmentId().intValue()==teacherPost.getDepartmentId().intValue()){
                    jb.put("checked", true);
                }
            }
        }
        return ja;
    }

    @Override
    public JSONArray getDepteacherTree(String schoolCode,Integer postId) {
        JSONArray ja=new JSONArray();
        UcenterDepartmentExample ucenterDepartmentExample=new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode);
        List<UcenterDepartment> list= ucenterDepartmentMapper.selectByExample(ucenterDepartmentExample);
        UcenterTeacher teacherbean=null;
        List<UcenterTeacherPost> teacherPostList=null;
        List<UcenterTeacherPost> postList=null;
        for(UcenterDepartment obj:list){
            JSONObject jb=new JSONObject();
            jb.put("id", obj.getDepartmentId());
            jb.put("pId", obj.getParentId());
            jb.put("name", obj.getDepartmentName());
            jb.put("open", "true");
            jb.put("nocheck","true");
            jb.put("status", obj.getStatus());
            ja.add(jb);
            UcenterTeacherPostExample ucenterTeacherPostExample=new UcenterTeacherPostExample();
            UcenterTeacherPostExample.Criteria criteria=ucenterTeacherPostExample.createCriteria();
            criteria.andDepartmentIdEqualTo(obj.getDepartmentId());
            teacherPostList=ucenterTeacherPostMapper.selectByExample(ucenterTeacherPostExample);
            for(UcenterTeacherPost teacherPostObj:teacherPostList){
                JSONObject jb1=new JSONObject();
                teacherbean=ucenterTeacherMapper.selectByPrimaryKey(teacherPostObj.getTeacherId());
                jb1.put("id",teacherbean.getTeacherId());
                jb1.put("pId",teacherPostObj.getDepartmentId());
                jb1.put("name",teacherbean.getTeacherName());
                jb1.put("open","true");
                jb1.put("status", teacherbean.getStatus());
                UcenterTeacherPostExample ucenterTeacherPostExample1=new UcenterTeacherPostExample();
                UcenterTeacherPostExample.Criteria criteria1=ucenterTeacherPostExample1.createCriteria();
                criteria1.andPostIdEqualTo(postId).andDepartmentIdEqualTo(obj.getDepartmentId());
                postList=ucenterTeacherPostMapper.selectByExample(ucenterTeacherPostExample1);
                if(postList!=null){
                    for (UcenterTeacherPost ar : postList){
                        if (teacherbean.getTeacherId().equals(ar.getTeacherId())){
                            jb1.put("checked",true);
                        }
                    }
                }
                ja.add(jb1);
            }
        }
        return ja;
    }

    @Override
    public JSONArray getDepteacherTree(String schoolCode) {
        JSONArray ja=new JSONArray();
        UcenterDepartmentExample ucenterDepartmentExample=new UcenterDepartmentExample();
        ucenterDepartmentExample.createCriteria()
                .andSchoolCodeEqualTo(schoolCode);
        List<UcenterDepartment> list= ucenterDepartmentMapper.selectByExample(ucenterDepartmentExample);
        UcenterTeacher teacherbean=null;
        List<UcenterTeacherPost> teacherPostList=null;
        for(UcenterDepartment obj:list){
            JSONObject jb=new JSONObject();
            jb.put("id", obj.getDepartmentId());
            jb.put("pId", obj.getParentId());
            jb.put("name", obj.getDepartmentName());
            jb.put("open", "true");
            jb.put("nocheck","true");
            jb.put("status", obj.getStatus());
            ja.add(jb);
            UcenterTeacherPostExample ucenterTeacherPostExample=new UcenterTeacherPostExample();
            UcenterTeacherPostExample.Criteria criteria=ucenterTeacherPostExample.createCriteria();
            criteria.andDepartmentIdEqualTo(obj.getDepartmentId());
            teacherPostList=ucenterTeacherPostMapper.selectByExample(ucenterTeacherPostExample);
            for(UcenterTeacherPost teacherPostObj:teacherPostList){
                JSONObject jb1=new JSONObject();
                teacherbean=ucenterTeacherMapper.selectByPrimaryKey(teacherPostObj.getTeacherId());
                jb1.put("id",teacherbean.getTeacherId());
                jb1.put("pId",teacherPostObj.getDepartmentId());
                jb1.put("name",teacherbean.getTeacherName());
                jb1.put("open","true");
                jb1.put("status", teacherbean.getStatus());
                ja.add(jb1);
            }
        }
        return ja;
    }
}