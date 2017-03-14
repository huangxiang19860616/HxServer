package Hx.HxServer.module;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public abstract class AbstractModule implements IModule{
    private final String id;

    public AbstractModule(String id)
    {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
