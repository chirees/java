package com.framework.runtime.application.xflow.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class XWhenNode {
	@Attribute
	private String value;
	@Attribute
	private String to;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
}
