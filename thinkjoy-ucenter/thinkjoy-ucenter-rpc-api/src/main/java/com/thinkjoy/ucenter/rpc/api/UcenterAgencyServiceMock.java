package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseServiceMock;
import com.thinkjoy.ucenter.dao.mapper.UcenterAgencyMapper;
import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;

import java.util.List;

/**
* 降级实现UcenterAgencyService接口
* Created by xufei on 2017/7/26.
*/
public class UcenterAgencyServiceMock extends BaseServiceMock<UcenterAgencyMapper, UcenterAgency, UcenterAgencyExample> implements UcenterAgencyService {


    @Override
    public JSONArray getSchoolAgencyByProjectId(List<String> cdoes) {
        return null;
    }

	@Override
	public JSONArray getschoolAndChilds(List<UcenterAgency> ucenterAgencies) {return null;}

	@Override
	public JSONArray getAgencyByProjectId(List<String> codes) {return null;}

	@Override
    public List<String> getSchoolAgencyAndChilds(List<UcenterAgency> ucenterAgencies) {
        return null;
    }

	@Override
	public JSONArray getAgencyAndChilds(List<UcenterAgency> ucenterAgencies) {return null;}

	@Override
	public JSONArray getAgencyschoolByProjectId(List<String> codes) {
		return null;
	}

	@Override
	public JSONArray getAgencyschoolAndChilds(List<UcenterAgency> ucenterAgencies) {
		return null;
	}

	@Override
    public String getNextCodeByAgency(String areaType, String pagencyCode) {
        return null;
    }

    @Override
    public String getOrgTree() {
        return null;
    }
}
