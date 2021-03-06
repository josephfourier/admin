package com.thinkjoy.zkconfig.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xufei on 2017/8/31.
 */
public abstract class ObjectPool<E> {
    private static final Logger logger = LoggerFactory.getLogger(ObjectPool.class);
    private ConcurrentLinkedQueue<E> pool;
    private ScheduledExecutorService executor;

    public ObjectPool(int minThread) {
        this.createObject(minThread);
    }

    public ObjectPool(final int minPoolSize, final int maxPoolSize, int interval) {
        this.createObject(minPoolSize);
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                int size = ObjectPool.this.pool.size();
                ObjectPool.logger.debug("Pool size:" + size);
                if(size < minPoolSize) {
                    ObjectPool.this.createObject(minPoolSize - size);
                } else if(size > maxPoolSize) {
                    ObjectPool.this.removeObject(size - maxPoolSize);
                }

            }
        }, (long)interval, (long)interval, TimeUnit.SECONDS);
    }

    public E borrowObject() {
        E poolObject = null;
        if((poolObject = this.pool.poll()) == null) {
            poolObject = this.createPoolObject();
        }

        logger.debug("Object borrowed from pool:" + poolObject.toString());
        return poolObject;
    }

    public void createObject(int tobeCreated) {
        if(this.pool == null) {
            this.pool = new ConcurrentLinkedQueue();
        }

        for(int i = 0; i < tobeCreated; ++i) {
            Object poolObject = this.createPoolObject();
            logger.debug("New object added to pool:" + poolObject.toString());
            this.pool.add(this.createPoolObject());
        }

    }

    public abstract E createPoolObject();

    public void removeObject(int toBeRemoved) {
        for(int i = 0; i < toBeRemoved; ++i) {
            Object poolObject = this.pool.poll();
            if(poolObject.getClass().isAssignableFrom(IDestroyable.class)) {
                ((IDestroyable)poolObject).destroy();
            }

            logger.debug("Object removed from pool:" + poolObject.toString());
        }

    }

    public void returnObject(E poolObject) {
        if(poolObject != null) {
            this.pool.offer(poolObject);
            logger.debug("Object returned to pool:" + poolObject.toString());
        }
    }

    public void terminate() {
        if(this.executor != null) {
            this.executor.shutdown();
        }

        if(this.pool != null) {
            this.removeObject(this.pool.size());
        }

    }
}
