package Hx.HxServer.network.core;

/**
 * 负责创建GameSession实例.
 * 
 * @param <T> Session类型
 */
public interface GameSessionFactory<T extends GameSession> {
    
    /**
     * 创建GameSession实例.
     * @return 
     */
    public T createGameSession();
    
}
