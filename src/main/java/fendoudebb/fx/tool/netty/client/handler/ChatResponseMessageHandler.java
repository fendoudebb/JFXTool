package fendoudebb.fx.tool.netty.client.handler;

import fendoudebb.fx.tool.netty.client.queue.ChatMessageQueue;
import fendoudebb.fx.tool.netty.message.ChatResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class ChatResponseMessageHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        log.info("chat client chat response#{}", msg);
        ChatMessageQueue.put(msg);
    }
}
