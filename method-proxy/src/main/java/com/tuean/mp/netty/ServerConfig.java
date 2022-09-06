package com.tuean.mp.netty;

import com.tuean.mp.netty.serialize.AbstractSerializer;

public class ServerConfig {

    private Integer port;

    private Integer bossThreadNum;

    private String bossThreadName;

    private Integer workerThreadNum;

    private String workerThreadName;

    private Class<? extends AbstractSerializer> serializer;


    public void check() {

    }

    public ServerConfig() {
    }

    public ServerConfig(Integer port, Integer bossThreadNum, String bossThreadName, Integer workerThreadNum, String workerThreadName, Class<? extends AbstractSerializer> serializer) {
        this.port = port;
        this.bossThreadNum = bossThreadNum;
        this.bossThreadName = bossThreadName;
        this.workerThreadNum = workerThreadNum;
        this.workerThreadName = workerThreadName;
        this.serializer = serializer;
    }

    public Class<? extends AbstractSerializer> getSerializer() {
        return serializer;
    }

    public void setSerializer(Class<? extends AbstractSerializer> serializer) {
        this.serializer = serializer;
    }

    public Integer getBossThreadNum() {
        return bossThreadNum;
    }

    public void setBossThreadNum(Integer bossThreadNum) {
        this.bossThreadNum = bossThreadNum;
    }

    public String getBossThreadName() {
        return bossThreadName;
    }

    public void setBossThreadName(String bossThreadName) {
        this.bossThreadName = bossThreadName;
    }

    public Integer getWorkerThreadNum() {
        return workerThreadNum;
    }

    public void setWorkerThreadNum(Integer workerThreadNum) {
        this.workerThreadNum = workerThreadNum;
    }

    public String getWorkerThreadName() {
        return workerThreadName;
    }

    public void setWorkerThreadName(String workerThreadName) {
        this.workerThreadName = workerThreadName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
