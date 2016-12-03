package com.framework.runtime.application.net.transport;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.Endpoint;
import com.framework.runtime.application.net.Transport;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;

public class SimpleTransportChain implements TransportChain {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	
	private List<Transport> transports = new LinkedList();

	@Override
	public void addTransport(Transport transport) {
		transports.add(transport);
		if(transport instanceof Endpoint) {
			Endpoint endpoint = (Endpoint)transport;
			endpoint.setTransportChain(this);
		}
	}
	

	public List<Transport> getTransports() {
		return transports;
	}


	public void setTransports(List<Transport> transports) {
		this.transports = transports;
	}

	@Override
	public void back(Transport transport, TransportMessage message) throws TransportException{
		int idx = transports.indexOf(transport);
		if(idx > 0) {
			Transport back = transports.get(idx - 1);
			back.handleBack(message, this);
		}
	}

	@Override
	public void next(Transport transport,TransportMessage message) throws TransportException{
		int idx = transports.indexOf(transport);
		if(idx < transports.size() - 1) {
			Transport next = transports.get(idx + 1);
			next.handle(message, this);
		}
	}

	@Override
	public void startup()  throws TransportException {
		for(Transport transport:transports) {
			logger.info("启动:" + transport.getName());
			transport.startup();
		}
	}

	@Override
	public void shutdown()  throws TransportException {
		for(Transport transport:transports) {
			transport.shutdown();
		}
	}

}
