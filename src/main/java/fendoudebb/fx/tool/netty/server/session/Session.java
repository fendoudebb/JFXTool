package fendoudebb.fx.tool.netty.server.session;

import io.netty.channel.Channel;

import java.util.Set;

public interface Session {

    void bind(Channel channel, String username);

    void unbind(Channel channel);

    Object getAttr(Channel channel, String key);

    void setAttr(Channel channel, String key, String name);

    Channel getChannel(String username);

    String getUsername(Channel channel);

    Set<Channel> getChannels();

}
