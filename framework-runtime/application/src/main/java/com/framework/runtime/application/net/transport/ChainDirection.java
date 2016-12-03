package com.framework.runtime.application.net.transport;

import com.framework.runtime.application.net.Transport;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;

public class ChainDirection {
	
	private TransportChain chain;
	private Transport transport;
	private boolean ahead = true;
	
	public ChainDirection(TransportChain chain, Transport transport, boolean ahead ) {
		this.chain = chain;
		this.transport = transport;
		this.ahead = ahead;
	}

	public void next(TransportMessage message) throws TransportException {
		chain.next(transport, message);
	}
	
	public void back(TransportMessage message) throws TransportException {
		if(ahead) {
			transport.handleBack(message, chain);
		}
		else {
			chain.back(transport, message);
		}
		
	}
	
	

}
