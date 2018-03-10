package com.thinkjoy.web.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.thinkjoy.common.base.BaseController;
import com.thinkjoy.web.common.constant.WebResult;
import com.thinkjoy.web.common.constant.WebResultConstant;
import com.thinkjoy.web.dao.model.WebUserAppcollections;
import com.thinkjoy.web.rpc.api.WebUserAppcollectionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangcheng on 17/8/24.
 */
@RequestMapping("/manage/userAppCollections")
@Controller
@Api(value = "我的应用收藏", description = "我的应用收藏")
public class WebUserAppCollectionsController extends BaseController {


    @Autowired
    private WebUserAppcollectionsService userAppcollectionsService;

    @ApiOperation(value = "更新应用收藏")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateUserCollectedApps(HttpServletRequest request){

        JSONArray addIds = JSONArray.parseArray(request.getParameter("addIds"));
        //JSONArray removeIds = JSONArray.parseArray(request.getParameter("removeIds"));

        List<String> lAddIds = null;
        if (addIds != null || !addIds.isEmpty()){
            lAddIds= addIds.toJavaList(String.class);
        }else {
            return new WebResult(WebResultConstant.EMPTY_COLLECTION_APPID, "请选择要收藏的应用!");
        }

        //List<String> lRemoveIds = removeIds.toJavaList(String.class);

        String userId = request.getParameter("userId");
        String usertypeId = request.getParameter("usertypeId");
        String relationCode = request.getParameter("relationCode");

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(usertypeId) || StringUtils.isBlank(relationCode)){
            return new WebResult(WebResultConstant.INVALID_USER_IDENTITY, "用户身份参数不全");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("usertypeId", usertypeId);
        map.put("relationCode", relationCode);

        //①先根据用户身份删除该身份下已收藏的应用
        int count = userAppcollectionsService.batchDeleteByPrimarykey(map);

        //②封装批量更新数据
        List<WebUserAppcollections> addCollections = new ArrayList<>();
        for (String laid : lAddIds){
            WebUserAppcollections webUserAppcollections = new WebUserAppcollections();
            webUserAppcollections.setUserId(Integer.parseInt(userId));
            webUserAppcollections.setUsertypeId(Integer.parseInt(usertypeId));
            webUserAppcollections.setRelationCode(relationCode);
            webUserAppcollections.setAppId(Integer.parseInt(laid));
            addCollections.add(webUserAppcollections);
        }

        //③再批量更新
        int i = userAppcollectionsService.batchInsert(addCollections);

        return new WebResult(WebResultConstant.SUCCESS, "收藏"+ i +"个应用成功!");
    }

}
