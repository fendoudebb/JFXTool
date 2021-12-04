package fendoudebb.fx.tool.netty.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatRequestMessage extends Message{

    private long timestamp;

    private String content;

    @Override
    public int getMessageType() {
        return CHAT_REQUEST_MESSAGE;
    }
}
