package com.thinkjoy.common.smssend;

import com.thinkjoy.common.util.HttpRequestUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jingqinghua on 17/12/25.
 */
public class SmsSend {



    public static void sms_send(String mobile,String content ) throws ParserConfigurationException, SAXException, IOException {
        String MOBILEURL = "http://121.40.78.35:8080/yxthttp/sms/sendUTF8";

        Map<String ,String> map=new HashMap<String,String>();
        map.put("action","send");
        map.put("userid","11146");
        map.put("account","xaxyhy");
        map.put("password","77iy2x");
        map.put("mobile",mobile);
        map.put("content",content);
        try {
            String  result= HttpRequestUtil.doHttpPost(MOBILEURL,map);
            System.out.println(result);

            Document doc = null;
            try {
                doc = DocumentHelper.parseText(result);
                Element rootElt = doc.getRootElement(); // 获取根节点
                System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
                List<Element> list = rootElt.elements();

                Iterator<Element> iterator = list.iterator();
                while(iterator.hasNext()){
                    Element elem=iterator.next();
                    System.out.println(elem.getName()+":"+elem.getText());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


//        String mobile="18629638225,18292070290";
//        String content="安陆中等职业技术学院,XXX同学你好,你报考的专业我校已录取,请您于2017年9月1号来我校报道!";
//
//        SmsSend send=new SmsSend();
//        try {
//            send.sms_send(mobile,content);
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//


    }
}
