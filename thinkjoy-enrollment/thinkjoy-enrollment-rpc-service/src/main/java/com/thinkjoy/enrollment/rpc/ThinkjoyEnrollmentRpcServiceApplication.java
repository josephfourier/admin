package com.thinkjoy.enrollment.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务启动类
 * Created by gengsongbo on 2017/11/2.
 */
class ThinkjoyEnrollmentRpcServiceApplication {

	private static Logger _log = LoggerFactory.getLogger(ThinkjoyEnrollmentRpcServiceApplication.class);

	public static void main(String[] args) {
		_log.info(">>>>> thinkjoy-enrollment-rpc-service 正在启动 <<<<<");
		new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
		_log.info(">>>>> thinkjoy-enrollment-rpc-service 启动完成 <<<<<");
	}

}
