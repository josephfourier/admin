package com.thinkjoy.ucenter.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.dao.mapper.UcenterTeacherPostMapper;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPost;
import com.thinkjoy.ucenter.dao.model.UcenterTeacherPostExample;
import com.thinkjoy.ucenter.rpc.api.UcenterTeacherPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* UcenterTeacherPostService实现
* Created by user on 2018/2/13.
*/
@Service
@Transactional
@BaseService
class UcenterTeacherPostServiceImpl extends BaseServiceImpl<UcenterTeacherPostMapper, UcenterTeacherPost, UcenterTeacherPostExample> implements UcenterTeacherPostService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTeacherPostServiceImpl.class);

    @Autowired
    UcenterTeacherPostMapper ucenterTeacherPostMapper;

    @Override
    public int addteacher(JSONArray datas, int id) {
         //先清理教师id的职务信息,在保存职务对象
        ucenterTeacherPostMapper.updateSelective(id);
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            Integer teacherId=json.getIntValue("id");
            Integer departmentId=json.getIntValue("pId");
            UcenterTeacherPost ucenterTeacherPost=new UcenterTeacherPost();
            // 新增老师职务
            ucenterTeacherPost.setPostId(id);
            ucenterTeacherPost.setTeacherId(teacherId);
            ucenterTeacherPost.setDepartmentId(departmentId);
            int count=ucenterTeacherPostMapper.updateByPrimaryKeySelective(ucenterTeacherPost);
        }

        return datas.size();
    }
}