package Hx.HxServer.core.service;

/**
 * Created by huangxiang on 2017/2/28 0028.
 */
public interface IExceptionService {
    void throwException(Object... msg);
    void throException(Exception e, Object...msg);
}
