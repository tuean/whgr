package config;

import netty.ServerConfig;

public class MethodProxyConfiguration {

    private ServerConfig serverConfig;






    public ServerConfig getConfig() {
        return serverConfig;
    }

    public void setConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
