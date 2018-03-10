package com.thinkjoy.zkconfig.context.impl;

import com.google.common.base.Strings;
import com.thinkjoy.zkconfig.AbstractLifecycle;
import com.thinkjoy.zkconfig.context.ICloudContext;
import com.thinkjoy.zkconfig.context.domain.AppType;
import com.thinkjoy.zkconfig.utils.ConfigLoader;
import com.thinkjoy.zkconfig.utils.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by xufei on 2017/8/31.
 */
public class CloudContextImpl  extends AbstractLifecycle implements ICloudContext {
    public static final Logger logger = LoggerFactory.getLogger(CloudContextImpl.class);
    private volatile boolean isStart = false;
    public static final String DEPLOY_PATH_KEY = "deploypath";
    public static final String DEPLOY_FILE_KEY = "deployfile";
    public static final String APP_METADATA_FILE = "/config/metadata.properties";
    public static final String APP_METADATA_FILE_NOHUB = "config/metadata.properties";
    public static final String APP_NAME = "name";
    public static final String APP_ZHNAME = "zh_name";
    public static final String APP_TYPE = "type";
    public static final String APP_OWNER = "owner";
    public static final String APP_OWNERCONTACT = "ownercontact";
    public static final String APP_DESCRIPTION = "description";
    public static final String APP_PORT = "port";
    public static final String APP_HTTP_PORT = "http_port";
    public static final String PRODUCT = "product";
    private String applicationName;
    private String zhName;
    private AppType appType;
    private String owner;
    private String ownerContact;
    private String description;
    private int port;
    private int httpPort = 80;
    private String product;
    private String id;

    public CloudContextImpl() {
    }

    public static Long ipToLong(String ip) {
        String[] ips = ip.split("\\.");
        return Long.valueOf((Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]));
    }

    public void doStart() {
        String deploypath = ConfigLoader.getInstance().getProperty("deploypath");
        String deployfile = ConfigLoader.getInstance().getProperty("deployfile");
        String metaFile = null;
        boolean isFromClasspath = false;
        if(!Strings.isNullOrEmpty(deploypath)) {
            metaFile = deploypath + "/config/metadata.properties";
        } else if(!Strings.isNullOrEmpty(deployfile)) {
            metaFile = deployfile;
        } else {
            isFromClasspath = true;
        }

        Properties properties = new Properties();

        try {
            if(!isFromClasspath) {
                properties.load(new FileInputStream(new File(metaFile)));
            } else {
                InputStream e = this.getClass().getClassLoader().getResourceAsStream("/config/metadata.properties");
                if(e == null) {
                    e = this.getClass().getClassLoader().getResourceAsStream("config/metadata.properties");
                }

                InputStreamReader isr = new InputStreamReader(e, "UTF-8");
                properties.load(isr);
            }

            this.applicationName = properties.getProperty("name");
            this.id = ipToLong(NetUtil.getIpByHost(NetUtil.getLocalHost())).toString();
            this.appType = AppType.valueOf(properties.getProperty("type").toUpperCase());
            this.owner = properties.getProperty("owner");
            this.ownerContact = properties.getProperty("ownercontact");
            this.description = properties.getProperty("description");
            this.zhName = properties.getProperty("zh_name");
            if(!Strings.isNullOrEmpty(properties.getProperty("port"))) {
                this.port = Integer.valueOf(properties.getProperty("port")).intValue();
            }

            if(!Strings.isNullOrEmpty(properties.getProperty("http_port"))) {
                this.httpPort = Integer.valueOf(properties.getProperty("http_port")).intValue();
            }

            this.product = properties.getProperty("product");
        } catch (IOException var8) {
            logger.error("CloudContextImpl init error! load [{}] error", metaFile, var8);
            System.exit(-1);
        }

    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public String getId() {
        return this.id;
    }

    public String getApplicationZhName() {
        return this.zhName;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getAppType() {
        return this.appType.name();
    }

    public String getOwnerContact() {
        return this.ownerContact;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPort() {
        return this.port;
    }

    public int getHttpPort() {
        return this.httpPort;
    }

    public String getProduct() {
        return this.product;
    }

    public void stop() {
    }
}
