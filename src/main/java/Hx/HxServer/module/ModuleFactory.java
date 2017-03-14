package Hx.HxServer.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public abstract class ModuleFactory implements IModuleFactory{
    private static Logger logger = LoggerFactory.getLogger(ModuleFactory.class);
    private Map<String, IModule> moduleMap = new HashMap<>();

    @Override
    public void registModule(IModule module) {
        if(moduleMap.containsKey(module.getId()))
        {
            logger.warn("WARN: repeated regist! " + module.getId());
        }
        else
        {
            moduleMap.put(module.getId(), module);
        }
    }

    @Override
    public void unRegistModule(IModule module) {
        if(!moduleMap.containsKey(module.getId()))
        {
            logger.warn("ERROR: unregist failed! " + module.getId());
        }
        else
        {
            moduleMap.remove(module.getId());
        }
    }

}
