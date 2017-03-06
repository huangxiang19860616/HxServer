package Hx.HxServer.core.filter;

import Hx.HxServer.core.event.IEvent;
import Hx.HxServer.core.extension.impl.IParams;
import Hx.HxServer.core.extension.impl.ISender;
import Hx.HxServer.core.handle.IRequestType;

/**
 * Created by huangxiang on 2017/3/1 0001.
 */
public interface IFilterChain {
    void addFilter(String filterName, IFilter filter);

    void remove(String filterName);

    FilterAction runRequestInChain(IRequestType requestType, ISender sender, IParams params);

    FilterAction runEventInChain(IEvent event);

    int size();

    void destroy();
}
