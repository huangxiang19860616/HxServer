package Hx.HxServer.core.control;

import Hx.HxServer.core.extension.impl.IRequest;

/**
 * Created by huangxiang on 2017/3/3 0003.
 */
public class ExtensionController extends AbstractController{


    public void init(String controlName, int controlMaxQueueSize, int controlthreadPoolSize)
    {
        super.init(controlName, controlMaxQueueSize, controlthreadPoolSize);
    }

    public void destory()
    {

    }

    @Override
    public void processRequest(IRequest var1) {

    }
}
