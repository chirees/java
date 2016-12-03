package com.framework.runtime.application.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.UUID;

import com.framework.runtime.application.net.NetConnection;
import com.framework.runtime.application.net.RemotingException;

public class NettyConnection implements NetConnection {
	private Channel channel;
	private String id;
	
	public NettyConnection(Channel channel) {
		this.channel = channel;
		id = UUID.randomUUID().toString();
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return channel.remoteAddress();
	}

	@Override
	public boolean isConnected() {
		return channel.isOpen();
	}

	@Override
	public void send(byte[] message) throws RemotingException {
//		ByteBuf sendBuf = Unpooled.copiedBuffer(message);
		channel.write(message);
		channel.flush();
	}

	@Override
	public SocketAddress getLocalAddress() {
		return channel.localAddress();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void close() {
		this.channel.close();
	}

}
