package fendoudebb.fx.tool.netty.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;

    private String content;

    private long timestamp;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return CHAT_RESPONSE_MESSAGE;
    }
}
