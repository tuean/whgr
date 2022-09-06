package com.tuean.mp.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.tuean.mp.netty.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.tuean.mp.config.MethodProxyServerConfiguration.DEFAULT_PORT;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static ServerConfig getByEnv(Environment env) {
        ServerConfig c = new ServerConfig();
        c.setPort(Integer.valueOf(env.getProperty("mp.server.port", String.valueOf(DEFAULT_PORT))));
        c.setBossThreadName(env.getProperty("mp.server.thread.boss.name", "mp.boss"));
        c.setBossThreadNum(Integer.valueOf(env.getProperty("mp.server.thread.boss.num", "2")));
        c.setWorkerThreadName(env.getProperty("mp.server.thread.worker.name", "mp.thread"));
        c.setWorkerThreadNum(Integer.valueOf(env.getProperty("mp.server.thread.worker.num", "2")));
        return c;
    }

    public static ClassPathScanningCandidateComponentProvider getScanner(Environment env) {
        return new ClassPathScanningCandidateComponentProvider(false, env) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isInterface()) {
                    return !beanDefinition.getMetadata().isAnnotation();
                }
                return false;
            }
        };
    }


    public static void closeOut(OutputStream out) {
        if (out == null) return;
        try {
            out.close();
        } catch (IOException e) {
            logger.error("Exception while closing", e);
        }
    }

    public static void closeIn(InputStream in) {
        if (in == null) return;
        try {
            in.close();
        } catch (IOException e) {
            logger.error("Exception while closing", e);
        }
    }

    public static void closeHessianOut(Hessian2Output out) {
        if (out == null) return;
        try {
            out.close();
        } catch (IOException e) {
            logger.error("Exception while closing", e);
        }
    }

    public static void closeHessianIut(Hessian2Input in) {
        if (in == null) return;
        try {
            in.close();
        } catch (IOException e) {
            logger.error("Exception while closing", e);
        }
    }

    public static void closeKryoOut(Output out) {
        if (out == null) return;
        out.close();
    }

    public static void closeKryoInput(Input in) {
        if (in == null) return;
        in.close();
    }

}
