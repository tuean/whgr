package com.tuean.mp.util;

import com.tuean.mp.netty.ServerConfig;
import org.springframework.core.env.Environment;

import static com.tuean.mp.config.MethodProxyServerConfiguration.DEFAULT_PORT;

public class Util {

    public static ServerConfig getByEnv(Environment env) {
        ServerConfig c = new ServerConfig();
        c.setPort(Integer.valueOf(env.getProperty("mp.server.port", String.valueOf(DEFAULT_PORT))));
        c.setBossThreadName(env.getProperty("mp.server.thread.boss.name", "mp.boss"));
        c.setBossThreadNum(Integer.valueOf(env.getProperty("mp.server.thread.boss.num", "2")));
        c.setWorkerThreadName(env.getProperty("mp.server.thread.worker.name", "mp.thread"));
        c.setWorkerThreadNum(Integer.valueOf(env.getProperty("mp.server.thread.worker.num", "2")));
        return c;
    }

}
