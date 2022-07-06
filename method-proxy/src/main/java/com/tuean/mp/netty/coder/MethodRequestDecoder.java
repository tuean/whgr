package com.tuean.mp.netty.coder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuean.mp.entity.MethodRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MethodRequestDecoder extends ByteToMessageDecoder {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        InputStream byteBufInputStream = new ByteBufInputStream(byteBuf);
        Reader reader = new InputStreamReader(byteBufInputStream);
        MethodRequest methodRequest = gson.fromJson(reader, MethodRequest.class);
        list.add(methodRequest);
    }
}
