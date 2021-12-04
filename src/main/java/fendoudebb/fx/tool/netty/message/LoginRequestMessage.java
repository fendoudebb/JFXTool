package fendoudebb.fx.tool.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginRequestMessage extends Message {

    private String username;
    private String password;

    @Override
    public int getMessageType() {
        return LOGIN_REQUEST_MESSAGE;
    }
}
