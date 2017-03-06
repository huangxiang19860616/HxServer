package Hx.HxServer.network.netty;

/**
 * 游戏消息协议.
 * 消息由三部分组成：
 * 1、MagicNumber（0x7FAB，二字节）
 * 2、消息ID（两字节）
 * 3、后续长度（两字节）
 * 4、后续内容（Protobuf）
 * ----------------------------------------------
 * | 0x7FAB | MSG ID | BODY LEN |      BODY       |
 * ----------------------------------------------
 */
public class GameMessageProtocol {
    
    // 魔数：0x7FAB
    public static final int MAGIC_NUMBER = 0x7FAB;
    public static final int MAGIC_NUMBER_OFFSET = 0;
    //public static final int MAGIC_NUMBER_LENGTH = 4;
    
    // 消息ID
    //public static final int MSG_ID_OFFSET = 4;
    //public static final int MSG_ID_LENGTH = 2;
    
    // 后续长度
    public static final int BODY_LEN_OFFSET = 4;
    //public static final int BODY_LEN_LENGTH = 2;
    
    // Protobuf
    public static final int HEADER_LENGTH = 6;
    public static final int HEADER_BIG_LENGTH = 8;
    //public static final int BODY_OFFSET = 8;
    //public static final int BODY_MAX_LENGTH = 65535;
    
}
