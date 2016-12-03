package com.framework.runtime.application.xflow.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class XTriggerNode {
	@Attribute
	private String code;
	@Attribute(required=false)
	private String event;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	
	
	
}
