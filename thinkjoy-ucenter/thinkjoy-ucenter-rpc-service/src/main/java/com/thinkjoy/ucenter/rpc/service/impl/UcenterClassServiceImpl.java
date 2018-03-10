package com.thinkjoy.ucenter.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.common.db.DataSourceEnum;
import com.thinkjoy.common.db.DynamicDataSource;
import com.thinkjoy.ucenter.dao.mapper.UcenterClassMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentBusMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterDepartmentMapper;
import com.thinkjoy.ucenter.dao.model.*;
import com.thinkjoy.ucenter.rpc.api.UcenterClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
* UcenterClassService实现
* Created by user on 2017/10/13.
*/
@Service
@Transactional
@BaseService
public class UcenterClassServiceImpl extends BaseServiceImpl<UcenterClassMapper, UcenterClass, UcenterClassExample> implements UcenterClassService {

    private static Logger _log = LoggerFactory.getLogger(UcenterClassServiceImpl.class);

    @Autowired
    UcenterClassMapper ucenterClassMapper;
    @Autowired
    UcenterDepartmentMapper ucenterDepartmentMapper;
    @Autowired
    UcenterDepartmentBusMapper ucenterDepartmentBusMapper;

    @Override
    public int insertSelective(UcenterClass ucenterClass, UcenterDepartment ucenterDepartment) {
        UcenterDepartmentBus ucenterDepartmentBus=new UcenterDepartmentBus();
        int scount=ucenterClassMapper.insertSelective(ucenterClass);
        int mcount=ucenterDepartmentMapper.insertSelective(ucenterDepartment);
        ucenterDepartmentBus.setBusId(ucenterClass.getClassId());
        ucenterDepartmentBus.setDepartmentId(ucenterDepartment.getDepartmentId());
        int count=ucenterDepartmentBusMapper.insertSelective(ucenterDepartmentBus);
        return count;
    }

    @Override
    public int updateByPrimaryKeySelective(UcenterClass ucenterClass, UcenterDepartmentBus departmentBus, UcenterDepartment ucenterDepartment) {
        UcenterDepartmentBusExample ucenterDepartmentBusExample=new UcenterDepartmentBusExample();
        UcenterDepartmentBusExample.Criteria criteria=ucenterDepartmentBusExample.createCriteria();
        UcenterDepartmentBus ucenterDepartmentBus=new UcenterDepartmentBus();
        int scount=ucenterClassMapper.updateByPrimaryKeySelective(ucenterClass);
        Integer departmentId=departmentBus.getDepartmentId();
        Integer classId=ucenterClass.getClassId();
        if (departmentId!=null) {
            int mcount=ucenterDepartmentMapper.updateByPrimaryKeySelective(ucenterDepartment);
        }
        if (classId!=null) {
            criteria.andBusIdEqualTo(classId);
            ucenterDepartmentBusMapper.deleteByExample(ucenterDepartmentBusExample);
        }
        ucenterDepartmentBus.setBusId(classId);
        ucenterDepartmentBus.setDepartmentId(departmentId);
        int count=this.ucenterDepartmentBusMapper.insertSelective(ucenterDepartmentBus);
        return count;
    }

    @Override
    public int deletePrimaryKey(Integer classId, UcenterDepartmentBus departmentBus) {
        try {
            UcenterDepartmentExample ucenterDepartmentExample = new UcenterDepartmentExample();
            UcenterDepartmentExample.Criteria criteria=ucenterDepartmentExample.createCriteria();

            UcenterDepartmentBusExample ucenterDepartmentBusExample=new UcenterDepartmentBusExample();
            UcenterDepartmentBusExample.Criteria criteria1=ucenterDepartmentBusExample.createCriteria();


            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
            int count = 0;
            Integer departmentId=departmentBus.getDepartmentId();
            criteria1.andBusIdEqualTo(classId);
            int mcount=ucenterDepartmentBusMapper.deleteByExample(ucenterDepartmentBusExample);

            criteria.andDepartmentIdEqualTo(departmentId);
            int scount=ucenterDepartmentMapper.deleteByExample(ucenterDepartmentExample);

            Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", classId.getClass());

            Object result = deleteByPrimaryKey.invoke(mapper, classId);
            count += Integer.parseInt(String.valueOf(result));
            return count;
        } catch (Exception e) {

            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {

                    String res = handleException(e);
                    throw new RuntimeException(res);
                }
            }
        }
        DynamicDataSource.clearDataSource();
        return 0;
    }
}