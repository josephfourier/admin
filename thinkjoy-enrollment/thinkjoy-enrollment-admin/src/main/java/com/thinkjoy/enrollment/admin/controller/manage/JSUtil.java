package com.thinkjoy.enrollment.admin.controller.manage;

import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by jingqinghua on 17/12/1.
 */
public class JSUtil {



    public static void main(String[] args) throws IOException, SAXException
    {

        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {

            //参数说明===phantomjs安装路径 noticeJs:模板Js路径 templateUrl:模板路径  参数4:图片输出路径
            process = rt.exec("/Users/jingqinghua/Desktop/tools/phantomjs-2.1.1-macosx/bin/phantomjs /Users/jingqinghua/Desktop/tools/phantomjs-2.1.1-macosx/test.js http://localhost:8080/file/upload/template.jsp /Users/jingqinghua/Downloads/apache-tomcat-7.0.59/webapps/file/upload/123.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        try {
            while ((tmp = br.readLine()) != null && !tmp.contains("completed")) {
                sbf.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            process.destroy();
            is.close();
            System.out.println(" over !");
        }
    }



}
