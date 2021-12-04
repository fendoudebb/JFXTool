package fendoudebb.fx.tool.netty.server.session;

public class SessionFactory {

    private static final Session SESSION = new SessionMemoryImpl();

    public static Session getSession() {
        return SESSION;
    }
}
