package Hx.HxServer.network.core;

import com.google.protobuf.Parser;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

/**
 * MsgRegistry将消息ID、Parser、Class和Action关联起来.
 * 注意：为了加速查找，Parser和GameAction放在数组里，索引是msgId.
 */
@Immutable  //不可变对象
public class MessageRegistry {
    
    private final Parser<?>[] parsers; // msgId -> parser
    private final GameAction<?, ?>[] actions; // msgId -> action
    private final Map<Class<?>, Integer> msgIdMap; // class -> msgId

    public MessageRegistry(
            Map<Integer, Parser<?>> parserMap,
            Map<Integer, GameAction<?, ?>> actionMap,
            Map<Class<?>, Integer> msgIdMap) {
        
        int maxMsgId = findMaxKey(parserMap);
        if (maxMsgId > 1024) {
            throw new RuntimeException("MSG ID is too big! " + maxMsgId);
        }

        // Parser和Action一一对应
        parsers = new Parser<?>[maxMsgId + 1];
        actions = new GameAction<?, ?>[maxMsgId + 1];
        parserMap.keySet().forEach(msgId -> {
            parsers[msgId] = parserMap.get(msgId);
            actions[msgId] = actionMap.get(msgId);
        });
        
        //ImmutableMap.copyOf(msgIdMap);
        this.msgIdMap = new HashMap<>(msgIdMap);
    }
    
    private int findMaxKey(Map<Integer, ?> map) {
        return map.keySet().stream().max(Integer::compare).get();
    }
    
    /**
     * 返回与消息ID对应的Parser.
     * @param msgId
     * @return null 如果没有Parser与消息ID对应
     */
    public Parser<?> getParser(int msgId) {
        if (msgId < 0 || msgId >= parsers.length) {
            throw new RuntimeException("Parser not found! msgId=" + msgId);
        }
        
        Parser<?> parser = parsers[msgId];
        if (parser == null) {
            throw new RuntimeException("Parser not found! msgId=" + msgId);
        }
        
        return parser;
    }
    
    /**
     * 返回与消息ID对应的Action.
     * @param msgId
     * @return null 如果没有Action与消息ID对应
     */
    public GameAction<?, ?> getAction(int msgId) {
        if (msgId < 0 || msgId >= actions.length) {
            throw new RuntimeException("Action not found! msgId=" + msgId);
        }
        
        GameAction<?, ?> action = actions[msgId];
        if (action == null) {
            throw new RuntimeException("Action not found! msgId=" + msgId);
        }
        
        return action;
    }
    
    /**
     * 返回与消息Class对应的消息ID.
     * @param msgClass
     * @return 消息ID
     * @throws RuntimeException 如果没有ID与消息Class对应
     */
    public int getMsgId(Class<?> msgClass) {
        Integer msgId = msgIdMap.get(msgClass);
        if (msgId == null) {
            throw new RuntimeException("MSG ID not found! class=" + msgClass);
        }
        
        return msgId;
    }
    
}
