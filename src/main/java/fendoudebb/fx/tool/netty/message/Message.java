package fendoudebb.fx.tool.netty.message;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public static final int LOGIN_REQUEST_MESSAGE = 0;
    public static final int LOGIN_RESPONSE_MESSAGE = 1;
    public static final int CHAT_REQUEST_MESSAGE = 2;
    public static final int CHAT_RESPONSE_MESSAGE = 3;

}
