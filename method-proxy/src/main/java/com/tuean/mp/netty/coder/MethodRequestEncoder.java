package com.tuean.mp.netty.coder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuean.mp.entity.MethodRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MethodRequestEncoder extends MessageToByteEncoder<MethodRequest> {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MethodRequest methodRequest, ByteBuf byteBuf) throws Exception {
        byte[] req = gson.toJson(methodRequest).getBytes();
//        byteBuf.writeInt(req.length);
        byteBuf.writeBytes(req);
    }
}
