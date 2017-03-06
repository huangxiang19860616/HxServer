package Hx.HxServer.network.jetty.core;

import com.google.protobuf.Parser;

import Hx.HxServer.network.jetty.core.FsGmMsgRegistryBuilder;
import Hx.HxServer.network.jetty.core.GmAction;
import Hx.HxServer.network.jetty.core.GmMsgRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FsGmMsgRegistryBuilder {

    private final Map<Integer, Parser<?>> parserMap = new HashMap<>(); // msgId -> parser
    private final Map<Integer, GmAction> actionMap = new HashMap<>(); // msgId -> action

    public <T> FsGmMsgRegistryBuilder register(int msgId, Class<?> protoClass, GmAction action) {

        try {
            Class<?> reqClass = Class.forName(protoClass.getName() + "$" + protoClass.getSimpleName() + "Request");
            Class<?> respClass = Class.forName(protoClass.getName() + "$" + protoClass.getSimpleName() + "Response");
            @SuppressWarnings("unchecked")
            Parser<T> reqParser = (Parser<T>) reqClass.getField("PARSER").get(null);
            register(msgId, reqParser, reqClass, respClass, action);
            return this;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to register msg!", e);
        }
    }

    private <T> void register(int msgId, Parser<T> reqMsgParser, Class<?> reqMsgClass, Class<?> respMsgClass, GmAction action) {
        if (msgId < 0) {
            throw new IllegalArgumentException("BAD MSG ID: " + msgId);
        }

        registerParser(msgId, reqMsgParser);
        registerAction(msgId, action);
    }

    private void registerParser(int msgId, Parser<?> parser) {
        if (parserMap.containsKey(msgId)) {
            throw new RuntimeException("Duplicate parser! msgId=" + msgId);
        }
        parserMap.put(msgId, Objects.requireNonNull(parser));
    }

    private void registerAction(int msgId, GmAction action) {
        if (actionMap.containsKey(msgId)) {
            throw new RuntimeException("Duplicate action! msgId=" + msgId);
        }
        actionMap.put(msgId, Objects.requireNonNull(action));
    }

    public GmMsgRegistry build() {
        return new GmMsgRegistry(parserMap, actionMap);
    }
}