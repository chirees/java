package com.framework.runtime.application.net.trans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.TransportChain;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.transport.AbstractTransport;
import com.framework.runtime.application.net.transport.ChainDirection;
import com.framework.runtime.application.net.transport.TransportException;

public abstract class TransactionTransport extends AbstractTransport  {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());

	public TransactionTransport(String name) {
		super(name);
	}
	
	public TransactionTransport() {
		
	}

	@Override
	protected void handleMessage(TransportMessage message, TransportChain chain) throws TransportException {
		logger.info("[" + getName() + "] handle :" + message); 
		Transaction transaction = matchTransaction(message);
		try {
			if(transaction != null) {
				transaction.request(message, new ChainDirection( chain, this, true));
			}
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

	@Override
	protected void handleBackMessage(TransportMessage message, TransportChain chain) throws TransportException {
		logger.info("[" + getName() + "] handleBack :" + message); 
		Transaction transaction = matchTransaction(message);
		try {
			if(transaction != null) {
				transaction.response(message, new ChainDirection( chain, this, false));
			}
			
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

	protected abstract Transaction matchTransaction(TransportMessage request);
	
}
