package com.thinkjoy.common.xftpay;

import org.jdom.JDOMException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by jingqinghua on 17/12/25.
 */
public class XftPayUtil {

    /**
     * 向校付通订单接口提交订单
     * @throws IOException
     * @throws JDOMException
     * */
    public static void main(String[] args) throws JDOMException, IOException {
        String mch_id="1491962252";//校付通子商户号，由校付通提供
        String mch_key="55628678b07c41dda6f31c8e774db587";//校付通子商户号密钥，由校付通提供
        String order_url="http://ad.xft123.com/xftpay/gateway";//校付通申请订单接口网址，由校付通提供
        String pay_url="http://ad.xft123.com/xftpay/showpay";//校付通支付界面接口网址，由校付通提供

        StringBuilder id = new StringBuilder();
        id.append(System.currentTimeMillis());
        String orderId=id.toString();//校付通子商户订单号，第三方内部订单号要唯一
        String fee="1";//校付通子商户订单金额，单位为分，例：100代表1元钱，只接受大于0的金额，不能有小数点
        String body="账户：hxh充值:30元";//校付通子商户订单描述

        String xmlP=applyParXml(orderId,fee,body,mch_id,mch_key);
        String xmlR = HttpUtils.post(order_url, xmlP,HttpUtils.UTF8);
        System.out.println("申请结果"+xmlR);
        @SuppressWarnings("unchecked")
        Map<String, String> rm=XMLUtil.doXMLParse(xmlR);
        //验证返回结果是否合法
        if(XFTPayCommonUtil.isTenpaySign(rm, mch_key,HttpUtils.UTF8_NAME)){
            //生成支付链接
            SortedMap<Object,Object> rs = new TreeMap<Object,Object>();
            //获取统一下单返回结果中的token_id
            rs.put("token_id", rm.get("token_id"));
            //生成支付签名
            String sign = XFTPayCommonUtil.createSign("UTF-8",rs, mch_key);// 生成签名
            //拼装支付链接
            String getUrl=pay_url+"?token_id="+rm.get("token_id")+"&sign="+sign;

            System.out.println("支付链接:" + getUrl);
            //注意：此处输出校付通支付界面URL，可以直接java跳转或者前台 javascript跳转
            //也可以把该链接生成二维码图片，展示到前端给用户扫描
        }
    }
    /** 构建支付请求xml
     * @param  */
    private static String applyParXml(String orderId, String fee, String body, String mch_id, String mch_key) {
        String notify_url="http://121.40.129.213/WeiXinTest/xftNotice";//第三方应用异步调用URL，由第三方应用确定
        String callback_url="http://121.40.129.213/WeiXinTest/recharge.jsp";//第三方应用前台支付后返回URL，由第三方应用确定

        //发送给微信服务器的参数是xml格式的string，微信返回来的信息也是xml格式的string。
        SortedMap<Object,Object> pm = new TreeMap<Object,Object>();
        pm.put("service", "pay.weixin.jspay");// 接口类型
        pm.put("version", "1.0");// 版本号
        pm.put("charset", "UTF-8");// 字符编码
        pm.put("sign_type", "MD5");// 签名加密方式
        pm.put("mch_id", mch_id);// 村付通商户号
        pm.put("out_trade_no", orderId);// 客户内部订单号
        pm.put("body", body);// 商品描述
        pm.put("help_buy", "测试学校-校付通");// 代付识别，如果开启代付需传入相关参数
        pm.put("total_fee", fee);// 支付金额
        //pm.put("user_Id", "oskcxuB_I93PxNoXdYkajzg5OiU4");// open_id
        pm.put("notify_url", notify_url);// 回调地址（采用动力加统一订单系统，到接口完成）
        pm.put("callback_url", callback_url);// 支付完成跳转
        pm.put("nonce_str", XFTPayCommonUtil.CreateNoncestr());
        String sign = XFTPayCommonUtil.createSign("UTF-8",pm, mch_key);// 生成签名
        pm.put("sign", sign);
        return XFTPayCommonUtil.getRequestXml(pm);// xml格式参数文本
    }

    /**
     * @throws IOException
     * @throws
     *
     * */
    @SuppressWarnings("unused")
    private void xftNotice(HttpServletRequest request,HttpServletResponse response) throws JDOMException, IOException{
        String xmlN = HttpUtils.postData(request, null);// 获取微信提交的数据
        //String xmlN="<xml><notify_id>O4MlR08kZja24Ocs</notify_id><sign><![CDATA[BD428EC9E7C79FC3470C0BE9B5E10ACC]]></sign><total_fee>1</total_fee><trade_no>80000006071440916568024779</trade_no><trade_status>99</trade_status><trade_time>20150830143908</trade_time><trade_type>JSAPI</trade_type><transaction_id>1004611009201508300745976885</transaction_id><xft_payid>80000000011440916568200200</xft_payid></xml>";
        String enc = HttpUtils.UTF8_NAME;
        if (null != xmlN && !xmlN.isEmpty()) {
            //判断签名
            @SuppressWarnings("unchecked")
            Map<String, String> nm = XMLUtil.doXMLParse(xmlN);// 解析XML
            String mch_key="";//校付通子商户号密钥，由校付通提供
            if(XFTPayCommonUtil.isTenpaySign(nm,mch_key,enc)){
                String result_code = nm.get("trade_status");
                String trade_no = nm.get("trade_no");//商户订单编号
                String xft_payid = nm.get("xft_payid");//校付通支付
                String total_fee=nm.get("total_fee");//订单金额
                if("99".equals(result_code)){//支付完成
                    //此处为第三方应用处理支付完成后动作
                }
                HttpUtils.print(response,"Success");
            }else{
                HttpUtils.print(response,"通知签名验证失败");
            }
        }
    }
}
