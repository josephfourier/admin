package com.thinkjoy.common.xftpay;

import java.util.*;


public class XFTPayCommonUtil {
	
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	/**
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @param mchKey 加密密钥
	 * @return
	 */
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters, String mchKey){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + mchKey);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg  返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param params  请求参数
	 * @return
	 */
	public static String getRequestXml(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}		
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @param key 
	 * @param nm 
	 * @param enc 
	 * @return boolean
	 */
	public static boolean isTenpaySign(Map<String, String> nm, String key, String enc) {
		StringBuffer sb = new StringBuffer();		
		sb.append(toParams(nm));
		sb.append("&key=" + key);	
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();		
		String tenpaySign = nm.get("sign");	
		return tenpaySign.equals(sign);
	}

	/**
	 * 将Map转换为参数格式(key=value&...)
	 * 
	 * @param map
	 *            Map数据
	 * @return key=value&...字符串
	 */
	public static String toParams(Map<String, String> map) {
		List<String> keys = sort(map.keySet());
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			String v=map.get(key);
			if(!"sign".equals(key) && null != v && !"".equals(v)) {
				sb.append(key);
				sb.append("=");
				sb.append(v);
				sb.append("&");
			}			
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 根据ASCII码排序
	 * 
	 * @param set
	 *            Set集合
	 * @return List集合
	 */
	public static List<String> sort(Set<String> set) {
		List<String> keys = new ArrayList<String>(set);
		Collections.sort(keys, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);// 根据ASCII码排序KEY
			};
		});
		return keys;
	}
	
	
}
