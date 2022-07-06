package com.tuean.mp.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InnerClient {

    private static Logger logger = LoggerFactory.getLogger(InnerClient.class);

    private final String host = "127.0.0.1";
    private final int port = 8888;

    public Channel InitChannel() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler())
                    .handler(new MineInnerClientInitializer());

            Channel channel = bootstrap.connect(host, port).sync().channel();
            return channel;
        } catch (Exception var) {
            logger.error("client error", var);
        }
        return null;
    }


}
