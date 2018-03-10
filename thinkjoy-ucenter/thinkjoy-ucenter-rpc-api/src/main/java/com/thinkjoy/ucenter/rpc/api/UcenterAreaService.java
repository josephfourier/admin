package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;


/**
* UcenterAreaService接口
* Created by xufei on 2017/7/26.
*/
public interface UcenterAreaService extends BaseService<UcenterArea, UcenterAreaExample> {

    JSONArray getAreaTree();
	JSONArray getschoolTree(String areaCode);
}