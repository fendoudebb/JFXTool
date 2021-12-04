package fendoudebb.fx.tool.netty.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemoryImpl implements Session{

    private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();

    private final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();

    private final Map<Channel, Map<String, Object>> channelAttrMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
        channelAttrMap.put(channel, new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannelMap.remove(username);
        channelAttrMap.remove(channel);
    }

    @Override
    public Object getAttr(Channel channel, String key) {
        return channelAttrMap.get(channel).get(key);
    }

    @Override
    public void setAttr(Channel channel, String key, String name) {
        channelAttrMap.get(channel).put(key, name);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public String getUsername(Channel channel) {
        return channelUsernameMap.get(channel);
    }

    @Override
    public Set<Channel> getChannels() {
        return channelUsernameMap.keySet();
    }
}
