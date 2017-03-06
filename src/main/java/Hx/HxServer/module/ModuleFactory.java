package Hx.HxServer.module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public abstract class ModuleFactory implements IModuleFactory{
    private Map<String, IModule> moduleMap = new ConcurrentHashMap<>();

    @Override
    public void registModule(IModule module) {
        if(moduleMap.containsKey(module.getId()))
        {

        }


    }

}
