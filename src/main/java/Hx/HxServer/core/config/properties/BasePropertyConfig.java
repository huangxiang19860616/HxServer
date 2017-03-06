package Hx.HxServer.core.config.properties;

import Hx.HxServer.core.config.PropertiesConfig;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public class BasePropertyConfig extends PropertiesConfig{

    private final String file = "base.properties";

    public BasePropertyConfig()
    {
        loadConfig(file);
    }

    public int getControlMaxQueueSize()
    {
        return config.getInt("control.maxQueueSize", 1);
    }

    public int getControlthreadPoolSize()
    {
        return config.getInt("control.threadPoolSize", 1);
    }

    public String getControlName()
    {
        return config.getString("control.name", "base");
    }
}
