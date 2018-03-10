package com.thinkjoy.zkconfig.zookeeper;


import com.thinkjoy.zkconfig.AbstractLifecycle;
import com.thinkjoy.zkconfig.utils.ConfigLoader;
import com.thinkjoy.zkconfig.utils.NetUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Created by xufei on 2017/8/31.
 */
public class ZKClient  extends AbstractLifecycle {
    public static final Logger logger = LoggerFactory.getLogger(ZKClient.class);
    public static final String DEFAULT_DOMAIN_NAME = "mc.zk.thinkjoy.cn";
    private static volatile CuratorFramework zkClient = null;

    private ZKClient() {
    }

    protected void doStart() {
        this.isStart = true;
        String ip = null;

//        try {
//            ip = NetUtil.getIpByDomain(DEFAULT_DOMAIN_NAME);
//        } catch (UnknownHostException var3) {
//            logger.error("getIpByDomain error!", var3);
//            System.exit(-1);
//        }

//        ip = ConfigLoader.getInstance().getProperty("zk.host");
//
//        String url = ip + ":" + ConfigLoader.getInstance().getProperty("zk.port");
        String url = ConfigLoader.getInstance().getProperty("zk.url");
        zkClient = CuratorFrameworkFactory.newClient(url, new ExponentialBackoffRetry(1000, 3));
        zkClient.start();
        logger.warn("ZKClient start success!");
    }

    public static CuratorFramework create(String ip) {
        logger.warn(" start conn zk server {} ", ip);
        CuratorFramework newClient = null;
        synchronized(ip) {
//            String url = ip + ":" + ConfigLoader.getInstance().getProperty("zk.port");
            String url = ConfigLoader.getInstance().getProperty("zk.url");
            newClient = CuratorFrameworkFactory.newClient(url, new ExponentialBackoffRetry(1000, 3));
            newClient.start();
        }

        logger.warn("  conn zk server {} success!", ip);
        return newClient;
    }

    public void stop() {
        if(zkClient != null) {
            zkClient.close();
        }

    }

    public static CuratorFramework getClient() {
        ZKClient.ZKClientHolder.instance.start();
        return zkClient;
    }

    private static class ZKClientHolder {
        private static final ZKClient instance = new ZKClient();

        private ZKClientHolder() {
        }
    }
}
