package Hx.HxServer.module;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public interface IModule {
    void init();
    void start();
    void stop();
    String getId();
}
