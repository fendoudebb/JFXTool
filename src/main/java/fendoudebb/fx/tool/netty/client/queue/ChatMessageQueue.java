package fendoudebb.fx.tool.netty.client.queue;

import fendoudebb.fx.tool.netty.message.ChatResponseMessage;

import java.util.concurrent.LinkedBlockingQueue;

public class ChatMessageQueue {

    private static final LinkedBlockingQueue<ChatResponseMessage> queue = new LinkedBlockingQueue<>();

    public static void put(ChatResponseMessage message) throws InterruptedException {
        queue.put(message);
    }

    public static ChatResponseMessage take() throws InterruptedException {
        return queue.take();
    }

}
