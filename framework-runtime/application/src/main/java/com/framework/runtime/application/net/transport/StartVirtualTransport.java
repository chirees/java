package com.framework.runtime.application.net.transport;

import com.framework.runtime.application.net.Endpoint;
import com.framework.runtime.application.net.NetMessageCodec;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;

public class StartVirtualTransport extends AbstractTransport implements Endpoint {
	private TransportChain chain;

	@Override
	protected void handleMessage(TransportMessage message, TransportChain chain)
			throws TransportException {
		this.chain.next(this, message);
	}

	@Override
	protected void handleBackMessage(TransportMessage message,
			TransportChain chain) throws TransportException {
		this.chain.back(this, message);
	}
	
	public void send(TransportMessage message) throws TransportException {
		this.chain.next(this, message);
	}

	@Override
	public void setTransportChain(TransportChain chain) {
		this.chain = chain;
	}

	@Override
	public void setNetMessageCodec(NetMessageCodec codec) {
		
	}

}
