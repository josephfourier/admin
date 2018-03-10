package com.thinkjoy.zkconfig;

/**
 * Created by xufei on 2017/8/31.
 */
public interface ILifecycle {
    void start();

    void stop();

    boolean isStarted();
}
