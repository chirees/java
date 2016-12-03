package com.framework.runtime.application.net.transport;

public class TransportException extends Exception {

	public TransportException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public TransportException(Throwable cause) {
		super(cause);
	}
	
	public TransportException(String msg) {
		super(msg);
	}
}
