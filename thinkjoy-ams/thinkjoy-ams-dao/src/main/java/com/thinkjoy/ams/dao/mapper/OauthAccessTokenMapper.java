package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.OauthAccessToken;
import com.thinkjoy.ams.dao.model.OauthAccessTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OauthAccessTokenMapper {
    long countByExample(OauthAccessTokenExample example);

    int deleteByExample(OauthAccessTokenExample example);

    int insert(OauthAccessToken record);

    int insertSelective(OauthAccessToken record);

    List<OauthAccessToken> selectByExample(OauthAccessTokenExample example);

    int updateByExampleSelective(@Param("record") OauthAccessToken record, @Param("example") OauthAccessTokenExample example);

    int updateByExample(@Param("record") OauthAccessToken record, @Param("example") OauthAccessTokenExample example);
}