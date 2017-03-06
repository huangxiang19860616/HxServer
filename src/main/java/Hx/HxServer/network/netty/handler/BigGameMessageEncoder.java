package Hx.HxServer.network.netty.handler;

import com.google.protobuf.MessageLite;

import Hx.HxServer.network.core.MessageRegistry;
import Hx.HxServer.network.netty.GameMessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 编码消息对象.
 * @see GameMessageDecoder
 *
 * TODO 使用LengthFieldPrepender？
 */
@Sharable
public class BigGameMessageEncoder extends MessageToByteEncoder<Object> {

    private final MessageRegistry registry;

    public BigGameMessageEncoder(MessageRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object msg, ByteBuf bb) throws Exception {
        // MagicNumber
        bb.writeShort(GameMessageProtocol.MAGIC_NUMBER);

        // 消息ID
        int msgId = registry.getMsgId(msg.getClass());
        bb.writeShort(msgId);

        // 后续长度占位
        int bodyLengthIdx = bb.writerIndex();
        bb.writeInt(0);//没有getUnsignedShort

        // 消息数据
        ((MessageLite) msg).writeTo(new ByteBufOutputStream(bb));

        // 补上后续长度
        int bodyLength = bb.writerIndex() - bodyLengthIdx - 4;
        // todo 检查消息长度是否太长（>65535）
        bb.setInt(bodyLengthIdx, bodyLength);
    }
}
