package com.thinkjoy.zkconfig.context;


import com.thinkjoy.zkconfig.ILifecycle;
import com.thinkjoy.zkconfig.context.impl.CloudContextImpl;

/**
 * Created by xufei on 2017/8/31.
 */
public class CloudContextFactory {
    public CloudContextFactory() {
    }

    public static ICloudContext getCloudContext() {
        ((ILifecycle)CloudContextFactory.CloudContextHolder.instance).start();
        return CloudContextFactory.CloudContextHolder.instance;
    }

    private static class CloudContextHolder {
        private static final ICloudContext instance = new CloudContextImpl();

        private CloudContextHolder() {
        }
    }
}
