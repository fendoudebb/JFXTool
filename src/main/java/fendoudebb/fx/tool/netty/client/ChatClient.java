package fendoudebb.fx.tool.netty.client;

import fendoudebb.fx.tool.netty.client.handler.ChatResponseMessageHandler;
import fendoudebb.fx.tool.netty.client.handler.LoginResponseMessageHandler;
import fendoudebb.fx.tool.netty.message.LoginRequestMessage;
import fendoudebb.fx.tool.netty.protocol.MessageCodecShareable;
import fendoudebb.fx.tool.netty.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatClient {

    private Channel channel;

    public static void main(String[] args) {
        new ChatClient().start("localhost", 8080, "Tom", "123", null, null);
    }

    public void start(String ip, int port, String username, String password, ChannelFutureListener openCallback, ChannelFutureListener closeCallback) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecShareable messageCodec = new MessageCodecShareable();
        LoginResponseMessageHandler loginResponseMessageHandler = new LoginResponseMessageHandler();
        ChatResponseMessageHandler chatResponseMessageHandler = new ChatResponseMessageHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            LoginRequestMessage message = new LoginRequestMessage(username, password);
                            ctx.writeAndFlush(message);
                        }
                    });
                    ch.pipeline().addLast(loginResponseMessageHandler);
                    ch.pipeline().addLast(chatResponseMessageHandler);
                }
            });
            ChannelFuture cf = bootstrap.connect(ip, port);
            if (openCallback != null) {
                cf.addListener(openCallback);
            }
            channel = cf.sync().channel();
            ChannelFuture channelFuture = channel.closeFuture();
            if (closeCallback != null) {
                channelFuture.addListener(closeCallback);
            }
            channelFuture.sync();
        } catch (InterruptedException e) {
            log.error("chat client exception#{}", e.getCause().getMessage(), e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public Channel getChannel() throws InterruptedException {
        return channel;
    }

}
