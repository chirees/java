package com.framework.runtime.application.process.config;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class TriggerNode {
	@Attribute
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
