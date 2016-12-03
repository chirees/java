package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public class ServiceNoRequest extends AbstractRequest {
	private String no;
	
	@Override
	public void validate() throws ValidateException {

	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	
}
