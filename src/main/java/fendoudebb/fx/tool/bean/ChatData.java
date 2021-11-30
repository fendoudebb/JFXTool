package fendoudebb.fx.tool.bean;

import lombok.Data;

/**
 * zbj: created on 2021/11/28 17:17.
 */
@Data
public class ChatData {

    private String username;

    private String content;

    private Long timestamp;

    private boolean self;

}
