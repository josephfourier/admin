package com.thinkjoy.ucenter.rpc.api;

import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterUserMapper;
import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterTeacher;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserAccountDto;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserInfoDto;

import java.util.List;
import java.util.Map;

/**
* 降级实现UcenterUserService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterUserServiceMock extends BaseServiceMock<UcenterUserMapper, UcenterUser, UcenterUserExample> implements UcenterUserService {

    @Override
    public List<UcenterUser> selectByIdentityRelationCodeForOffsetPage(Map<String, Object> map) {
        return null;
    }

    @Override
    public long countByIdentityRelationCodeForOffsetPage(Map<String, Object> map) {
        return 0;
    }

    @Override
    public UcenterUser createUserBeforeCreateStudent(UcenterStudent ucenterStudent, String schoolCode) {
        return null;
    }

    @Override
    public UcenterUser createUserBeforeCreateTeacher(UcenterTeacher ucenterTeacher, String schoolCode) {
        return null;
    }

    @Override
    public int createTeacherSpecialtyAndClass(Integer teacherId, String specialtyCodes, String classIds) {
        return 0;
    }

    @Override
    public UserInfoDto getUserInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public UserAccountDto getUserAccount(Map<String, Object> map) {
        return null;
    }

    @Override
	public UcenterUser selectuserbeanMap(String username) {return null;}

}
