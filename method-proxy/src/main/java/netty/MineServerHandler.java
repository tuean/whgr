package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

public class MineServerHandler extends SimpleChannelInboundHandler<Object> {

    private String uri;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) o;
            uri = request.uri().substring(1);
        }

        if (o instanceof HttpContent) {

        }
    }
}
