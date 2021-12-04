package fendoudebb.fx.tool.netty.server.handler;

import fendoudebb.fx.tool.netty.message.LoginRequestMessage;
import fendoudebb.fx.tool.netty.message.LoginResponseMessage;
import fendoudebb.fx.tool.netty.server.service.UserServiceFactory;
import fendoudebb.fx.tool.netty.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if (login) {
            message = new LoginResponseMessage(true, "login success");
            SessionFactory.getSession().bind(ctx.channel(), username);
            ctx.writeAndFlush(message);
        } else {
            message = new LoginResponseMessage(false, "username or password not correct");
            ctx.writeAndFlush(message);
            ctx.channel().close();
        }
    }
}
