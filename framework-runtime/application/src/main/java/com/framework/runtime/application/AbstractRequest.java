package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public abstract class AbstractRequest implements java.io.Serializable {
	private String requestTraceId;
	
	public abstract void validate() throws ValidateException;

	public String getRequestTraceId() {
		return requestTraceId;
	}

	public void setRequestTraceId(String requestId) {
		this.requestTraceId = requestId;
	}
	
	
	
}
