package com.thinkjoy.zkconfig.zookeeper;

import org.apache.curator.framework.CuratorFramework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by xufei on 2017/8/31.
 */
public class ZKClientManager {

    private static ConcurrentMap<String, CuratorFramework> zkClientMap = new ConcurrentHashMap();

    private ZKClientManager() {
    }

    public static CuratorFramework getClient(String ip) {
        if(ip != null && ip.trim().length() != 0) {
            synchronized(ip) {
                if(!zkClientMap.containsKey(ip)) {
                    CuratorFramework client = ZKClient.create(ip);
                    CuratorFramework oldClient = (CuratorFramework)zkClientMap.putIfAbsent(ip, client);
                    if(oldClient != null) {
                        oldClient.close();
                    }

                    return client;
                }
            }

            return (CuratorFramework)zkClientMap.get(ip);
        } else {
            throw new IllegalArgumentException("zk ip not null!");
        }
    }
}
