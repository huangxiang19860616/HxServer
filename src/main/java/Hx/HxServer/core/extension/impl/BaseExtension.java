package Hx.HxServer.core.extension.impl;

import Hx.HxServer.core.config.IConfig;
import Hx.HxServer.core.config.properties.BasePropertyConfig;
import Hx.HxServer.core.control.ExtensionController;
import Hx.HxServer.core.control.IController;
import Hx.HxServer.core.extension.IExtension;
import Hx.HxServer.core.service.IExceptionService;
import Hx.HxServer.core.service.ILogService;

/**
 * Created by huangxiang on 2017/2/28 0028.
 */
public abstract class BaseExtension implements IExtension {
    private ILogService logService;
    private IExceptionService exceptionService;
    private IController controller;
    private BasePropertyConfig config;

    public IExceptionService getExceptionService() {
        return exceptionService;
    }
    public ILogService getLogService() {
        return logService;
    }

    protected BaseExtension()
    {
        controller = new ExtensionController();
        config = new BasePropertyConfig();
    }

    protected void init()
    {
        controller.init(config.getControlName(), config.getControlMaxQueueSize(), config.getControlthreadPoolSize());
    }

    protected void destory()
    {
        controller.destory();
    }
}
