package Hx.HxServer.network.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Hx.HxServer.network.core.GameAction;
import Hx.HxServer.network.core.GameSession;
import Hx.HxServer.network.core.MessageRegistry;
import Hx.HxServer.network.netty.NettyGameSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.function.Consumer;

/**
 * 处理接收到的消息.
 * 
 * @param <T>
 */
public class GameMessageHandler<T extends GameSession> extends ChannelInboundHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(GameMessageHandler.class);

    // 把session和channel关联起来，仅调试用
    static final AttributeKey<GameSession> DEBUG_SESSION_KEY = AttributeKey.valueOf("DEBUG_SESSION_KEY");
    
    private final MessageRegistry registry;
    private final T session;
    private final Consumer channelActiveEvent;
    private final boolean closeWhenException;

    public GameMessageHandler(MessageRegistry registry, T session, Consumer activeEvent, boolean closeWhenException) {
        this.registry = registry;
        this.session = session;
        this.channelActiveEvent = activeEvent;
        this.closeWhenException = closeWhenException;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("channelActive: {}", ctx.channel());
        }
        
        ctx.channel().attr(DEBUG_SESSION_KEY).set(session);
        ((NettyGameSession) session).setContext(ctx);
        if (channelActiveEvent != null) {
            channelActiveEvent.accept(session);
        }

        //super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("channelInactive: {}", ctx.channel());
        }
        
        ctx.channel().attr(DEBUG_SESSION_KEY).remove();
        ((NettyGameSession) session).setContext(null);
        logger.debug("postClose: {}", ctx.channel());
        session.postClose();
        //super.channelInactive(ctx);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("exceptionCaught!", cause);
        ctx.close();

        // todo
        //sessionListener.onClose(session);
        
        //super.exceptionCaught(ctx, cause);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int msgId = registry.getMsgId(msg.getClass());
        GameAction<?, ?> action = registry.getAction(msgId);
        
        // todo
        if (logger.isDebugEnabled()) {
            logger.debug("Execute Action: " + action.getClass());
        }
        
        try {
            Object rawAction = action; // 跳过Java泛型，以及编译器警告！
            ((GameAction) rawAction).execute(session, msg, msgId);
        } catch (Exception e) {
            ctx.pipeline().fireExceptionCaught(e);
        }
        
        //super.channelRead(ctx, msg);
    }
    
}
