package fendoudebb.fx.tool.netty.server;

import fendoudebb.fx.tool.netty.protocol.MessageCodecShareable;
import fendoudebb.fx.tool.netty.protocol.ProtocolFrameDecoder;
import fendoudebb.fx.tool.netty.server.handler.ChatRequestMessageHandler;
import fendoudebb.fx.tool.netty.server.handler.LoginRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        new ChatServer().start(8080, null, null);
    }

    public void start(int port, ChannelFutureListener openCallback, ChannelFutureListener closeCallback) {
        System.out.println(port);
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecShareable messageCodec = new MessageCodecShareable();
        LoginRequestMessageHandler loginRequestMessageHandler = new LoginRequestMessageHandler();
        ChatRequestMessageHandler chatRequestMessageHandler = new ChatRequestMessageHandler();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast(loginRequestMessageHandler);
                    ch.pipeline().addLast(chatRequestMessageHandler);
                }
            });

            ChannelFuture cf = serverBootstrap.bind(port);
            if (openCallback != null) {
                cf.addListener(openCallback);
            }
            Channel channel = cf.sync().channel();
            ChannelFuture channelFuture = channel.closeFuture();
            if (closeCallback != null) {
                channelFuture.addListener(closeCallback);
            }
            channelFuture.sync();
        } catch (InterruptedException e) {
            log.error("chat server exception#{}", e.getCause().getMessage(), e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
