package Hx.HxServer.module;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public interface IModuleFactory {
    void registModule(IModule module);
    void unRegistModule(IModule module);
}
