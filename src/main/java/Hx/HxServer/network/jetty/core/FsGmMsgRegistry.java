package Hx.HxServer.network.jetty.core;

import Hx.HxServer.network.jetty.core.FsGmMsgRegistryBuilder;
import Hx.HxServer.network.jetty.core.GmMsgRegistry;

public class FsGmMsgRegistry {
    public static final GmMsgRegistry REGISTRY;

    static {
        REGISTRY = new FsGmMsgRegistryBuilder()
//                .register(GmMsgIdEnum.GM_LOCK_ACCOUNT_VALUE,          LockAccount.class,         new GmLockAccountAction())
//                .register(GmMsgIdEnum.GM_GET_PLAYER_INFO_BY_GM_VALUE, GetPlayerInfoByGm.class,   new GmGetInfoAction())
//                .register(GmMsgIdEnum.GM_SEND_ITEM_TO_PLAYER_VALUE,   SendItemToPlayer.class,    new SendItemToPlayerAction())
//                .register(GmMsgIdEnum.GM_SEND_MAIL_CONDITIONAL_VALUE, SendMailConditional.class, new SendMailConditionalAction())
//                .register(GmMsgIdEnum.GM_SEND_WORLD_NOTICE_VALUE,     SendWorldNotice.class,     new SendWorldNoticeAction())
//                .register(GmMsgIdEnum.GM_GET_PLAYER_DETAIL_VALUE,     GetPlayerDetailByGm.class, new GmGetPlayerDetailAction())
                .build();
    }
}
