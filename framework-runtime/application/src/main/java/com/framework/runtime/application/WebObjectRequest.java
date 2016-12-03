package com.framework.runtime.application;

public class WebObjectRequest<T> extends WebRequest {
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
