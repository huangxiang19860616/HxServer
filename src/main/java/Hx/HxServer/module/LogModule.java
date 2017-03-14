package Hx.HxServer.module;

import javax.jws.WebService;

/**
 * Created by huangxiang on 2017/3/6 0006.
 */
@WebService
public class LogModule extends AbstractModule{
    private static final String id = "logModule";

    public LogModule() {
        super(id);
    }

    public LogModule(String id) {
        super(id);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
