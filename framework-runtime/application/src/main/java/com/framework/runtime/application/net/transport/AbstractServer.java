package com.framework.runtime.application.net.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.ByteTransportMessage;
import com.framework.runtime.application.net.RemotingException;
import com.framework.runtime.application.net.Server;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.util.ScheduleManager;

public abstract class AbstractServer extends AbstractEndpoint implements Server {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());

	public AbstractServer(String name, String service,String ip, int port) {
		super(name, service, ip, port);
	}
	
	public AbstractServer(String name) {
		super(name);
	}
	
	public AbstractServer() {
		
	}
	
	protected boolean isClient() {
		return false;
	}
	
	@Override
	public void send(byte[] message) throws RemotingException {
		
	}
	
	@Override
	public void startup() throws TransportException {
		ScheduleManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				try {
					startServer();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			
		}, 1);
	}
	
	protected abstract void startServer() throws TransportException;
	
	protected void handleMessage(TransportMessage message, TransportChain chain) throws TransportException {
		chain.next(this, message);
	}

	protected void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException {
		try {
			TransportId id = message.getCurrentId();
			if (id != null) {
				logger.info("发送返回数据：" + id);
				byte[] data = this.getNetMessageCodec().encode((ByteTransportMessage)message);
				this.send(id.getChildId(), data);
			}
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}
}
