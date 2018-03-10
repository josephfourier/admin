package com.thinkjoy.zkconfig.context;

/**
 * Created by xufei on 2017/8/31.
 */
public interface ICloudContext {
    String getApplicationName();

    String getId();

    String getApplicationZhName();

    String getAppType();

    String getOwner();

    String getOwnerContact();

    String getDescription();

    int getPort();

    int getHttpPort();

    String getProduct();
}
