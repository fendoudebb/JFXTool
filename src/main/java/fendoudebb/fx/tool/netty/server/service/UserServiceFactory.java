package fendoudebb.fx.tool.netty.server.service;

public class UserServiceFactory {

    private static final UserService USER_SERVICE = new UserServiceMemoryImpl();

    public static UserService getUserService() {
        return USER_SERVICE;
    }

}
