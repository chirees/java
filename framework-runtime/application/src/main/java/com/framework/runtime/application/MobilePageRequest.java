package com.framework.runtime.application;

import com.framework.runtime.application.exception.ValidateException;

public class MobilePageRequest extends AbstractRequest {
	private Long id;
	private boolean isBigger = true;
	private int limit;

	@Override
	public void validate() throws ValidateException {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isBigger() {
		return isBigger;
	}

	public void setBigger(boolean isBigger) {
		this.isBigger = isBigger;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	

}
