package com.thinkjoy.web.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wangcheng on 17/8/23.
 */
public class ThinkjoyWebRpcServiceApplication {

    private static Logger _log = LoggerFactory.getLogger(ThinkjoyWebRpcServiceApplication.class);

    public static void main(String[] args) {
        _log.info(">>>>> thinkjoy-web-rpc-service 正在启动 <<<<<");
        new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
        _log.info(">>>>> thinkjoy-web-rpc-service 启动完成 <<<<<");
    }
}
