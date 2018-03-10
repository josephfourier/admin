package com.thinkjoy.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * base64工具类
 * @author yonson
 */
public class BASE64Util {
    private final static Logger _log = LoggerFactory.getLogger(BASE64Util.class);

	/**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("restriction")
	public static byte[] decryptBASE64(String key) throws Exception {

    	if(key != null){
    		key = key.replace(" ", "");
    		return (new BASE64Decoder()).decodeBuffer(key);
    	}else{
    		return null;
    	}

    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * BASE64加密 红包活动
     * @param object
     * @return
     */
    public static String encryptBase64(Object object) {
        String str = "";
        try {
            String json = JsonUtil.tranObjectToJsonStr(object).trim();
            System.out.println("处理的json,"+json);
            str = Base64.encodeBase64String(json.getBytes());
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return str;
    }

    public static <T extends Object> T  decryptBASE64(String str,Class<T> clazz){

    	try{
    		byte[] byteArr = Base64.decodeBase64(str);
    		String jsonStr = URLDecoder.decode(new String(byteArr), "UTF-8");
    		T t = JsonUtil.tranjsonStrToObject(jsonStr, clazz);
    		return t;
    	}catch(Exception e){
            _log.error(e.getMessage());
    	}
    	return null;

    }


    public static void main(String[] args) throws Exception
    {
//        String data = BASE64Util.encryptBASE64("http://115.28.171.4/notice/test/aaa".getBytes());
//        System.out.println("加密前："+data);
//
//        byte[] byteArray = BASE64Util.decryptBASE64(data);
//        System.out.println("解密后："+new String(byteArray));


//    	HashMap<String, Object> map = new HashMap<>();
//        map.put("userId", "0000");
//        map.put("cropId", "0000");
//        System.out.println(getBase64(map));
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("userId", "5b42587e61be11e58635fa163e0e90d3");
//        map.put("cropId", "wx9e8af8ca5ea7c2c3");
//
//        String code = encryptBase64(map);
//        System.out.println(code);
//
//        Test hashMap = BASE64Util.decryptBASE64(code,Test.class);
//        System.out.println(hashMap.getCropId());
//        System.out.println(hashMap.getUserId());

//    	HashMap<String, Object> map = new HashMap<>();
//        map.put("followedAdd", 10);
//        map.put("schoolCode", "zscy001");
//        map.put("userId", "966e6dedef3211e58cc6fa163e37f8a9");
//        map.put("currentMembers", 14);
//        map.put("reward", 1);
//        map.put("hcode", "100100101");
//        map.put("cropId", "wx29a48dfc48fb3877");
//
//        System.out.println(encryptBase64(map));

//    	System.out.println(BASE64Util.encryptBASE64("https://dev.weixiao100.cn/frontstest/act/qkyzhxy/index.html".getBytes()));
//    	System.out.println(new String(BASE64Util.decryptBASE64("aHR0cHM6Ly9kZXYud2VpeGlhbzEwMC5jbi9mcm9udHN0ZXN0L2FjdC9xa3l6aHh5L2luZGV4Lmh0bWw="),"UTF-8"));

//    	System.out.println(BASE64Util.encryptBASE64("https://www.weixiao100.cn/portal/wap/qkyzhxy/jump".getBytes()));
//    	System.out.println(new String(BASE64Util.decryptBASE64("aHR0cHM6Ly9kZXYud2VpeGlhbzEwMC5jbi9mcm9udHN0ZXN0L2FjdC9xa3l6aHh5L2hiZm9ybS5odG1s"),"UTF-8"));

    	System.out.println(BASE64Util.encryptBASE64("#class".getBytes()));  //I2NsYXNz
    	System.out.println(new String(BASE64Util.decryptBASE64("I2NsYXNz"),"UTF-8"));

    }

}
