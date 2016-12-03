package com.framework.runtime.application.net;

import com.framework.runtime.application.net.transport.TransportException;

public interface TransportChain {

	void addTransport(Transport transport);
	
	void back(Transport transport,TransportMessage message) throws TransportException;
	
	void next(Transport transport,TransportMessage message) throws TransportException;
	
	void startup()  throws TransportException;
	
	void shutdown()  throws TransportException;
}
