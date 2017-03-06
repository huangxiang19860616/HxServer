package Hx.HxServer.network.netty.server;


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
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyGameServer<T extends GameSession> {
    
    private static final Logger logger = LoggerFactory.getLogger(NettyGameServer.class);
    
    private final int port;
    private final MessageRegistry msgRegistry;
    private final GameSessionFactory<T> sessionFactory;

    public NettyGameServer(int port, MessageRegistry msgRegistry,
            GameSessionFactory<T> sessionFactory) {
        
        this.port = port;
        this.msgRegistry = msgRegistry;
        this.sessionFactory = sessionFactory;
    }
    
    public void start(boolean sync, boolean isBig, boolean closeWhenException) throws Exception {
        final boolean isDebug = logger.isDebugEnabled();
        final GameMessageEncoder MSG_ENCODER = new GameMessageEncoder(msgRegistry);
        final GameMessageLogger MSG_LOGGER = new GameMessageLogger(msgRegistry);
        final BigGameMessageEncoder BIG_MSG_ENCODER = new BigGameMessageEncoder(msgRegistry);
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup);
            sb.channel(NioServerSocketChannel.class);
            sb.localAddress(new InetSocketAddress(port));
            sb.childHandler(new ChannelInitializer<SocketChannel>() {
                    
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new LoggingHandler());
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
                                sessionFactory.createGameSession(), null, closeWhenException));
                    } 
                    
            });
            
            ChannelFuture cf = sb.bind().sync();
            logger.error("start server at port: {}", port);
            if (sync) {
                cf.channel().closeFuture().sync();
            }
        } finally {
            if (sync) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

}
