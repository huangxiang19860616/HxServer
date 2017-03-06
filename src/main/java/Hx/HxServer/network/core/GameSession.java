package Hx.HxServer.network.core;

/**
 * GameAction通过这个接口将消息发送给客户端.
 */
public interface GameSession {

    /**
     * 判断玩家是否在线.
     * @return 
     */
    public boolean isOnline();
    
    /**
     * 给客户端发消息.
     * @param msg 
     */
    public void write(Object msg);
    
    /**
     * 关闭连接.
     */
    public void close();
    
    /**
     * 连接关闭后的回调.
     */
    public void postClose();
    
}
