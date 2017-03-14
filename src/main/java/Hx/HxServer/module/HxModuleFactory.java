package Hx.HxServer.module;

/**
 * Created by huangxiang on 2017/3/6 0006.
 */
public class HxModuleFactory extends ModuleFactory{

    void init()
    {
        registModule(new ConfigModule());
        registModule(new LogModule());

    }
}
