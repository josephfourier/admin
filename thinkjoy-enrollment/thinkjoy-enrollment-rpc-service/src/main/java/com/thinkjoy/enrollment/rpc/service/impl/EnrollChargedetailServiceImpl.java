package com.thinkjoy.enrollment.rpc.service.impl;

import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargedetailMapper;
import com.thinkjoy.enrollment.dao.mapper.EnrollChargedetailSpecialtyMapper;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetail;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailExample;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialty;
import com.thinkjoy.enrollment.dao.model.EnrollChargedetailSpecialtyExample;
import com.thinkjoy.enrollment.dao.model.enrollDto.SpecialtyInfoDto;
import com.thinkjoy.enrollment.rpc.api.EnrollChargedetailService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EnrollChargedetailService实现
 * Created by user on 2017/11/10.
 */
@Service
@Transactional
@BaseService
public class EnrollChargedetailServiceImpl extends BaseServiceImpl<EnrollChargedetailMapper, EnrollChargedetail, EnrollChargedetailExample> implements EnrollChargedetailService {

    private static Logger _log = LoggerFactory.getLogger(EnrollChargedetailServiceImpl.class);

    @Autowired
    EnrollChargedetailMapper enrollChargedetailMapper;

    @Autowired
    EnrollChargedetailSpecialtyMapper enrollChargedetailSpecialtyMapper;

    @Override
    public int insertChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes) {

        int i = enrollChargedetailMapper.insertSelective(chargedetail);
        assert chargedetail.getDetailId() != null;

        if (CollectionUtils.isNotEmpty(specialtyCodes)) {
            for (String code : specialtyCodes) {

                EnrollChargedetailSpecialty enrollChargedetailSpecialty = new EnrollChargedetailSpecialty();
                enrollChargedetailSpecialty.setDetailId(chargedetail.getDetailId());
                enrollChargedetailSpecialty.setSpecialtyCode(code);
                enrollChargedetailSpecialty.setItemId(chargedetail.getItemId());
                enrollChargedetailSpecialty.setYear(chargedetail.getYear());
                enrollChargedetailSpecialty.setSchoolCode(chargedetail.getSchoolCode());

                enrollChargedetailSpecialtyMapper.insertSelective(enrollChargedetailSpecialty);
            }
        }

        return i;
    }

    @Override
    public int updateChargeDetail(EnrollChargedetail chargedetail, List<String> specialtyCodes) {

        //更新缴费项目
        int i = enrollChargedetailMapper.updateByPrimaryKeySelective(chargedetail);

        //删除之前的关联关系
        EnrollChargedetailSpecialtyExample enrollChargedetailSpecialtyExample = new EnrollChargedetailSpecialtyExample();
        enrollChargedetailSpecialtyExample.createCriteria()
                .andDetailIdEqualTo(chargedetail.getDetailId())
                .andSchoolCodeEqualTo(chargedetail.getSchoolCode())
                .andYearEqualTo(chargedetail.getYear());
                //.andItemIdEqualTo(chargedetail.getItemId());
        int count=enrollChargedetailSpecialtyMapper.deleteByExample(enrollChargedetailSpecialtyExample);

        //维护关联关系
        if (CollectionUtils.isNotEmpty(specialtyCodes)) {
            for (String code : specialtyCodes) {

                EnrollChargedetailSpecialty enrollChargedetailSpecialty = new EnrollChargedetailSpecialty();
                enrollChargedetailSpecialty.setDetailId(chargedetail.getDetailId());
                enrollChargedetailSpecialty.setSpecialtyCode(code);
                enrollChargedetailSpecialty.setItemId(chargedetail.getItemId());
                enrollChargedetailSpecialty.setYear(chargedetail.getYear());
                enrollChargedetailSpecialty.setSchoolCode(chargedetail.getSchoolCode());

                enrollChargedetailSpecialtyMapper.insertSelective(enrollChargedetailSpecialty);
            }
        }

        return i;
    }

    @Override
    public int deleteChargeDetail(String ids) {

        if (StringUtils.isBlank(ids)) {
            return 0;
        }

        String[] idArray = ids.split("-");
        List<Integer> idList = new ArrayList<>();
        for (String idStr : idArray) {
            idList.add(Integer.parseInt(idStr));
        }

        int i1 = 0;
        try {
//            //删除关联关系
//            EnrollChargedetailSpecialtyExample enrollChargedetailSpecialtyExample = new EnrollChargedetailSpecialtyExample();
//            enrollChargedetailSpecialtyExample.createCriteria()
//                    .andDetailIdIn(idList);
//            int i = enrollChargedetailSpecialtyMapper.deleteByExample(enrollChargedetailSpecialtyExample);

            //删除缴费项目
            EnrollChargedetailExample enrollChargedetailExample = new EnrollChargedetailExample();
            enrollChargedetailExample.createCriteria()
                    .andDetailIdIn(idList);
            i1 = enrollChargedetailMapper.deleteByExample(enrollChargedetailExample);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                if (((InvocationTargetException) e).getTargetException() instanceof DataIntegrityViolationException) {
                    //String res = handleException(e);
                    throw new RuntimeException("请先删除关联数据!");
                }
            }

            if (e instanceof DataIntegrityViolationException) {
                throw new RuntimeException("请先删除关联数据!");
            }
        }

        return i1;
    }

    @Override
    public List<SpecialtyInfoDto> getChargeDetailPriceAndSpecialtyInfo(Map<String, Object> map) {
        return enrollChargedetailMapper.getChargeDetailPriceAndSpecialtyInfo(map);
    }


}