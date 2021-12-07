package fendoudebb.fx.tool.netty.message;

/**
 * zbj: created on 2021/12/5 11:55.
 */
public class PongMessage extends Message{
    @Override
    public int getMessageType() {
        return PONG_MESSAGE;
    }
}
