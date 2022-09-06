package com.tuean.mp.netty.server;

import com.tuean.mp.netty.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.spi.SelectorProvider;

public class InnerServer implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(InnerServer.class);

    private ServerConfig serverConfig;

    public InnerServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    ServerBootstrap b = new ServerBootstrap();
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    @Override
    public void run() {
        serverConfig.check();
        bossGroup = new NioEventLoopGroup(serverConfig.getBossThreadNum(), new DefaultThreadFactory(serverConfig.getBossThreadName(), Thread.MAX_PRIORITY));
        workerGroup = new NioEventLoopGroup(serverConfig.getWorkerThreadNum(), new DefaultThreadFactory(serverConfig.getWorkerThreadName(), Thread.MAX_PRIORITY));

        try {
            b.group(bossGroup, workerGroup)
                    .channelFactory((ChannelFactory) () -> new NioServerSocketChannel(SelectorProvider.provider()))
                    .option(ChannelOption.SO_BACKLOG, 20)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MineServerInitializer());
            Integer port = serverConfig.getPort();
            Channel ch = b.bind(port).sync().channel();
            logger.info("InnerServer started on port {}", port);
//            ch.closeFuture().sync();
        } catch (Exception var) {
            logger.error("", var);
        }
    }

    public void shutdown() throws Exception {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
