package Hx.HxServer.network.netty.handler;

import com.google.protobuf.GeneratedMessage;

import Hx.HxServer.message.protobuf.JsonHelper;
import Hx.HxServer.network.core.GameSession;
import Hx.HxServer.network.core.MessageRegistry;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 把消息用JSON格式打印到日志里.
 */
@Sharable
public class GameMessageLogger extends ChannelDuplexHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GameMessageLogger.class);
    
    private final MessageRegistry registry;
    
    public GameMessageLogger(MessageRegistry registry) {
        this.registry = registry;
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof GeneratedMessage) {
            int msgId = registry.getMsgId(msg.getClass());
            String msgName = msg.getClass().getSimpleName();
            String msgJson = JsonHelper.toJson(msg);
            GameSession session = getSession(ctx);
            logger.debug("*SENDING>>>>> MSG#{} SESSION:{} {} {}",
                    msgId, session, msgName, msgJson);
        }
        
        ctx.write(msg, promise);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof GeneratedMessage) {
            int msgId = registry.getMsgId(msg.getClass());
            String msgName = msg.getClass().getSimpleName();
            String msgJson = JsonHelper.toJson(msg);
            GameSession session = getSession(ctx);
            logger.debug("*RECEIVED<<<<< MSG#{} SESSION:{} {} {}",
                    msgId, session, msgName, msgJson);
        }
        
        ctx.fireChannelRead(msg);
    }
    
    private static GameSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(GameMessageHandler.DEBUG_SESSION_KEY).get();
    }
    
}
