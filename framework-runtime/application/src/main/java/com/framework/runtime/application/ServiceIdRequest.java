package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public class ServiceIdRequest<T extends Number> extends AbstractRequest {
	private T id;

	@Override
	public void validate() throws ValidateException {
		if(id == null)
			throw new ValidateException("id 不能为空.");
		
		if(id instanceof Integer && id.intValue() < 0) {
			throw new ValidateException("id 不能小于0.");
		}
		else if(id instanceof Long && id.longValue() < 0) {
			throw new ValidateException("id 不能小于0.");
		}
			
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
	public String toString() {
		return id + " ";
	}

}
