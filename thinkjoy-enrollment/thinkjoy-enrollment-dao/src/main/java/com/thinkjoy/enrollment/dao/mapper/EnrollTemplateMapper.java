package com.thinkjoy.enrollment.dao.mapper;

import com.thinkjoy.enrollment.dao.model.EnrollTemplate;
import com.thinkjoy.enrollment.dao.model.EnrollTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollTemplateMapper {
    long countByExample(EnrollTemplateExample example);

    int deleteByExample(EnrollTemplateExample example);

    int deleteByPrimaryKey(Integer templateId);

    int insert(EnrollTemplate record);

    int insertSelective(EnrollTemplate record);

    List<EnrollTemplate> selectByExample(EnrollTemplateExample example);

    EnrollTemplate selectByPrimaryKey(Integer templateId);

    int updateByExampleSelective(@Param("record") EnrollTemplate record, @Param("example") EnrollTemplateExample example);

    int updateByExample(@Param("record") EnrollTemplate record, @Param("example") EnrollTemplateExample example);

    int updateByPrimaryKeySelective(EnrollTemplate record);

    int updateByPrimaryKey(EnrollTemplate record);
}