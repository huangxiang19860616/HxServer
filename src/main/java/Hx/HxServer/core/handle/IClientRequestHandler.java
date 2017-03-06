package Hx.HxServer.core.handle;

import Hx.HxServer.core.extension.IExtension;
import Hx.HxServer.core.extension.impl.IParams;
import Hx.HxServer.core.extension.impl.ISender;

/**
 * Created by huangxiang on 2017/3/1 0001.
 */
public interface IClientRequestHandler {
    void handleClientRequest(ISender sender, IParams params);

    void setParentExtension(IExtension extension);

    IExtension getParentExtension();
}
