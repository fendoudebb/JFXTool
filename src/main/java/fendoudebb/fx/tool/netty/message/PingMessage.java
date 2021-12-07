package fendoudebb.fx.tool.netty.message;

/**
 * zbj: created on 2021/12/5 11:53.
 */
public class PingMessage extends Message{
    @Override
    public int getMessageType() {
        return PING_MESSAGE;
    }
}
