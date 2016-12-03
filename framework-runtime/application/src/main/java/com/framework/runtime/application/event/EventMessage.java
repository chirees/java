package com.framework.runtime.application.event;

import java.util.EventObject;

public class EventMessage extends EventObject {
	private String key;

	public EventMessage(String key, EventSource source) {
		super(source);
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

}
