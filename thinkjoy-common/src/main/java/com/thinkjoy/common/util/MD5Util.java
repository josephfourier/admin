package com.thinkjoy.common.util;

import com.thinkjoy.common.xftpay.HttpUtils;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by  on 2017/1/19.
 */
public class MD5Util {

    public final static String MD5(String content) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = content.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    public final static String LowerCase_MD5(String content) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = content.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = MD5("366794f169d10145506c7afb84c2ceb9");
        System.out.println(s);

        String s1 = "{\"phone\":\"13487196007\",\"schoolKey\":\"4209820001\"}";
        byte[] encode = Base64Utils.encode(s1.getBytes("utf-8"));

        String s2 = new String(encode);
        System.out.println(s2);

        String s3 = "eyJhY2Nlc3NUb2tlbiI6IjQ2MzIwM2JjLWFlNTgtNGI5Yi05MWY5LThhMTYzMTI1ZjAwYiIsInJl\n" +
                "c3VsdENvZGUiOiIwIiwicmVzdWx0TXNnIjoi5pON5L2c5oiQ5YqfIiwic2Nob29sQ29kZSI6IjEy\n" +
                "MzQ1NiJ9";
        byte[] decode = Base64Utils.decode(s3.getBytes("utf-8"));

        String s4 = new String(decode);
        System.out.println(s4);


        Token token = new Token();


        token.setSecret("5EB05CFE71A51E514D2E4D5728BF80A2");
        token.setAccount("wxq_alzjy");
        token.setAuthType("THIRD_PART");

        String s5 = JsonUtil.tranObjectToJsonStr(token);
        System.out.println(s5);

        //s5 = "{\"account\":\"wxq_alzjy\",\"authType\":\"THIRD_PART\",\"secret\":\"5eb05cfe71a51e514d2e4d5728bf80a2\"}";

        System.out.println(s5);
        BASE64Encoder encoder = new BASE64Encoder();
        String encode1 = encoder.encodeBuffer(s5.getBytes("UTF-8"));
        System.out.println("encode: " + encode1);


        if("eyJhY2NvdW50Ijoid3hxX2Fsemp5IiwiYXV0aFR5cGUiOiJUSElSRF9QQVJUIiwic2VjcmV0IjoiNWViMDVjZmU3MWE1MWU1MTRkMmU0ZDU3MjhiZjgwYTIifQ==".equals("eyJhY2NvdW50Ijoid3hxX2Fsemp5IiwiYXV0aFR5cGUiOiJUSElSRF9QQVJUIiwic2VjcmV0IjoiNWViMDVjZmU3MWE1MWU1MTRkMmU0ZDU3MjhiZjgwYTIifQ==")){
            System.out.println("yes");
        }


//        String post = HttpUtils.post("http://dev.weixiao100.cn/external/token/get", encode1, null, "application/json;charset=utf-8");
//        System.out.println(post);
    }



}
