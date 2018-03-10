package com.thinkjoy.enrollment.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.enrollment.dao.mapper.EnrollTeacherMapper;
import com.thinkjoy.enrollment.dao.model.EnrollTeacher;
import com.thinkjoy.enrollment.dao.model.EnrollTeacherExample;

/**
* 降级实现EnrollTeacherService接口
* Created by user on 2017/11/2.
*/
public class EnrollTeacherServiceMock extends BaseServiceMock<EnrollTeacherMapper, EnrollTeacher, EnrollTeacherExample> implements EnrollTeacherService {

}
