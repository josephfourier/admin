package com.thinkjoy.ucenter.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseService;
import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;

import java.util.List;

/**
* UcenterAgencyService接口
* Created by xufei on 2017/7/26.
*/
public interface UcenterAgencyService extends BaseService<UcenterAgency, UcenterAgencyExample> {

    JSONArray getSchoolAgencyByProjectId(List<String> codes);

	JSONArray getschoolAndChilds(List<UcenterAgency> ucenterAgencies);

	JSONArray getAgencyByProjectId(List<String> codes);

	JSONArray getAgencyAndChilds(List<UcenterAgency> ucenterAgencies);

	JSONArray getAgencyschoolByProjectId(List<String> codes);

	List<String> getSchoolAgencyAndChilds(List<UcenterAgency> ucenterAgencies);

	JSONArray getAgencyschoolAndChilds(List<UcenterAgency> ucenterAgencies);

    String getNextCodeByAgency(String areaType,String pagencyCode);

    String getOrgTree();
}