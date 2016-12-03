package com.framework.runtime.application.net.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.ConnectionHandler;
import com.framework.runtime.application.net.NetConnection;
import com.framework.runtime.application.net.PhysicalEndpoint;
import com.framework.runtime.application.net.netty.NettyConnection;
import com.framework.runtime.application.util.NumberUtil;

public class NettyInboundHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	public static final byte[] heart_send = NumberUtil.fromHex("00020000");
	public static final byte[] heart_reply = NumberUtil.fromHex("00028888");
	private static AttributeKey connectionKey = AttributeKey.valueOf("connection");

	private PhysicalEndpoint endpoint;
	private ConnectionHandler connectionHandler;

	public NettyInboundHandler(PhysicalEndpoint endpoint, ConnectionHandler connectionHandler) {
		this.endpoint = endpoint;
		this.connectionHandler = connectionHandler;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
//		logger.info("handlerAdded");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
//		logger.info("handlerRemoved");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);

//		logger.info("channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
//		logger.info("channelInactive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
//		logger.info("channelRead:" + message);

		ByteBuf buf = (ByteBuf) message;
		if (this.endpoint.getHeartbeatTime() > 0 && buf.readableBytes() == 2) {
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			if (req[0] == heart_send[2] && req[1] == heart_send[3]) {
				ByteBuf sendBuf = Unpooled.copiedBuffer(heart_reply);
				ctx.writeAndFlush(sendBuf);
//				logger.info("收到心跳包并回应:" + NumberUtil.toHex(req));
			} else {
//				logger.info("收到心跳包回应包:" + NumberUtil.toHex(req));
			}
		} else {
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			Attribute attr = ctx.attr(connectionKey);
			NetConnection connection = (NetConnection) attr.get();
			connectionHandler.received(connection, req);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
//		logger.info("channelReadComplete");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		NettyConnection connection = new NettyConnection(ctx.channel());
		Attribute attr = ctx.attr(connectionKey);
		attr.set(connection);
		connectionHandler.connected(connection);
//		logger.info("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		Attribute attr = ctx.attr(connectionKey);
		NetConnection connection = (NetConnection) attr.get();
		connectionHandler.disconnected(connection);
//		logger.info("channelUnregistered");
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		super.channelWritabilityChanged(ctx);
//		logger.info("channelWritabilityChanged");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
//		logger.info("exceptionCaught");
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			
			if (e.state() == IdleState.READER_IDLE) {
				if (this.endpoint.getIdleCloseTime() > 0)
					ctx.close();
			} else if (e.state() == IdleState.ALL_IDLE) {
				if (this.endpoint.getHeartbeatTime() > 0) {
					logger.info("心跳事件发生...类型：" + e.state());
					ByteBuf sendBuf = Unpooled.copiedBuffer(heart_send);
					ctx.writeAndFlush(sendBuf);
				}
			}
		}

		super.userEventTriggered(ctx, evt);
	}

}
