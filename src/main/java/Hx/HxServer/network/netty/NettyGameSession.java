package Hx.HxServer.network.netty;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Hx.HxServer.network.core.GameSession;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * GameSession实现.
 */
public abstract class NettyGameSession implements GameSession {

    protected Logger logger = LoggerFactory.getLogger(NettyGameSession.class);

    private volatile ChannelHandlerContext ctx; // Channel?

    public void setContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean isOnline() {
        return ctx != null;
    }
    
    @Override
    public void write(Object msg) {
        ChannelHandlerContext localCtx = this.ctx;
        if (localCtx != null) {
            localCtx.write(msg)
                    .addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            localCtx.flush();
        } else {
            // todo
        }
    }

    @Override
    public void close() {
        if (ctx != null) {
            ctx.channel().close();
            // ctx.channel().disconnect();
        }
    }
    
}
