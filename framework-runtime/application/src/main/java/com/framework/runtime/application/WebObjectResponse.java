package com.framework.runtime.application;

public class WebObjectResponse<T> extends WebResponse {
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
