package com.framework.runtime.application;

public class ServiceObjectResponse<T> extends AbstractResponse {
	private T data;
	
	public ServiceObjectResponse(T data) {
		this.data = data;
	}
	
	public ServiceObjectResponse() {
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String toString() {
		return data + " " + super.toString();
	}
	
	
}
