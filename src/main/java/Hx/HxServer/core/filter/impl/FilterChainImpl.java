package Hx.HxServer.core.filter.impl;

import Hx.HxServer.core.event.IEvent;
import Hx.HxServer.core.extension.IExtension;
import Hx.HxServer.core.extension.impl.IParams;
import Hx.HxServer.core.extension.impl.ISender;
import Hx.HxServer.core.filter.FilterAction;
import Hx.HxServer.core.filter.IFilter;
import Hx.HxServer.core.filter.IFilterChain;
import Hx.HxServer.core.handle.IRequestType;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class FilterChainImpl implements IFilterChain {
    public FilterChainImpl(IExtension extension) {
    }

    @Override
    public void addFilter(String filterName, IFilter filter) {

    }

    @Override
    public void remove(String filterName) {

    }

    @Override
    public FilterAction runRequestInChain(IRequestType requestType, ISender sender, IParams params) {
        return null;
    }

    @Override
    public FilterAction runEventInChain(IEvent event){
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void destroy() {

    }
}
