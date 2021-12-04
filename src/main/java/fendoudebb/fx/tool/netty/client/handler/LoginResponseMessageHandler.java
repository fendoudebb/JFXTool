package fendoudebb.fx.tool.netty.client.handler;

import fendoudebb.fx.tool.netty.message.LoginResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LoginResponseMessageHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
        log.info("chat client login response#{}", msg);
    }
}
