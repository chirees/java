package com.framework.runtime.application.posp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.trans.Transaction;
import com.framework.runtime.application.net.trans.TransactionException;
import com.framework.runtime.application.net.transport.ChainDirection;

public abstract class AbstractPospTransaction implements Transaction {
	
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getCoreLogger());

	@Override
	public void request(TransportMessage message, ChainDirection direct) throws TransactionException {
		
		logger.info("[" + getName() + "] 处理交易请求, " + message);
		
		doRequest((PospTransportMessage)message, direct);
	}

	@Override
	public void response(TransportMessage message, ChainDirection direct) throws TransactionException {
		doResponse((PospTransportMessage)message, direct);
	}

	@Override
	public boolean match(TransportMessage message) throws Exception {
		return doMatch((PospTransportMessage)message);
	}
	
	protected abstract boolean doMatch(PospTransportMessage message) throws Exception ;
	
	public abstract void doRequest(PospTransportMessage message, ChainDirection direct) throws TransactionException ;
	
	public abstract void doResponse(PospTransportMessage message, ChainDirection direct) throws TransactionException;
	
	

}
