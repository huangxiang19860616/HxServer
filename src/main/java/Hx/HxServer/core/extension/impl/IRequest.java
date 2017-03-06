package Hx.HxServer.core.extension.impl;

import Hx.HxServer.core.MessagePriority;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public interface IRequest {
    MessagePriority getPriority();

    void setPriority(MessagePriority priority);

    long getTimeStamp();

    void setTimeStamp(long timeStamp);
}
