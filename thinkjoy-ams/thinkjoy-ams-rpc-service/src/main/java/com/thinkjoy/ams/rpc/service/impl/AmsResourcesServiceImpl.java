package com.thinkjoy.ams.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.ams.dao.model.AmsRoleResource;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ams.dao.mapper.AmsResourcesMapper;
import com.thinkjoy.ams.dao.model.AmsResources;
import com.thinkjoy.ams.dao.model.AmsResourcesExample;
import com.thinkjoy.ams.rpc.api.AmsResourcesService;
import com.thinkjoy.common.db.DataSourceEnum;
import com.thinkjoy.common.db.DynamicDataSource;
import com.thinkjoy.ucenter.dao.model.UcenterDictValues;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* AmsResourcesService实现
* Created by shuzheng on 2017/8/1.
*/
@Service
@Transactional
@BaseService
public class AmsResourcesServiceImpl extends BaseServiceImpl<AmsResourcesMapper, AmsResources, AmsResourcesExample> implements AmsResourcesService {

    private static Logger _log = LoggerFactory.getLogger(AmsResourcesServiceImpl.class);

    @Autowired
    AmsResourcesMapper amsResourcesMapper;

    @Override
    public JSONArray getResourceTree(Map<String, String> allResourceClass, String roleId, List<AmsResources> amsResources) {

        JSONArray rc = new JSONArray();

        for (Map.Entry<String,String> entry : allResourceClass.entrySet()){
            JSONObject jo = new JSONObject();
            jo.put("name", entry.getValue());
            //字段表中的value_key
            jo.put("vkey", entry.getKey());
            jo.put("nocheck", true);
            jo.put("open", false);
            rc.add(jo);
        }

        for (Object o : rc){

            String vkey = ((JSONObject) o).getString("vkey");

            JSONArray jsonArray = new JSONArray();


            AmsResourcesExample amsResourcesExample = new AmsResourcesExample();
            amsResourcesExample.createCriteria().andResourceClassEqualTo(((JSONObject) o).getString("vkey"));

            //相应分类对应的所有资源
            List<AmsResources> amsResources1 = amsResourcesMapper.selectByExample(amsResourcesExample);

            for (AmsResources r : amsResources1){
                JSONObject node = new JSONObject();
                node.put("resourceId", r.getResourceId());
                node.put("open", false);
                node.put("name", r.getResourceName());

                for (AmsResources ar : amsResources){
                    if (vkey.equals(ar.getResourceClass())){
                        node.put("checked",true);
                    }
                }

                jsonArray.add(node);
            }

            ((JSONObject) o).put("children", jsonArray);

        }

        return rc;
    }

    @Override
    public List<AmsResources> getResourcesByRoleId(Integer roleId) {
        return amsResourcesMapper.getResourcesByRoleId(roleId);
    }

}