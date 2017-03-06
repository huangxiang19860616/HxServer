package Hx.HxServer.core.extension;

import Hx.HxServer.core.event.IEvent;
import Hx.HxServer.core.event.IEventType;
import Hx.HxServer.core.extension.impl.IParams;
import Hx.HxServer.core.extension.impl.ISender;
import Hx.HxServer.core.filter.IFilter;
import Hx.HxServer.core.handle.IRequestType;

/**
 * Created by huangxiang on 2017/3/1 0001.
 */
public interface IExtension {

    void addFilter(String filterName, IFilter filter);
    void removeFilter(String filterName);
    void clearFilters();
    void addEventListener(IEventType eventType, Class<?> listener);
    void removeEventListener(IEventType eventType, Class<?> listener);
    void addRequestHandler(IRequestType requestType, Class<?> handler);
    void removeRequestHandler(IRequestType requestType, Class<?> handler);
    void clearAllHandlers();
    void handleClientRequest(IRequestType requestType, ISender sender, IParams params);
    void handleServerEvent(IEvent event);
}
