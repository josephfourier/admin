package com.thinkjoy.ucenter.dao.mapper;

import com.thinkjoy.ucenter.dao.model.UcenterPaySetting;
import com.thinkjoy.ucenter.dao.model.UcenterPaySettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcenterPaySettingMapper {
    long countByExample(UcenterPaySettingExample example);

    int deleteByExample(UcenterPaySettingExample example);

    int deleteByPrimaryKey(Integer paysettingId);

    int insert(UcenterPaySetting record);

    int insertSelective(UcenterPaySetting record);

    List<UcenterPaySetting> selectByExample(UcenterPaySettingExample example);

    UcenterPaySetting selectByPrimaryKey(Integer paysettingId);

    int updateByExampleSelective(@Param("record") UcenterPaySetting record, @Param("example") UcenterPaySettingExample example);

    int updateByExample(@Param("record") UcenterPaySetting record, @Param("example") UcenterPaySettingExample example);

    int updateByPrimaryKeySelective(UcenterPaySetting record);

    int updateByPrimaryKey(UcenterPaySetting record);

    List<UcenterPaySetting>  getPaySettingListByOrderId(String order_id);
}