package com.thinkjoy.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import static org.apache.http.protocol.HTTP.UTF_8;

/**
 * 模拟http get和post请求工具类
 */
public class HttpRequestUtil {

    //连接超时时间(单位毫秒)
    private static Integer HTTP_TIME_OUT = 2000;
    //读数据超时时间(单位毫秒)
    private static Integer HTTP_SO_TIME_OUT = 10000;


    /**
     * 其中部分特殊字符已经处理
     */
    public static String doGet(String linkUrl, Map<String, String> params) throws Exception {
        HttpURLConnection connection = null;
        StringBuffer urlApi = new StringBuffer(linkUrl);

        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (i == 0) {
                urlApi.append("?");
            } else {
                urlApi.append("&");
            }
            urlApi.append(entry.getKey() + "=" + entry.getValue());
            i++;
        }
        try {
            URL url = new URL(urlApi.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(HTTP_TIME_OUT);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(HTTP_SO_TIME_OUT);// 设置读取数据超时时间，单位毫秒
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            connection.disconnect();
        }
    }
//
    /**
     * 执行一个HTTP GET请求
     *
     * @param url
     * @return HTTP状态
     */
    public static void doHttpGet(String url) throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "text/html;charset=utf-8");
        HttpConnectionParams.setConnectionTimeout(client.getParams(), HTTP_TIME_OUT);
        HttpConnectionParams.setSoTimeout(client.getParams(), HTTP_SO_TIME_OUT);
        client.execute(httpGet);
    }

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    @SuppressWarnings("deprecation")
    public static String doHttpGet(String url, String queryString) throws Exception {
        String strResult = "";
        String uriAPI = url + "?" + queryString;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uriAPI);
        httpGet.addHeader("Content-type", "text/html;charset=utf-8");
        HttpResponse httpResponse = client.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            strResult = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
        }
        return strResult;
    }

    public static String doHttpGet(String url, Map<String, String> params) throws Exception {
        StringBuffer urlApi = new StringBuffer(url);

        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (i == 0) {
                urlApi.append("?");
            } else {
                urlApi.append("&");
            }
            urlApi.append(entry.getKey() + "=" + entry.getValue());
            i++;
        }
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urlApi.toString().replace(" ", "%20"));
        httpGet.addHeader("Content-type", "text/html;charset=utf-8");
        HttpResponse httpResponse = client.execute(httpGet);
        String strResult = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
        return strResult;
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url    请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     * @throws IOException
     * @throws IllegalStateException
     */
    @SuppressWarnings("deprecation")
    public static String doHttpPost(String url, Map<String, String> params)
            throws Exception {
        String strResult = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postData.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, UTF_8);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);

        System.out.println("状态吗：" + response.getStatusLine().getStatusCode());
        // 若状态码为200 ok
        if (response.getStatusLine().getStatusCode() == 200) {
            // 取出回应字串
            strResult = EntityUtils.toString(response.getEntity(), UTF_8);
        }
        return strResult;
    }

}
