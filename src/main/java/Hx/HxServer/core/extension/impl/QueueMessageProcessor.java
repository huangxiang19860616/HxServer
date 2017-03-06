package Hx.HxServer.core.extension.impl;

import Hx.HxServer.core.control.IController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public class QueueMessageProcessor implements IQueueMessageProcessor, Runnable {
    private final BlockingQueue<IRequest> requestQueue;

    private boolean isActive = true;

    private final int maxQueueSize;

    private IController controller;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public QueueMessageProcessor(IController controller, int maxQueueSize) {
        this.requestQueue = new LinkedBlockingQueue<>();
        this.controller = controller;
        this.maxQueueSize = maxQueueSize;
    }

    public void enqueueRequest(IRequest request) throws RequestQueueFullException {
        if (this.requestQueue.size() >= maxQueueSize) {
            throw new RequestQueueFullException();
        }
        this.requestQueue.add(request);
    }

    public void run() {
        while (this.isActive) {
            try {
                IRequest request = (IRequest) this.requestQueue.take();
                controller.processRequest(request);
            } catch (InterruptedException e) {
                //this.isActive = false;
                this.logger.warn("有一个线程抛出异常了，Controller main loop was interrupted");

            } catch (Throwable t) {

            }
        }
    }
}
