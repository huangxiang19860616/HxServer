package Hx.HxServer.network.jetty.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Hx.HxServer.network.jetty.core.GmAction;

public abstract class GmAction<T> {
    protected static Logger logger = LoggerFactory.getLogger(GmAction.class);
    public abstract Object execute(T msg);
}
