package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterStudent;
import com.thinkjoy.ucenter.dao.model.UcenterUser;
import com.thinkjoy.ucenter.dao.model.UcenterUserExample;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserAccountDto;
import com.thinkjoy.ucenter.dao.model.ucenterDto.UserInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterUserMapper {
    long countByExample(UcenterUserExample example);

    int deleteByExample(UcenterUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UcenterUser record);

    int insertSelective(UcenterUser record);

    List<UcenterUser> selectByExample(UcenterUserExample example);

    UcenterUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UcenterUser record, @Param("example") UcenterUserExample example);

    int updateByExample(@Param("record") UcenterUser record, @Param("example") UcenterUserExample example);

    int updateByPrimaryKeySelective(UcenterUser record);

    int updateByPrimaryKey(UcenterUser record);

    List<UcenterUser> selectByIdentityRelationCodeForOffsetPage(Map<String,Object> map);

    long countByIdentityRelationCodeForOffsetPage(Map<String, Object> map);

    int insertBatch(List<UcenterUser> record);

    int createUserBeforeCreateStudent(UcenterStudent ucenterStudent, String schoolCode);

    UserInfoDto getUserInfo(Map<String, Object> map);

	UcenterUser selectuserbeanMap(Map map);

    UserAccountDto getUserAccount(Map<String, Object> map);
}