package com.thinkjoy.ams.dao.mapper;

import com.thinkjoy.ams.dao.model.OauthCode;
import com.thinkjoy.ams.dao.model.OauthCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OauthCodeMapper {
    long countByExample(OauthCodeExample example);

    int deleteByExample(OauthCodeExample example);

    int insert(OauthCode record);

    int insertSelective(OauthCode record);

    List<OauthCode> selectByExample(OauthCodeExample example);

    int updateByExampleSelective(@Param("record") OauthCode record, @Param("example") OauthCodeExample example);

    int updateByExample(@Param("record") OauthCode record, @Param("example") OauthCodeExample example);
}