package Hx.HxServer.network.core;

import com.google.protobuf.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 负责创建MsgRegistry.
 */
public class MessageRegistryBuilder {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageRegistryBuilder.class);
    
    private final Map<Integer, Parser<?>> parserMap = new HashMap<>();
    private final Map<Integer, GameAction<?, ?>> actionMap = new HashMap<>();
    private final Map<Class<?>, Integer> msgIdMap = new HashMap<>();
    
    /**
     * 注册请求消息和Action.
     * 请求消息和响应消息一一对应.
     * 
     * @param <T>
     * @param msgId 消息ID（必须大于等于0）
     * @param reqMsgParser 请求消息的Parser
     * @param reqMsgClass 请求消息的Class
     * @param respMsgClass 响应消息的Class
     * @param action Action实例
     * @return 
     */
    public <T> MessageRegistryBuilder register(int msgId, Parser<T> reqMsgParser, Class<?> reqMsgClass, Class<?> respMsgClass, GameAction<?, T> action) {
        if (msgId < 0) {
            throw new IllegalArgumentException("BAD MSG ID: " + msgId);
        }
        
        registerParser(msgId, reqMsgParser);
        registerAction(msgId, action);
        registerMsgId(reqMsgClass, msgId);
        registerMsgId(respMsgClass, msgId);
        
        logger.info("Register(#{}, {}, {}, {}, {})",
                msgId,
                reqMsgParser.getClass().getName(),
                reqMsgClass.getSimpleName(),
                respMsgClass.getSimpleName(),
                action.getClass().getSimpleName()
        );
        
        return this;
    }
    
    protected void registerParser(int msgId, Parser<?> parser) {
        if (parserMap.containsKey(msgId)) {
            throw new RuntimeException("Duplicate parser! msgId=" + msgId);
        }
        
        parserMap.put(msgId, Objects.requireNonNull(parser));
    }
    
    protected void registerAction(int msgId, GameAction<?, ?> action) {
        if (actionMap.containsKey(msgId)) {
            throw new RuntimeException("Duplicate action! msgId=" + msgId);
        }
        
        actionMap.put(msgId, Objects.requireNonNull(action));
    }
    
    private void registerMsgId(Class<?> msgClass, int msgId) {
        if (msgIdMap.containsKey(msgClass)) {
            throw new RuntimeException("Duplicate msg! class=" + msgClass);
        }
        
        msgIdMap.put(Objects.requireNonNull(msgClass), msgId);
    }
    
    /**
     * 注册PUSH消息.
     * PUSH消息由服务器主动发送给客户端.
     * 
     * @param msgId 消息ID（必须大于等于0）
     * @param pushMsgClass PUSH消息的Class
     * @return 
     */
    public MessageRegistryBuilder registerPush(int msgId, Class<?> pushMsgClass) {
        registerMsgId(pushMsgClass, msgId);
        
        return this;
    }
    
    /**
     * 创建MsgRegistry实例.
     * @return 
     */
    public MessageRegistry build() {
        return new MessageRegistry(parserMap, actionMap, msgIdMap);
    }
    
}
