package com.framework.runtime.application.net.transport;

import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;

public abstract class CodecTransportAdapter extends AbstractTransport {
	
	public CodecTransportAdapter(String name) {
		super(name);
	}
	
	public CodecTransportAdapter() {
		
	}

	@Override
	protected void handleMessage(TransportMessage message, TransportChain chain) throws TransportException {
		TransportMessage pre = message;
		message = codec(pre);
		
		message.addAllIds(pre.allIds());
		
		chain.next(this, message);
	}
	
	protected abstract TransportMessage codec(TransportMessage message) throws TransportException;

	@Override
	protected void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException {
		TransportMessage pre = message;
		
		message = codec(message);
		
		message.addAllIds(pre.allIds());
		
		chain.back(this, message);
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
