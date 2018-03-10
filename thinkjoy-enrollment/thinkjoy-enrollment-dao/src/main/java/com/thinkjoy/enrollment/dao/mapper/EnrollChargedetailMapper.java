package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollChargedetail;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailExample;
import java.util.List;
import java.util.Map;

import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;
import org.apache.ibatis.annotations.Param;

public interface EnrollChargedetailMapper {
    long countByExample(EnrollChargedetailExample example);

    int deleteByExample(EnrollChargedetailExample example);

    int deleteByPrimaryKey(Integer detailId);

    int insert(EnrollChargedetail record);

    int insertSelective(EnrollChargedetail record);

    List<EnrollChargedetail> selectByExample(EnrollChargedetailExample example);

    EnrollChargedetail selectByPrimaryKey(Integer detailId);

    int updateByExampleSelective(@Param("record") EnrollChargedetail record, @Param("example") EnrollChargedetailExample example);

    int updateByExample(@Param("record") EnrollChargedetail record, @Param("example") EnrollChargedetailExample example);

    int updateByPrimaryKeySelective(EnrollChargedetail record);

    int updateByPrimaryKey(EnrollChargedetail record);

    List<SpecialtyInfoDto> getChargeDetailPriceAndSpecialtyInfo(Map<String, Object> map);

}