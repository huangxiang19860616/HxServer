package Hx.HxServer.core.handle.impl;

import Hx.HxServer.core.extension.IExtension;
import Hx.HxServer.core.handle.IHandlerFactory;
import Hx.HxServer.core.handle.IRequestType;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class HandlerFactoryImpl implements IHandlerFactory {
    public HandlerFactoryImpl(IExtension extension) {
    }

    @Override
    public void addHandler(IRequestType type, Class<?> handler) {

    }

    @Override
    public void addHandler(IRequestType type, Object handler) {

    }

    @Override
    public void removeHandler(IRequestType type) {

    }

    @Override
    public Object findHandler(IRequestType type) {
        return null;
    }

    @Override
    public void clearAll() {

    }
}
