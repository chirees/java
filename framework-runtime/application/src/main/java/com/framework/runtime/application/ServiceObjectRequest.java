package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public class ServiceObjectRequest<T> extends AbstractRequest {
	private T data;

	@Override
	public void validate() throws ValidateException {
		if(data == null)
			throw new ValidateException("请求对象 不能为空.");
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String toString() {
		return data + " ";
	}

}
