package Hx.HxServer.core.extension.impl;

import Hx.HxServer.core.event.IEvent;
import Hx.HxServer.core.event.IEventType;
import Hx.HxServer.core.filter.FilterAction;
import Hx.HxServer.core.filter.IFilterChain;
import Hx.HxServer.core.filter.impl.FilterChainImpl;
import Hx.HxServer.core.filter.IFilter;
import Hx.HxServer.core.handle.impl.HandlerFactoryImpl;
import Hx.HxServer.core.handle.IClientRequestHandler;
import Hx.HxServer.core.handle.IHandlerFactory;
import Hx.HxServer.core.handle.IRequestType;

/**
 * Created by Huangxiang on 2017/2/28 0028.
 * 监听器后面再处理
 */
public abstract class EventHandleExtension extends BaseExtension
{
    private final IHandlerFactory handlerFactory = new HandlerFactoryImpl(this);
    private final IFilterChain filterChain = new FilterChainImpl(this);

    protected void init()
    {
        super.init();
    }

    protected void destory()
    {
        this.handlerFactory.clearAll();
        this.filterChain.destroy();
        super.destory();
    }

    public void addEventListener(IEventType eventType, Class<?> listener)
    {

    }

    @Override
    public void removeEventListener(IEventType eventType, Class<?> listener) {

    }

    public void addRequestHandler(IRequestType requestType, Class<?> handler) {
        if(!IClientRequestHandler.class.isAssignableFrom(handler)) {
            getExceptionService().throwException(String.format("Provided Request Handler does not implement IClientRequestHandler: %s, Cmd: %s", new Object[]{handler, requestType}));
        } else {
            this.handlerFactory.addHandler(requestType, handler);
        }
    }

    public void clearAllHandlers() {
        this.handlerFactory.clearAll();
    }

    @Override
    public void removeRequestHandler(IRequestType requestType, Class<?> handler) {
        this.handlerFactory.removeHandler(requestType);
    }

    public final void addFilter(String filterName, IFilter filter) {
        this.filterChain.addFilter(filterName, filter);
    }

    public void removeFilter(String filterName) {
        this.filterChain.remove(filterName);
    }

    public void clearFilters() {
        this.filterChain.destroy();
    }

    public void handleClientRequest(IRequestType requestId, ISender sender, IParams params) {
        if(this.filterChain.size() <= 0 || this.filterChain.runRequestInChain(requestId, sender, params) != FilterAction.HALT) {
            try {
                IClientRequestHandler err = (IClientRequestHandler)this.handlerFactory.findHandler(requestId);
                if(err == null) {
                    getExceptionService().throwException("Request handler not found: \'" + requestId + "\'. Make sure the handler is registered in your extension using addRequestHandler()");
                }

                err.handleClientRequest(sender, params);
            } catch (Exception e) {
                getExceptionService().throwException(e);
            }
        }
    }

    public void handleServerEvent(IEvent event) {
    }
}
