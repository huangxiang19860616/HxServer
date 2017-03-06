package Hx.HxServer.config.properties;

import Hx.HxServer.core.config.PropertiesConfig;

/**
 * Created by huangxiang on 2017/2/20 0020.
 */
public class JdbcConfig extends PropertiesConfig {

    private static JdbcConfig instance;

    private JdbcConfig()
    {
        loadConfig("Jdbc.properties");
    }

    static
    {
        instance = new JdbcConfig();
    }

    public static JdbcConfig getInstance()
    {
        return  instance;
    }

    public  boolean isEnabled()
    {
        return instance.config != null;
    }

    public String getJdbcDriver()
    {
        return instance.config.getString("jdbc.driver");
    }

    public String getJdbcUrl()
    {
        return instance.config.getString("jdbc.url");
    }

    public String getUserName()
    {
        return instance.config.getString("jdbc.username");
    }

    public String getPassword()
    {
        return instance.config.getString("jdbc.password");
    }

    public Integer getMaxPoolSize()
    {
        return instance.config.getInt("jdbc.maxPoolSize");
    }

    public Integer getMinPoolSize()
    {
        return instance.config.getInt("jdbc.minPoolSize");
    }

    public Integer getInitialPoolSize()
    {
        return instance.config.getInt("jdbc.initialPoolSize");
    }

    public Integer getMaxIdleTime()
    {
        return instance.config.getInt("jdbc.maxIdleTime");
    }

    public String getChina() {
        return instance.config.getString("jdbc.china");
    }
}
