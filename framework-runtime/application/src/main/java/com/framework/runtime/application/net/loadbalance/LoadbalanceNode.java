package com.framework.runtime.application.net.loadbalance;

import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.transport.LoadbalanceTransport;
import com.framework.runtime.application.net.transport.TransportException;

public interface LoadbalanceNode {
	
	public enum Status {
		enable,
		disable
	}
	
	void startup() throws TransportException;
	
	void shutdown() throws TransportException;
	
	void setLoadbalanceTransport(LoadbalanceTransport transport) ;
	
	void handle(TransportMessage message, TransportChain chain) throws TransportException;
	
	
}
