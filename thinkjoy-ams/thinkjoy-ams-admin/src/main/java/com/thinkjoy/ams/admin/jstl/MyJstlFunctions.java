package com.thinkjoy.ams.admin.jstl;

import com.thinkjoy.common.util.SpringContextUtil;
import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import com.thinkjoy.ucenter.dao.model.UcenterDictValuesExample;
import com.thinkjoy.ucenter.rpc.api.UcenterDictValuesService;

import java.util.List;

/**
 * Created by wangcheng on 17/8/3.
 * 自定义标签函数
 */
public class MyJstlFunctions {

    /**
     * 根据字典code查询字典列表
     * @param code
     * @return
     */
    public static List<UcenterDictValues> getValueByCode(String code){
        UcenterDictValuesService ucenterDictValuesService = (UcenterDictValuesService)SpringContextUtil
                .getBean("ucenterDictValuesService");

        UcenterDictValuesExample ucenterDictValuesExample = new UcenterDictValuesExample();
        ucenterDictValuesExample.createCriteria().andDictCodeEqualTo(code);

        List<UcenterDictValues> ucenterDictValues = ucenterDictValuesService.selectByExample(ucenterDictValuesExample);

        return ucenterDictValues;
    }

}
