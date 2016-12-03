package com.framework.runtime.application.xflow.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XStatus {
	private String code;
	private String name;
	private boolean started = false;
	private Map<String, XEvent> events = new HashMap();
	private List<XTrigger> triggers = new ArrayList();
	
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
	public XEvent getEvent(String code) {
		return events.get(code);
	}
	public void putEvent(String code, XEvent event) {
		events.put(code, event);
	}
	
	public List<XTrigger> getTriggers() {
		return triggers;
	}
	
	public void addTrigger(XTrigger trigger) {
		this.triggers.add(trigger);
	}
	
	public String toString() {
		return "Status[" + code + ", " + name + "]";
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	
	
}
