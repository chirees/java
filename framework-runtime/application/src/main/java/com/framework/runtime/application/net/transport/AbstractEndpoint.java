package com.framework.runtime.application.net.transport;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.ConnectionHandler;
import com.framework.runtime.application.net.NetConnection;
import com.framework.runtime.application.net.NetMessageCodec;
import com.framework.runtime.application.net.PhysicalEndpoint;
import com.framework.runtime.application.net.RemotingException;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.codec.DefaultNetMessageCodec;

public abstract class AbstractEndpoint extends AbstractTransport implements PhysicalEndpoint {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	private static Logger errorLogger = LoggerFactory.getLogger(Application.getInstance().getErrorLogger());

	private String ip;
	private int port;
	private int heartbeatTime = 30;
	private int idleCloseTime = 60;

	private Map<String, NetConnection> connections = new HashMap();
	private ConnectionHandler connectionHandler;
	private TransportChain chain;
	private String service;
	
	private NetMessageCodec netMessageCodec;

	public AbstractEndpoint(String name) {
		super(name);
	}

	public AbstractEndpoint() {

	}

	public AbstractEndpoint(String name, String service, String ip, int port) {
		super(name);
		this.ip = ip;
		this.port = port;
		this.service = service;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setTransportChain(TransportChain chain) {
		this.chain = chain;
	}

	protected ConnectionHandler getConnectionHandler() {
		if (connectionHandler == null) {
			connectionHandler = new ConnectionHandler() {

				@Override
				public void connected(NetConnection connection) throws TransportException {
					connections.put(connection.getId(), connection);
					AbstractEndpoint.this.connected(connection);
				}

				@Override
				public void disconnected(NetConnection connection) throws TransportException {
					connections.remove(connection.getId());
					AbstractEndpoint.this.disconnected(connection);
				}

				@Override
				public void sent(NetConnection connection, byte[] message) throws TransportException {
					AbstractEndpoint.this.sent(connection, message);
				}

				@Override
				public void received(NetConnection connection, byte[] message) throws TransportException {
					AbstractEndpoint.this.received(connection, message);
				}

				@Override
				public void caught(NetConnection connection, Throwable exception) throws TransportException {
					AbstractEndpoint.this.caught(connection, exception);
				}

			};
		}

		return connectionHandler;
	}

	protected Collection<NetConnection> getConnections() {
		return connections.values();
	}

	protected NetConnection getConnection(String id) {
		return connections.get(id);
	}

	protected void connected(NetConnection connection) throws TransportException {
	}

	protected void disconnected(NetConnection connection) throws TransportException {
	}

	protected void sent(NetConnection channel, byte[] message) throws TransportException {

	}

	protected void received(NetConnection connection, byte[] message) throws TransportException {
//		TransportMessage<byte[]> data = new TransportMessage(message);

		TransportMessage data = this.getNetMessageCodec().decode(message);
		
		if (isClient()) {
			this.handleBack(data, AbstractEndpoint.this.chain);
		} else {
			TransportId id = new TransportId(getId(), connection.getId());
			data.push(id);
			this.handle(data, chain);
		}
	}

	protected abstract boolean isClient();

	protected void caught(NetConnection connection, Throwable exception) throws TransportException {

	}

	@Override
	public InetSocketAddress getLocalAddress() {
		return null;
	}

	@Override
	public void send(String connectionId, byte[] message) throws RemotingException {
		NetConnection connection = getConnection(connectionId);
		if (connection != null) {
			connection.send(message);
		}
	}

	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getHeartbeatTime() {
		return heartbeatTime;
	}

	public void setHeartbeatTime(int heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}

	public int getIdleCloseTime() {
		return idleCloseTime;
	}

	public void setIdleCloseTime(int idleCloseTime) {
		this.idleCloseTime = idleCloseTime;
	}

	public NetMessageCodec getNetMessageCodec() {
		if(netMessageCodec == null) {
			netMessageCodec = new DefaultNetMessageCodec();
		}
		return netMessageCodec;
	}

	public void setNetMessageCodec(NetMessageCodec netMessageCodec) {
		this.netMessageCodec = netMessageCodec;
	}

	
}
