package Hx.HxServer.network.core;

/**
 * 处理客户端发来的消息.
 * GameAction实现应该是无状态的，因为同一个GameAction实例可能会被多个线程同时使用.
 * 
 * @param <S> Session类型
 * @param <T> 消息的类型
 */
// TODO 改成抽象类
// TODO 这里用泛型导致代码比较丑陋
public interface GameAction<S extends GameSession, T> {
    
    /**
     * 处理消息.
     * @param session
     * @param msg 
     * @param msgId 
     */
    public void execute(S session, T msg, int msgId);
    
}
