package com.thinkjoy.common.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * json工具类
 * @author yonson
 */
public class JsonUtil {
	
	/**
	 * 对像转化为json字符串
	 * @param obj
	 * @return
	 */
	public static String tranObjectToJsonStr(Object obj){
		
		return JSONObject.toJSONString(obj);
		
	}
	
	/**
	 * json字符串转化为对像
	 * @param jsonStr
	 * @return 
	 * @return
	 */
	public static <T extends Object> T tranjsonStrToObject(String jsonStr,Class<T> clazz){
		
		return (T)JSONObject.parseObject(jsonStr,clazz);
		
	}
	
	/**
	 * json字符串转化为列表
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T extends Object> List<T> tranjsonStrToArray(String jsonStr,Class<T> clazz){
		
		return JSONObject.parseArray(jsonStr, clazz);
		
	}
	
	public static JSONObject tranjsonStrToJSONObject(String jsonStr){
		return JSONObject.parseObject(jsonStr);
	}
	

}
