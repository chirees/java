package com.framework.runtime.application.process;

import au.com.ds.ef.EventEnum;
import au.com.ds.ef.StateEnum;

public class TriggerEvent {
	private String value;
	private StateEnum status;
	private EventEnum event;
	private Object data;
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public StateEnum getStatus() {
		return status;
	}
	public void setStatus(StateEnum status) {
		this.status = status;
	}
	public EventEnum getEvent() {
		return event;
	}
	public void setEvent(EventEnum event) {
		this.event = event;
	}
	
	
}
