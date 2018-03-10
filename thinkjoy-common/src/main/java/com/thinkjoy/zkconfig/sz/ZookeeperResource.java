package com.thinkjoy.zkconfig.sz;

import com.google.common.collect.Maps;

import com.thinkjoy.common.util.PropertiesUtil;

import com.thinkjoy.zkconfig.context.CloudContextFactory;
import com.thinkjoy.zkconfig.zookeeper.ZKClient;
import com.thinkjoy.zkconfig.zookeeper.recover.ZKRecoverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.AbstractResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by xufei on 2017/8/31.
 */
public class ZookeeperResource  extends AbstractResource implements ApplicationContextAware, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(ZookeeperResource.class);
    public static final String URL_HEADER = "zk://";
    private static final String PATH_FORMAT = "/zjy-admin/%s/config";
    private String path = String.format(PATH_FORMAT, new Object[]{CloudContextFactory.getCloudContext().getApplicationName()});
    ConcurrentMap<String, Object> recoverDataCache = Maps.newConcurrentMap();
    AbstractApplicationContext ctx;

    public ZookeeperResource() {
    }

    public boolean exists() {
        try {
            return null != ZKClient.getClient().checkExists().forPath("");
        } catch (Exception var2) {
            log.error("Falied to detect the config in zoo keeper.", var2);
            return false;
        }
    }

    public boolean isOpen() {
        return false;
    }

    public URL getURL() throws IOException {
        return new URL(URL_HEADER + this.path);
    }

    public String getFilename() throws IllegalStateException {
        return this.path;
    }

    public String getDescription() {
        return "Zookeeper resouce at \'zk://" + this.path;
    }

    public InputStream getInputStream() throws IOException {
        Object data = null;

        byte[] data1;
        try {
            data1 = (byte[])ZKClient.getClient().getData().forPath(this.path);
        } catch (Exception var3) {
            data1 = ZKRecoverUtil.loadRecoverData(this.path);
        }
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(data1));

        PropertiesUtil.getInstance().setProperties(properties);
        ZKRecoverUtil.doRecover(data1, this.path, this.recoverDataCache);
        log.warn("init get startconfig data {}", new String(data1));
        return new ByteArrayInputStream(data1);
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = (AbstractApplicationContext)ctx;
    }

    public void destroy() throws Exception {
        log.info("Destory Zookeeper Resouce.");
    }
}
