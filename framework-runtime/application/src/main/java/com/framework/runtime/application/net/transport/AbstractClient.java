package com.framework.runtime.application.net.transport;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.ByteTransportMessage;
import com.framework.runtime.application.net.NetConnection;
import com.framework.runtime.application.net.RemotingException;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.loadbalance.LoadbalanceNode;

public abstract class AbstractClient extends AbstractEndpoint implements LoadbalanceNode {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());

	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	private LoadbalanceTransport loadbalanceTransport;
	
	private NetConnection connection;

	public LoadbalanceTransport getLoadbalanceTransport() {
		return loadbalanceTransport;
	}

	public void setLoadbalanceTransport(LoadbalanceTransport loadbalanceTransport) {
		this.loadbalanceTransport = loadbalanceTransport;
	}

	public AbstractClient(String name, String service, String ip, int port) {
		super(name, service, ip, port);
	}

	public AbstractClient(String name) {
		super(name);
	}

	protected boolean isClient() {
		return true;
	}
	
	@Override
	public void send(byte[] message) throws RemotingException {
		if(this.connection != null) {
			this.connection.send(message);
		}
	}

	protected void handleMessage(TransportMessage message, TransportChain chain) throws TransportException {
		try {
			byte[] data = this.getNetMessageCodec().encode((ByteTransportMessage)message);
			send(data);
		} catch (RemotingException e) {
			throw new TransportException(e);
		}
	}

	protected void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException {
		if (loadbalanceTransport != null) {
			loadbalanceTransport.handleBack(message, chain);
		} else {
			chain.back(this, message);
		}
	}

	public void startup() throws TransportException {
		logger.info("客户端启动，开始连接[" + getIp() + "," + getPort() + "]");
		connectImpl();
	}

	protected void connected(NetConnection connection) throws TransportException {
		logger.info("连接[" + getIp() + "," + getPort() + "]成功!");
		this.connection = connection;
		if (loadbalanceTransport != null) {
			loadbalanceTransport.nodeStatusChanged(this, LoadbalanceNode.Status.enable);
		}
	}

	protected void disconnected(NetConnection connection) throws TransportException {
		logger.info("连接[" + getIp() + "," + getPort() + "]断开，当前线程[" + Thread.currentThread().getId() + "]");
		this.connection = null;
		connectImpl();
		if (loadbalanceTransport != null) {
			loadbalanceTransport.nodeStatusChanged(this, LoadbalanceNode.Status.disable);
		}
	}

	protected void connectImpl() {
		logger.info("建立连接任务[" + getIp() + "," + getPort() + "]，当前线程[" + Thread.currentThread().getId() + "]");
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				try {
					logger.info("连接[" + getIp() + "," + getPort() + "]断开，重连......当前线程[" + Thread.currentThread().getId() + "]");
					connect();
				} catch (Exception e) {
					logger.error("连接 [" + getIp() + "," + getPort() + "] 失败 !!", e);
				}
			}

		}, 5, TimeUnit.SECONDS);
	}

	protected abstract void connect() throws Exception;

	public void shutdown() {
		this.scheduledExecutorService.shutdown();
	}

	public String toString() {
		return getIp() + ":" + getPort();
	}
}
