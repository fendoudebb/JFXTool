package fendoudebb.fx.tool.netty.server.service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImpl implements UserService{

    private final Map<String, String> userMap = new ConcurrentHashMap<>();

    {
        userMap.put("Tom", "123");
        userMap.put("Jerry", "456");
        userMap.put("Jack", "789");
        userMap.put("Alice", "000");
    }

    @Override
    public boolean login(String username, String password) {
        String pwd = userMap.get(username);
        if (pwd == null) {
            return false;
        }
        return Objects.equals(pwd, password);
    }
}
