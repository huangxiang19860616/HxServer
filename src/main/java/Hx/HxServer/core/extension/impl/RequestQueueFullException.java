package Hx.HxServer.core.extension.impl;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public class RequestQueueFullException extends Exception {
    public RequestQueueFullException() {
    }

    public RequestQueueFullException(String message) {
        super(message);
    }
}
