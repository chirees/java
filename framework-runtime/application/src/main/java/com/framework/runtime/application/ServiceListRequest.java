package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public class ServiceListRequest<T> extends PageRequest {
	private T data;

	@Override
	public void validate() throws ValidateException {
		// TODO Auto-generated method stub
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String toString() {
		return data + " " + this.getPage() + " " + this.getSize();
	}


}
