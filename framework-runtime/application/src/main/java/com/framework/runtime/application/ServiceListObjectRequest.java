package com.framework.runtime.application;

import java.util.List;

import com.framework.runtime.application.exception.ValidateException;

public class ServiceListObjectRequest<T> extends AbstractRequest {
	private List<T> list;
	
	
	@Override
	public void validate() throws ValidateException {
		if(list == null || list.isEmpty())
			throw new ValidateException("请求对象 不能为空.");
	}


	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}
	
	public String toString() {
		return list == null ? "null" : list.toString();
	}

	
}
