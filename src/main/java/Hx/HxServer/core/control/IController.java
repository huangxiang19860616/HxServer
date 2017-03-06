package Hx.HxServer.core.control;

import Hx.HxServer.core.extension.impl.IRequest;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public interface IController {
    void processRequest(IRequest request);
    void init(String controlName, int controlMaxQueueSize, int controlThreadPoolSize);
    void destory();
}
