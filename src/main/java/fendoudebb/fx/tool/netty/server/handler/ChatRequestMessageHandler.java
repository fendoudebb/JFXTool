package fendoudebb.fx.tool.netty.server.handler;

import fendoudebb.fx.tool.netty.message.ChatRequestMessage;
import fendoudebb.fx.tool.netty.message.ChatResponseMessage;
import fendoudebb.fx.tool.netty.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.function.Consumer;

@Slf4j
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String username = SessionFactory.getSession().getUsername(ctx.channel());

        Set<Channel> channels = SessionFactory.getSession().getChannels();
        channels.forEach(channel -> {
            ChatResponseMessage message = new ChatResponseMessage(true, msg.getContent());
            message.setFrom(username);
            message.setContent(msg.getContent());
            message.setTimestamp(System.currentTimeMillis());
            channel.writeAndFlush(message);
        });

    }
}
