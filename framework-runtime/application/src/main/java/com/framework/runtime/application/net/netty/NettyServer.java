package com.framework.runtime.application.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.netty.handler.ChannelMessageEncoder;
import com.framework.runtime.application.net.netty.handler.NettyInboundHandler;
import com.framework.runtime.application.net.transport.AbstractServer;
import com.framework.runtime.application.net.transport.TransportException;

public class NettyServer extends AbstractServer {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	public NettyServer(String name, String service,String ip, int port) {
		super(name, service, ip, port);
	}
	
	public NettyServer(String name) {
		super(name);
	}
	
	public NettyServer() {
		
	}

	public void startServer() throws TransportException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(100000000,0, 2, 0, 2));
                	 if(getHeartbeatTime() > 0 || getIdleCloseTime() > 0)
                	 ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(getIdleCloseTime(), (getIdleCloseTime() + getHeartbeatTime()) / 2, getHeartbeatTime(), TimeUnit.SECONDS));// 心跳包
                     ch.pipeline().addLast(new NettyInboundHandler(NettyServer.this, NettyServer.this.getConnectionHandler()));
                     ch.pipeline().addLast(new ChannelMessageEncoder());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            ChannelFuture f = b.bind(getPort()).sync(); // (7)
            logger.info("服务器启动!![port:" + getPort() + "]");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
        	throw new TransportException(e);
        }        
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}
	
	public static void main(String[] args) throws Exception {
		new NettyServer("", "", "0.0.0.0", 8888).startup();
	}

	
	
}
