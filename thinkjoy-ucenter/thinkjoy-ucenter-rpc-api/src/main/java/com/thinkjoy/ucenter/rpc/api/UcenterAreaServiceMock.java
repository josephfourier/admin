package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterAreaMapper;
import com.thinkjoy.ucenter.dao.model.UcenterArea;
import com.thinkjoy.ucenter.dao.model.UcenterAreaExample;

/**
* 降级实现UcenterAreaService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterAreaServiceMock extends BaseServiceMock<UcenterAreaMapper, UcenterArea, UcenterAreaExample> implements UcenterAreaService {

    @Override
    public JSONArray getAreaTree() {
        return null;
    }

	@Override
	public JSONArray getschoolTree(String areaCode) {
		return null;
	}
}
