package com.framework.runtime.application.net;

import com.framework.runtime.application.net.transport.TransportException;

public interface Transport  {

	void handle(TransportMessage message, TransportChain chain) throws TransportException;
	
	void handleBack(TransportMessage message, TransportChain chain) throws TransportException;
	
	void startup() throws TransportException;
	
	void shutdown() throws TransportException;
	
	boolean isShutdown();
	
	String getName();
	
	void setName(String name);
	
	String getId();
	
}
