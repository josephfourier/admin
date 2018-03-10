package com.thinkjoy.ams.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ams.dao.model.AmsProject;
import com.thinkjoy.ams.dao.model.AmsProjectExample;

import java.util.List;

/**
 * AmsProjectService接口
 *
 * @author wangcheng
 * @date 2017/7/27
 */
public interface AmsProjectService extends BaseService<AmsProject, AmsProjectExample> {
    List<String> getAmsProjectRelatedCodes(Integer pid);

    /**
     * 给项目关联组织与应用
     *
     * @param datas
     * @param appId
     * @param pid
     * @return
     */
    int relateAgencyAndApp(JSONArray datas, String[] appId, Integer pid);
}