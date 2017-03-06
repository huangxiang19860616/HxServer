package Hx.HxServer.network.jetty.core;

import com.google.protobuf.Parser;

import Hx.HxServer.network.jetty.core.GmAction;

import java.util.Map;

public class GmMsgRegistry {
    private final Map<Integer, Parser<?>> parserMap; // msgId -> parser
    private final Map<Integer, GmAction> actionMap; // msgId -> action

    public GmMsgRegistry(Map<Integer, Parser<?>> parserMap,
                         Map<Integer, GmAction> actionMap) {
        this.parserMap = parserMap;
        this.actionMap = actionMap;
    }

    public GmAction getAction(int msgId) {
        return actionMap.get(msgId);
    }

    public Parser getParser(int msgId) {
        return parserMap.get(msgId);
    }
}
