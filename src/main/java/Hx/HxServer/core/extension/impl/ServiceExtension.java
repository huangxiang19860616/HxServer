package Hx.HxServer.core.extension.impl;

import Hx.HxServer.core.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static Hx.HxServer.core.service.IService.EServiceState.EServiceStarting;

/**
 * Created by huangxiang on 2017/2/28 0028.
 * 注册子模块，初始化子模块
 */
public class ServiceExtension extends EventHandleExtension {

    private final Logger logger = LoggerFactory.getLogger(ServiceExtension.class);

    private final Map<UUID, IService> serviceMap = new HashMap();

    protected void init()
    {
        super.init();
    }

    protected void destory()
    {
        serviceMap.values().forEach(Hx.HxServer.core.service.IService::destory);
        super.destory();
    }

    /*
    *  后续可以提供同步和异步方法，优化效率
    * */
    public void registService(IService service)
    {
        service.init();
        service.registEventListen(this);
        service.registRequestHandle(this);
        serviceMap.put(service.getId(), service);
        service.start();
    }

    public void unRegistService(IService service)
    {
        unRegistService(service.getId());
    }

    public void unRegistService(UUID uuid)
    {
        if(!serviceMap.containsKey(uuid))
        {
            getExceptionService().throwException(uuid);
        }

        IService service = serviceMap.get(uuid);

        service.unRegistEventListen(this);
        service.unRegistRequestHandle(this);

        if(service.getServiceState().equals(EServiceStarting))
        {
            service.destory();
        }

        serviceMap.remove(uuid);
    }
}
