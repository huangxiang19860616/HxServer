package Hx.HxServer.core.service.impl;

import Hx.HxServer.core.event.IEventType;
import Hx.HxServer.core.extension.IExtension;
import Hx.HxServer.core.handle.IRequestType;
import Hx.HxServer.core.service.IService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static Hx.HxServer.core.service.IService.EServiceState.EServiceStoped;

/**
 * Created by huangxiang on 2017/3/1 0001.
 */
public abstract class AbstractServiceImpl implements IService{
    protected EServiceState serviceState = EServiceStoped;
    protected final String serviceName;
    protected final UUID uuid = UUID.randomUUID();
    protected Map<IEventType, Class<?>> eventTypeClassMap = new HashMap<>();
    protected Map<IRequestType, Class<?>> requestTypeClassMap = new HashMap<>();

    public AbstractServiceImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public EServiceState getServiceState() {
        return serviceState;
    }

    @Override
    public void setServiceState(EServiceState state) {
        this.serviceState = state;
    }

    public UUID getId() {return uuid;}

    @Override
    public void registEventListen(IExtension extension) {
        for(IEventType eventType : eventTypeClassMap.keySet())
        {
            Class<?> listen = eventTypeClassMap.get(eventType);
            extension.addEventListener(eventType, listen);
        }
    }

    @Override
    public void registRequestHandle(IExtension extension) {
        for(IRequestType requestType : requestTypeClassMap.keySet())
        {
            Class<?> handle = requestTypeClassMap.get(requestType);
            extension.addRequestHandler(requestType, handle);
        }
    }

    @Override
    public void unRegistEventListen(IExtension extension) {
        for(IEventType eventType : eventTypeClassMap.keySet())
        {
            Class<?> listen = eventTypeClassMap.get(eventType);
            extension.removeEventListener(eventType, listen);
        }
    }

    @Override
    public void unRegistRequestHandle(IExtension extension) {
        for(IRequestType requestType : requestTypeClassMap.keySet())
        {
            Class<?> handle = requestTypeClassMap.get(requestType);
            extension.removeRequestHandler(requestType, handle);
        }
    }

    abstract public void init();
    abstract public void destory();
    abstract public void start();
}
