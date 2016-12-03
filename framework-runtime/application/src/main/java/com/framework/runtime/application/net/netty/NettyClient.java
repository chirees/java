package com.framework.runtime.application.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.netty.handler.ChannelMessageEncoder;
import com.framework.runtime.application.net.netty.handler.NettyInboundHandler;
import com.framework.runtime.application.net.transport.AbstractClient;
import com.framework.runtime.application.net.transport.TransportException;

public class NettyClient extends AbstractClient {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());

	private EventLoopGroup group = new NioEventLoopGroup();
	private Bootstrap bootstrap = new Bootstrap();

	public NettyClient(String name, String service, String ip, int port) {
		super(name, service, ip, port);
	}

	public NettyClient(String name) {
		super(name);
	}

	public void startup() throws TransportException {
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(100000000, 0, 2, 0, 2));
				if (getHeartbeatTime() > 0 || getIdleCloseTime() > 0)
					ch.pipeline().addLast("idleStateHandler",
							new IdleStateHandler(getIdleCloseTime(), (getIdleCloseTime() + getHeartbeatTime()) / 2, getHeartbeatTime(), TimeUnit.SECONDS));// 心跳包
				ch.pipeline().addLast(new NettyInboundHandler(NettyClient.this, NettyClient.this.getConnectionHandler()));
				ch.pipeline().addLast(new ChannelMessageEncoder());
			}
		});
		super.startup();
	}

	protected void connect() throws Exception {

		try {
			// 发起异步链接操作
			logger.info("NETTY 开始连接，当前线程[" + Thread.currentThread().getId() + "]");
			ChannelFuture channelFuture = bootstrap.connect(getIp(), getPort()).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("连接失败！", e);
		} catch (Exception e) {
			logger.error("连接失败！", e);
		} finally {

		}
		logger.info("NETTY 连接结束，当前线程[" + Thread.currentThread().getId() + "]");
	}

	public void shutdown() {
		logger.info("NETTY 关闭客户端，释放线程资源，当前线程[" + Thread.currentThread().getId() + "]");
		super.shutdown();
		group.shutdownGracefully();
	}

	public boolean isShutdown() {
		return group.isShutdown();
	}

	public static void main(String[] args) throws Exception {
		new NettyClient("", "", "0.0.0.0", 8888).startup();
	}

}
