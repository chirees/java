package com.framework.runtime.application.net.trans;

import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.transport.ChainDirection;

public interface Transaction {

	void request(TransportMessage message, ChainDirection direct) throws TransactionException;
	
	void response(TransportMessage message, ChainDirection direct) throws TransactionException;
	
	boolean match(TransportMessage message) throws Exception;
	
	String getName();
	
	String getCode();
}
