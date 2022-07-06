package com.tuean.mp.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineInnerClientHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LoggerFactory.getLogger(MineInnerClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        logger.info("收到服务端的响应：" + msg);
    }

}
