package com.framework.runtime.application.process.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
@Root
public class EventNode {
	@Attribute
	private String code;
	@Attribute
	private String name;
	@Attribute
	private String toStatus;
	@Attribute(required=false)
	private String validate;
	@Attribute(required=false)
	private String fail;
	
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
	public String getToStatus() {
		return toStatus;
	}
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getFail() {
		return fail;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}
	
	
}
