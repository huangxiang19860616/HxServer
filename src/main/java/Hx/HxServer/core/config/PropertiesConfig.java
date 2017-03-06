package Hx.HxServer.core.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by huangxiang on 2017/2/20 0020.
 */
public abstract class PropertiesConfig implements IConfig{
    private static final Logger logger = LoggerFactory
            .getLogger(PropertiesConfig.class);

    protected PropertiesConfiguration config;

    public void loadConfig(String file)
    {
        try
        {
            config = new PropertiesConfiguration();
            config.setEncoding("utf8");
            config.load(file);
            config.setReloadingStrategy(new FileChangedReloadingStrategy());  //修改了本地文件，可以自重载
            logger.info("配置文件{}加载成功", file);
        }
        catch (ConfigurationException e)
        {
            logger.error(String.format("配置文件{%s}加载失败!", file), e);
        }
    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer("PropertiesConfig{\n");

        for (Iterator<String>itr = config.getKeys();itr.hasNext();)
        {
            String key = itr.next();
            String value = config.getString(key);
            stringBuffer.append(key + ": " + value + ";\n");
        }

        return stringBuffer.toString();
    }
}
