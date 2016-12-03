package com.framework.runtime.application.process;

import au.com.ds.ef.EventEnum;

public class Event implements EventEnum {
	private String code;
	private String name;
	
	private Status toStatus;
	
	private String validate;
	
	private String fail;
	
	
	public String validate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	@Override
	public String code() {
		return code;
	}
	
	public String toString() {
		return "Event-{" + code + ", " + name + ", " + toStatus + "}";
	}

	@Override
	public String name() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getToStatus() {
		return toStatus;
	}

	public void setToStatus(Status toStatus) {
		this.toStatus = toStatus;
	}

	public String fail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}
	
	
	
	
}
