package fendoudebb.fx.tool.netty.protocol;

import fendoudebb.fx.tool.netty.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        // @Shareable
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(
                loggingHandler,
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec()
        );

        LoginRequestMessage message = new LoginRequestMessage("nickname", "password");
        message.setSequenceId(Integer.MAX_VALUE);
        channel.writeOutbound(message);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buffer);

        ByteBuf s1 = buffer.slice(0, 100);
        ByteBuf s2 = buffer.slice(100, buffer.readableBytes() - 100);

//        channel.writeInbound(buffer);
        // mock unpack half-pack sticky-pack
        s1.retain();
        channel.writeInbound(s1);
        channel.writeInbound(s2);

    }
}
