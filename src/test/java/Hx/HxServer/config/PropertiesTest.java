package Hx.HxServer.config;

import Hx.HxServer.config.properties.JdbcConfig;
import Hx.HxServer.core.config.properties.BasePropertyConfig;
import org.junit.Test;

/**
 * Created by huangxiang on 2017/2/20 0020.
 */
public class PropertiesTest {

    @Test
    public void test() throws Exception {
        System.out.println(JdbcConfig.getInstance().getInitialPoolSize());
        System.out.println(JdbcConfig.getInstance().toString());
        System.out.println(new BasePropertyConfig().toString());
    }
}
