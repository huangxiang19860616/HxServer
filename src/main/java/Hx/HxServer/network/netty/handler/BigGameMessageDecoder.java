package Hx.HxServer.network.netty.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;

import Hx.HxServer.network.core.MessageRegistry;
import Hx.HxServer.network.netty.GameMessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CodecException;

import java.io.InputStream;
import java.util.List;

/**
 * 解码消息对象.
 * @see GameMessageProtocol
 *
 * TODO 使用LengthFieldBasedFrameDecoder？
 */
public class BigGameMessageDecoder extends ByteToMessageDecoder {

    private final MessageRegistry registry;

    public BigGameMessageDecoder(MessageRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf bb, List<Object> list) throws Exception {
        // 消息是否完整到达？
        if (fullMessageArrived(bb)) {
            // 消息完整到达
            Object msg = decodeMessage(bb);
            list.add(msg);
        }
    }

    // 看消息是否完整到达，顺便检查MagicNumber
    private boolean fullMessageArrived(ByteBuf bb) {
        // 看消息头是否完整
        if (bb == null || bb.readableBytes() < GameMessageProtocol.HEADER_BIG_LENGTH) {
            return false;
        }
        bb.markReaderIndex();
        // 检查MagicNumber
        final int magicNumber = bb.readShort();
        if (magicNumber != GameMessageProtocol.MAGIC_NUMBER) {
            throw new CodecException("Bad Magic Number!");
        }
        // 跳过MsgId
        bb.readUnsignedShort();
        // 看消息是否完整到达
        final int bodyLength = bb.readInt();
        //System.out.print("++++++++++" + bodyLength);
        bb.resetReaderIndex();
        return bb.readableBytes() >= GameMessageProtocol.HEADER_BIG_LENGTH + bodyLength;
    }

    // 解码消息
    private Object decodeMessage(ByteBuf bb) throws InvalidProtocolBufferException {
        bb.readShort(); // 跳过MagicNumber
        int msgId = bb.readUnsignedShort();
        int bodyLength = bb.readInt();
        //System.out.println("==========" + bodyLength);

        Parser<?> parser = registry.getParser(msgId);

        // 用Reader，避免拷贝字节数组
        InputStream is = new ByteBufInputStream(bb, bodyLength);

        Object msg = parser.parseFrom(is);

        return msg;
    }

}
