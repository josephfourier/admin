package com.thinkjoy.ams.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wangcheng on 17/7/24.
 */
public class ThinkjoyAmsRpcServiceApplication {

    private static Logger _log = LoggerFactory.getLogger(ThinkjoyAmsRpcServiceApplication.class);

    public static void main(String[] args) {
        _log.info(">>>>>>thinkjoy-ams-rpc-service 正在启动rpc服务!");
        new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
        _log.info(">>>>>>thinkjoy-ams-rpc-service 启动rpc服务成功!");
    }
}
