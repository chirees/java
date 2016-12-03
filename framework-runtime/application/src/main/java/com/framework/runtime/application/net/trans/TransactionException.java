package com.framework.runtime.application.net.trans;

public class TransactionException extends Exception {

	public TransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public TransactionException(Throwable cause) {
		super(cause);
	}
	
	public TransactionException(String msg) {
		super(msg);
	}
}
