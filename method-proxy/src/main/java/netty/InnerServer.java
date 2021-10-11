package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.spi.SelectorProvider;

public class InnerServer {

    private static final Logger logger = LoggerFactory.getLogger(InnerServer.class);

    public static void run(ServerConfig serverConfig) {
        serverConfig.check();
        EventLoopGroup bossGroup = new NioEventLoopGroup(serverConfig.getBossThreadNum(), new DefaultThreadFactory(serverConfig.getBossThreadName(), Thread.MAX_PRIORITY));
        EventLoopGroup workerGroup = new NioEventLoopGroup(serverConfig.getWorkerThreadNum(), new DefaultThreadFactory(serverConfig.getWorkerThreadName(), Thread.MAX_PRIORITY));
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channelFactory((ChannelFactory) () -> new NioServerSocketChannel(SelectorProvider.provider()))
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new MineServerInitializer());
            Integer port = serverConfig.getPort();
            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } catch (Exception var) {
            logger.error("", var);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
