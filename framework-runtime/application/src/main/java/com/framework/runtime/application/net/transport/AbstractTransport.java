package com.framework.runtime.application.net.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.Transport;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;

public abstract class AbstractTransport  implements Transport {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());
	
	private String name;
	private String id;
	
	public AbstractTransport() {
		
	}
	
	public void handle(TransportMessage message, TransportChain chain) throws TransportException {
		pushTransportId(message);
		
		logger.info("[" + getName() + "]处理传输层消息:" + message);
		
		handleMessage(message, chain);
	}
	
	public void handleBack(TransportMessage message, TransportChain chain) throws TransportException {
		
		logger.info("[" + getName() + "]处理传输层返回消息:" + message);
		
		message.rsetCurrentId();
		
		handleBackMessage(message, chain);
		
	}
	
	protected abstract void handleMessage(TransportMessage message, TransportChain chain) throws TransportException;
	
	protected abstract void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException;
	
	protected void pushTransportId(TransportMessage message) {
		TransportId id = message.peek();
		if(id == null) {
			id = new TransportId(this.getId());
			message.push(id);
		}
		else {
			if(id.getId() == null) {
				id.setId(this.getId());
			}
			else if(!id.getId().equals(getId())) {
				id = new TransportId(this.getId());
				message.push(id);
			}
		}
		
	}
	
	
	public AbstractTransport(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getId() {
		if(id == null) {
			id = this.getClass().getSimpleName();
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void startup() throws TransportException {
 
	}

	@Override
	public void shutdown() throws TransportException {

	}

	@Override
	public boolean isShutdown() {
		return false;
	}

}
