package com.thinkjoy.ucenter.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkjoy.common.annotation.BaseService;
import com.thinkjoy.common.base.BaseConstants;
import com.thinkjoy.common.base.BaseServiceImpl;
import com.thinkjoy.ucenter.common.constant.UcenterConstant;
import com.thinkjoy.ucenter.dao.mapper.UcenterAgencyMapper;
import com.thinkjoy.ucenter.dao.mapper.UcenterSchoolMapper;
import com.thinkjoy.ucenter.dao.model.UcenterAgency;
import com.thinkjoy.ucenter.dao.model.UcenterAgencyExample;
import com.thinkjoy.ucenter.dao.model.UcenterSchool;
import com.thinkjoy.ucenter.dao.model.UcenterSchoolExample;
import com.thinkjoy.ucenter.rpc.api.UcenterAgencyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * UcenterAgencyService实现
 * Created by wangcheng on 2017/7/26.
 */
@Service
@Transactional
@BaseService
public class UcenterAgencyServiceImpl extends BaseServiceImpl<UcenterAgencyMapper, UcenterAgency, UcenterAgencyExample> implements UcenterAgencyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterAgencyServiceImpl.class);

    @Autowired
    UcenterAgencyMapper ucenterAgencyMapper;

    @Autowired
    UcenterSchoolMapper ucenterSchoolMapper;


    //根据区域类型查询组织机构
    public List<UcenterAgency> getUcAgenciesByAreaType(String areaType){
        UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
        ucenterAgencyExample.createCriteria().andAreaTypeEqualTo(areaType);
        List<UcenterAgency> ucenterAgencies = ucenterAgencyMapper.selectByExample(ucenterAgencyExample);
        return ucenterAgencies;
    }

    //根据组织代码查询组织下所属学校
    public List<UcenterSchool> getUcSchoolsByAgencyCode(UcenterAgency ua){
        UcenterSchoolExample ucenterSchoolExample = new UcenterSchoolExample();
        ucenterSchoolExample.createCriteria().andAgencyCodeEqualTo(ua.getAgencyCode());
        List<UcenterSchool> ucenterSchools = ucenterSchoolMapper.selectByExample(ucenterSchoolExample);

        return ucenterSchools;
    }

    //根据上级组织机构编码查询组织机构
    public List<UcenterAgency> getUcAgenciesByPagencyCode(UcenterAgency ua){
        UcenterAgencyExample ucenterAgencyExample = new UcenterAgencyExample();
        ucenterAgencyExample.createCriteria().andPagencyCodeEqualTo(ua.getAgencyCode());
        List<UcenterAgency> ucenterAgencies = ucenterAgencyMapper.selectByExample(ucenterAgencyExample);

        return ucenterAgencies;
    }

    /**
     * 递归添加所有层级的直属学校和组织
     * @param ja json串每层封装的数组
     * @param ucenterAgencies 对应层次的组织
     * @param codes 项目已有的组织的编码
     */
    public void addAgencyAndSchoolNode(JSONArray ja, List<UcenterAgency> ucenterAgencies, List<String> codes){

        if (CollectionUtils.isEmpty(ucenterAgencies)){
            return;
        }

        for (UcenterAgency ua : ucenterAgencies) {

            if(!ua.getStatus().equals(BaseConstants.Status.NORMAL)){
                continue;
            }


            JSONObject node = new JSONObject();
            node.put("code", ua.getAgencyCode());
            node.put("name", ua.getAgencyName());
            //标记此节点为机构类型
            node.put("type", "1");
            //node.put("nocheck", true);
            node.put("open", false);
            for (String code : codes){
                if (ua.getAgencyCode().equals(code)){
                    node.put("checked", true);
                }
            }

            //组织机构添加
            ja.add(node);

            JSONArray childs = new JSONArray();

            List<UcenterSchool> ucenterSchools = getUcSchoolsByAgencyCode(ua);

            for (UcenterSchool us : ucenterSchools){
                if(!us.getStatus().equals(BaseConstants.Status.NORMAL)){
                    continue;
                }

                JSONObject node1 = new JSONObject();
                node1.put("code", us.getSchoolCode());
                node1.put("name", us.getSchoolName());
                //标记此节点为学校类型
                node1.put("type", "2");
                //node.put("nocheck", true);
                node1.put("open", false);
                for (String code : codes){
                    if (us.getSchoolCode().equals(code)){
                        node1.put("checked", true);
                    }
                }
                childs.add(node1);
            }
            //组织机构的直属学校添加
            ((JSONObject)ja.get(ja.size()-1)).put("children", childs);


            // 转折点,当组织机构的区域类型为QU_XIAN时,说明区县级组织的添加已经完成
            //,不再往下递归,这里用contitue是因为当有多个区县级的组织时,保证其他都能被处理,所以没直接return
            // 若后期还有更多层次,修改这里AreaType即可
            if (ua.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
                continue;
            }
            //递归开始
            List<UcenterAgency> ucenterAgencies2 = getUcAgenciesByPagencyCode(ua);
            addAgencyAndSchoolNode(childs, ucenterAgencies2, codes);

        }

        return;
    }

    /**
     * 递归添加所有层级的组织机构
     * @param ja json串每层封装的数组
     * @param ucenterAgencies 对应层次的组织
     * @param codes 项目已有的组织的编码
     */
    public void addAgencyNode(JSONArray ja, List<UcenterAgency> ucenterAgencies, List<String> codes){

        if (CollectionUtils.isEmpty(ucenterAgencies)){
            return;
        }
        for (UcenterAgency ua : ucenterAgencies) {

            if(!ua.getStatus().equals(BaseConstants.Status.NORMAL)){
                continue;
            }

            JSONObject node = new JSONObject();
            node.put("code", ua.getAgencyCode());
            node.put("name", ua.getAgencyName());
            //标记此节点为机构类型
            node.put("type", "1");
            //node.put("nocheck", true);
            node.put("open", false);
            for (String code : codes){
                if (ua.getAgencyCode().equals(code)){
                    node.put("checked", true);
                }
            }

            //组织机构添加
            ja.add(node);

            JSONArray childs = new JSONArray();
/*
			List<UcenterSchool> ucenterSchools = getUcSchoolsByAgencyCode(ua);

			for (UcenterSchool us : ucenterSchools){
				JSONObject node1 = new JSONObject();
				node1.put("code", us.getSchoolCode());
				node1.put("name", us.getSchoolName());
				//标记此节点为学校类型
				node1.put("type", "2");
				//node.put("nocheck", true);
				node1.put("open", false);
				for (String code : codes){
					if (us.getSchoolCode().equals(code)){
						node1.put("checked", true);
					}
				}
				childs.add(node1);
			}
			*/
            //组织机构的直属学校添加
            ((JSONObject)ja.get(ja.size()-1)).put("children", childs);

            // 转折点,当组织机构的区域类型为QU_XIAN时,说明区县级组织的添加已经完成
            //,不再往下递归,这里用contitue是因为当有多个区县级的组织时,保证其他都能被处理,所以没直接return
            // 若后期还有更多层次,修改这里AreaType即可
            if (ua.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
                continue;
            }
            //递归开始
            List<UcenterAgency> ucenterAgencies2 = getUcAgenciesByPagencyCode(ua);
            addAgencyNode(childs, ucenterAgencies2, codes);
        }
        return;
    }

    /**
     * 递归添加所有层级的组织学校机构
     * @param ja json串每层封装的数组
     * @param ucenterAgencies 对应层次的组织
     * @param codes 项目已有的组织学校机构的编码
     */
    public void addAgencyschoolNode(JSONArray ja, List<UcenterAgency> ucenterAgencies, List<String> codes){

        if (CollectionUtils.isEmpty(ucenterAgencies)){
            return;
        }
        for (UcenterAgency ua : ucenterAgencies) {

            JSONObject node = new JSONObject();
            node.put("code", ua.getAgencyCode());
            node.put("name", ua.getAgencyName());
            //标记此节点为机构类型
            node.put("type", "1");
            //node.put("nocheck", true);
            node.put("open", false);
            for (String code : codes){
                if (ua.getAgencyCode().equals(code)){
                    node.put("checked", true);
                }
            }

            //组织机构添加
            ja.add(node);

            JSONArray childs = new JSONArray();

            List<UcenterSchool> ucenterSchools = getUcSchoolsByAgencyCode(ua);
            for (UcenterSchool us : ucenterSchools){
                JSONObject node1 = new JSONObject();
                node1.put("code", us.getSchoolCode());
                node1.put("name", us.getSchoolName());
                //标记此节点为学校类型
                node1.put("type", "2");
                //node.put("nocheck", true);
                node1.put("open", false);
                for (String code : codes){
                    if (us.getSchoolCode().equals(code)){
                        node1.put("checked", true);
                    }
                }
                childs.add(node1);
            }
            //组织机构的直属学校添加
            ((JSONObject)ja.get(ja.size()-1)).put("children", childs);

            // 转折点,当组织机构的区域类型为QU_XIAN时,说明区县级组织的添加已经完成
            //,不再往下递归,这里用contitue是因为当有多个区县级的组织时,保证其他都能被处理,所以没直接return
            // 若后期还有更多层次,修改这里AreaType即可
            if (ua.getAreaType().equals(UcenterConstant.AreaType.QU_XIAN)){
                continue;
            }
            //递归开始
            List<UcenterAgency> ucenterAgencies2 = getUcAgenciesByPagencyCode(ua);
            addAgencyschoolNode(childs, ucenterAgencies2, codes);
        }
        return;
    }



    /**
     *
     * @param
     * @param codes 已关联的组织机构code
     * @return
     */
    @Override
    public JSONArray getSchoolAgencyByProjectId(List<String> codes){

        JSONArray agencys = new JSONArray();

        //①查询所有省级的组织
        List<UcenterAgency> ucenterAgencies1 = getUcAgenciesByAreaType(UcenterConstant.AreaType.SHENG);

        //②递归添加所有层级的直属学校和组织
        addAgencyAndSchoolNode(agencys, ucenterAgencies1, codes);

        return agencys;
    }

    /**
     *
     * @param
     * @param codes 已关联的组织机构code
     * @return
     */
    @Override
    public JSONArray getAgencyByProjectId(List<String> codes) {
        JSONArray agencys = new JSONArray();
        //①查询所有省级的组织
        List<UcenterAgency> ucenterAgencies1=getUcAgenciesByAreaType(UcenterConstant.AreaType.SHENG);

        //②递归添加所有层级的直属学校和组织
        addAgencyNode(agencys, ucenterAgencies1, codes);

        return agencys;
    }
    /**
     *
     * @param
     * @param codes 已关联的组织机构学校code
     * @return
     */
    @Override
    public JSONArray getAgencyschoolByProjectId(List<String> codes) {
        JSONArray agencys = new JSONArray();
        //①查询所有省级的组织
        List<UcenterAgency> ucenterAgencies1=getUcAgenciesByAreaType(UcenterConstant.AreaType.SHENG);

        //②递归添加所有层级的直属学校和组织
        addAgencyschoolNode(agencys, ucenterAgencies1, codes);

        return agencys;
    }

    /**
     * 获取当前登录管理员所属组织下的子组织和学校
     * @param ucenterAgencies 当前登录管理员所属的组织
     * @return
     */
    @Override
    public List<String> getSchoolAgencyAndChilds(List<UcenterAgency> ucenterAgencies){
        JSONArray agencys = new JSONArray();
        addAgencyAndSchoolNode(agencys, ucenterAgencies, Collections.EMPTY_LIST);
        List<String> strings = new ArrayList<>();
        recursionJsonArray(agencys, strings);
        return strings;
    }


    @Override
    //组织机构树
    public JSONArray getAgencyAndChilds(List<UcenterAgency> ucenterAgencies){
        JSONArray agencys = new JSONArray();
        addAgencyNode(agencys, ucenterAgencies, Collections.EMPTY_LIST);
//		List<String> strings = new ArrayList<>();
//		recursionJsonArray(agencys, strings);
        return agencys;
    }

    @Override
    public JSONArray getschoolAndChilds(List<UcenterAgency> ucenterAgencies){
        JSONArray agencys = new JSONArray();
        addAgencyschoolNode(agencys, ucenterAgencies, Collections.EMPTY_LIST);
//		List<String> strings = new ArrayList<>();
//		recursionJsonArray(agencys, strings);
        return agencys;
    }

    @Override
    //(组织学校机构)
    public JSONArray getAgencyschoolAndChilds(List<UcenterAgency> ucenterAgencies){
        JSONArray agencys = new JSONArray();
        addAgencyschoolNode(agencys, ucenterAgencies, Collections.EMPTY_LIST);
//		List<String> strings = new ArrayList<>();
//		recursionJsonArray(agencys, strings);
        return agencys;
    }

    //递归获取组织和学校code
    public List<String> recursionJsonArray(JSONArray jsonArray, List<String> codes){
        Iterator<Object> iterator = jsonArray.iterator();

        while (iterator.hasNext()){
            JSONObject jsonObject = (JSONObject)iterator.next();
            String code = jsonObject.getString("code");
            codes.add(code);
            if (jsonObject.getJSONArray("children") != null){
                recursionJsonArray(jsonObject.getJSONArray("children"), codes);
            }
        }

        return codes;
    }



    @Override
    public String getOrgTree() {


        UcenterAgencyExample example=new UcenterAgencyExample();
        List<UcenterAgency> list= ucenterAgencyMapper.selectByExample(example);
        StringBuffer json=new StringBuffer("[");
        String data="";
        for (int i = 0; i < list.size(); i++) {
            json.append("{id:"+list.get(i).getAgencyCode()+",");
            json.append("pId:"+list.get(i).getPagencyCode()+",");
            json.append("name:\""+list.get(i).getAgencyName()+"\",");
            json.append("open:false,");

            data=json.substring(0,json.lastIndexOf(","))+"},";
            json=new StringBuffer(data);
        }
        data=json.substring(0, json.length()-1)+"]";
        return data;
    }

    @Override
    public String getNextCodeByAgency(String areaType, String pagencyCode) {
        if(areaType.equals(UcenterConstant.AreaType.SHENG)){
            Map map=new HashMap<String,Object>();
            map.put("areaType", areaType);
            String agencyCode=ucenterAgencyMapper.selectMaxCodeByMap(map);
            if (null == agencyCode || agencyCode == "0") {
                return "110000";
            }
            return String.valueOf((Integer.parseInt(agencyCode) / 10000 + 1) * 10000);
        }else if(areaType.equals(UcenterConstant.AreaType.SHI)){
            Map map=new HashMap<String,Object>();
            map.put("areaType", areaType);
            map.put("pagencyCode",pagencyCode);
            String agencyCode=ucenterAgencyMapper.selectMaxCodeByMap(map);
            if (null == agencyCode || agencyCode == "0") {
                return String.valueOf((Integer.parseInt(pagencyCode) / 100 + 1) * 100);
            }
            return String.valueOf((Integer.parseInt(agencyCode) / 100 + 1) * 100);
        }else if(areaType.equals(UcenterConstant.AreaType.QU_XIAN)){
            Map map=new HashMap<String,Object>();
            map.put("areaType", areaType);
            map.put("pagencyCode",pagencyCode);
            String agencyCode=ucenterAgencyMapper.selectMaxCodeByMap(map);
            if (null == agencyCode || agencyCode == "0") {
                return String.valueOf(Integer.parseInt(pagencyCode) + 1 );
            }
            return String.valueOf(Integer.parseInt(agencyCode) + 1);
        }
        return null;
    }

}