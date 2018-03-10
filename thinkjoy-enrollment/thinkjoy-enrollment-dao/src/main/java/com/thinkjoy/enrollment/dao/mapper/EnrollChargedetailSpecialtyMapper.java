package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialty;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialtyExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.ChargedetailSpeciaaltyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EnrollChargedetailSpecialtyMapper {
    long countByExample(EnrollChargedetailSpecialtyExample example);

	/**
	 * @param example
	 * @return
	 */
    int deleteByExample(EnrollChargedetailSpecialtyExample example);

    int deleteByPrimaryKey(@Param("detailId") Integer detailId, @Param("specialtyCode") String specialtyCode, @Param("year") Integer year, @Param("schoolCode") String schoolCode);

    int insert(EnrollChargedetailSpecialty record);

    int insertSelective(EnrollChargedetailSpecialty record);

    List<EnrollChargedetailSpecialty> selectByExample(EnrollChargedetailSpecialtyExample example);

    EnrollChargedetailSpecialty selectByPrimaryKey(@Param("detailId") Integer detailId, @Param("specialtyCode") String specialtyCode, @Param("year") Integer year, @Param("schoolCode") String schoolCode);

    int updateByExampleSelective(@Param("record") EnrollChargedetailSpecialty record, @Param("example") EnrollChargedetailSpecialtyExample example);

    int updateByExample(@Param("record") EnrollChargedetailSpecialty record, @Param("example") EnrollChargedetailSpecialtyExample example);

    int updateByPrimaryKeySelective(EnrollChargedetailSpecialty record);

    int updateByPrimaryKey(EnrollChargedetailSpecialty record);

	ChargedetailSpeciaaltyDto selectchargespecialtyBean(Map map);

	ChargedetailSpeciaaltyDto selectchargeBean(Map map);
}