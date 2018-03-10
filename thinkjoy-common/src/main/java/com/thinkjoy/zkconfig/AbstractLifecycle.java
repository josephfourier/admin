package com.thinkjoy.zkconfig;

/**
 * Created by xufei on 2017/8/31.
 */
public abstract class AbstractLifecycle implements ILifecycle {
    protected volatile boolean isStart = false;

    public AbstractLifecycle() {
    }

    public void start() {
        if (!this.isStart) {
            this.doStart();
            this.isStart = true;
        }

    }

    public boolean isStarted() {
        return this.isStart;
    }

    protected abstract void doStart();
}
