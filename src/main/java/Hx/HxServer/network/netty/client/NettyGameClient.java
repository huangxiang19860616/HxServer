package Hx.HxServer.network.netty.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Hx.HxServer.network.core.GameSession;
import Hx.HxServer.network.core.GameSessionFactory;
import Hx.HxServer.network.core.MessageRegistry;
import Hx.HxServer.network.netty.handler.BigGameMessageDecoder;
import Hx.HxServer.network.netty.handler.BigGameMessageEncoder;
import Hx.HxServer.network.netty.handler.GameMessageDecoder;
import Hx.HxServer.network.netty.handler.GameMessageEncoder;
import Hx.HxServer.network.netty.handler.GameMessageHandler;
import Hx.HxServer.network.netty.handler.GameMessageLogger;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

public class NettyGameClient<T extends GameSession> {

    private static final Logger logger = LoggerFactory.getLogger(NettyGameClient.class);

    private final int port;
    private final String host;
    private final MessageRegistry msgRegistry;
    private final GameSessionFactory<T> sessionFactory;
    private final Bootstrap bootstrap = new Bootstrap();

    public NettyGameClient(String host, int port, MessageRegistry msgRegistry, GameSessionFactory<T> sessionFactory) {
        this.port = port;
        this.msgRegistry = msgRegistry;
        this.sessionFactory = sessionFactory;
        this.host = host;
    }

    public NettyGameClient start(Consumer event, boolean isBig, boolean closeWhenException) {
        final boolean isDebug = logger.isDebugEnabled();
        EventLoopGroup group = new NioEventLoopGroup();
        final GameMessageEncoder MSG_ENCODER = new GameMessageEncoder(msgRegistry);
        final BigGameMessageEncoder BIG_MSG_ENCODER = new BigGameMessageEncoder(msgRegistry);
        final GameMessageLogger MSG_LOGGER = new GameMessageLogger(msgRegistry);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        if (isBig) {
                            ch.pipeline().addLast(new BigGameMessageDecoder(msgRegistry));
                            ch.pipeline().addLast(BIG_MSG_ENCODER);
                        } else {
                            ch.pipeline().addLast(new GameMessageDecoder(msgRegistry));
                            ch.pipeline().addLast(MSG_ENCODER); // shared!
                        }

                        if (isDebug) {
                            ch.pipeline().addLast(MSG_LOGGER); // shared!
                        }

                        ch.pipeline().addLast(new GameMessageHandler<>(msgRegistry,
                                sessionFactory.createGameSession(), event, closeWhenException));
                    }
                });
        return this;
    }

    // netty是否有可用的接口
    public void connect(int reconnectTimes) throws Exception {
        while (reconnectTimes > 0) {
            try {
                bootstrap.connect().sync();
                logger.error("connect OK");
                return;
            } catch (Exception e) {
                reconnectTimes--;
                logger.error("failed to connect", e);
                logger.error("reconnect....");
                Thread.currentThread().sleep(5000);
            }
        }
    }
}
