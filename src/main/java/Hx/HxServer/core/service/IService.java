package Hx.HxServer.core.service;

import Hx.HxServer.core.extension.IExtension;

import java.util.UUID;

/**
 * Created by huangxiang on 2017/2/28 0028.
 */
public interface IService {
    enum EServiceState
    {
        EServiceStoped,
        EServiceStarting,
        EServiceStarted,
        EServiceStopping
    }

    String getServiceName();

    void init();

    void destory();

    void start();

    EServiceState getServiceState();

    void setServiceState(EServiceState state);

    UUID getId();

    void registEventListen(IExtension extension);

    void registRequestHandle(IExtension extension);

    void unRegistEventListen(IExtension extension);

    void unRegistRequestHandle(IExtension extension);
}
