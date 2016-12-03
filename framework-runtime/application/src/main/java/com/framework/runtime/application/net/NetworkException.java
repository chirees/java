package com.framework.runtime.application.net;

public class NetworkException extends Exception {

	public NetworkException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public NetworkException(Throwable cause) {
		super(cause);
	}
	
	public NetworkException(String msg) {
		super(msg);
	}
}
