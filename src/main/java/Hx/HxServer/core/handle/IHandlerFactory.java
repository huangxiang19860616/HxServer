package Hx.HxServer.core.handle;

/**
 * Created by huangxiang on 2017/3/1 0001.
 */
public interface IHandlerFactory {
    void addHandler(IRequestType type, Class<?> handler);

    void addHandler(IRequestType type, Object handler);

    void removeHandler(IRequestType type);

    Object findHandler(IRequestType type);

    void clearAll();
}
