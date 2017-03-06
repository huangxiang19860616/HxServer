package Hx.HxServer.core.control;

import Hx.HxServer.core.RequestComparator;
import Hx.HxServer.core.extension.impl.IRequest;
import Hx.HxServer.core.extension.impl.RequestQueueFullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Administrator on 2017/3/3 0003.
 */
public abstract class AbstractController implements IController, Runnable {
    protected String name;
    protected BlockingQueue<IRequest> requestQueue;
    protected ExecutorService threadPool;
    protected int threadPoolSize = -1;
    protected volatile int maxQueueSize = -1;
    protected volatile boolean isActive = false;
    private volatile int threadId = 1;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractController() {
    }

    public void enqueueRequest(IRequest request) throws RequestQueueFullException {
        if(this.requestQueue.size() >= this.maxQueueSize) {
            throw new RequestQueueFullException();
        } else {
            this.requestQueue.add(request);
        }
    }

    public void init(String controlName, int controlMaxQueueSize, int controlthreadPoolSize) {
        setName(controlName);
        setMaxQueueSize(controlMaxQueueSize);
        setThreadPoolSize(controlthreadPoolSize);
        if(this.isActive) {
            throw new IllegalArgumentException("Object is already initialized. Destroy it first!");
        } else if(this.threadPoolSize < 1) {
            throw new IllegalArgumentException("Illegal value for a thread pool size: " + this.threadPoolSize);
        } else if(this.maxQueueSize < 1) {
            throw new IllegalArgumentException("Illegal value for max queue size: " + this.maxQueueSize);
        } else {
            RequestComparator requestComparator = new RequestComparator();
            this.requestQueue = new PriorityBlockingQueue(50, requestComparator);
            this.threadPool = Executors.newFixedThreadPool(this.threadPoolSize);
            this.isActive = true;
            this.initThreadPool();
            this.logger.info(String.format("Controller started: %s -- Queue: %s/%s", new Object[]{this.getClass().getName(), Integer.valueOf(this.getQueueSize()), Integer.valueOf(this.getMaxQueueSize())}));
        }
    }

    public void destroy() {
        this.isActive = false;
        List leftOvers = this.threadPool.shutdownNow();
        this.logger.info("Controller stopping: " + this.getClass().getName() + ", Unprocessed tasks: " + leftOvers.size());
    }

    public void handleMessage(Object message) {
    }

    public void run() {
        Thread.currentThread().setName(this.getClass().getName() + "-" + this.threadId++);

        while(this.isActive) {
            try {
                IRequest t = (IRequest)this.requestQueue.take();
                this.processRequest(t);
            } catch (InterruptedException var2) {
                this.isActive = false;
                this.logger.warn("Controller main loop was interrupted");
            } catch (Throwable var3) {
            }
        }

        this.logger.info("Controller worker threads stopped: " + this.getClass().getName());
    }

    public abstract void processRequest(IRequest var1) ;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreadPoolSize() {
        return this.threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        if(this.threadPoolSize < 1) {
            this.threadPoolSize = threadPoolSize;
        }

    }

    public int getQueueSize() {
        return this.requestQueue.size();
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    protected void initThreadPool() {
        for(int j = 0; j < this.threadPoolSize; ++j) {
            this.threadPool.execute(this);
        }

    }
}
