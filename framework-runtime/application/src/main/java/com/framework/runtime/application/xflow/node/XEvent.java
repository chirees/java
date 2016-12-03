package com.framework.runtime.application.xflow.node;

import java.util.HashMap;
import java.util.Map;

public class XEvent {
	private String code;
	private String name;
	private XStatus to;
	
	private Map<String, XStatus> xtos = new HashMap();
	
	public static final String WHEN_SUCCESS = "success";
	public static final String WHEN_FAIL = "fail";
	
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
	
	public String toString() {
		return "Event[" + code + ", " + name + ", to=" + to + "]";
	}
	public XStatus getTo() {
		return to;
	}
	public void setTo(XStatus to) {
		this.to = to;
	}
	
	public XStatus get(String when) {
		return xtos.get(when);
	}
	
	public void put(String when, XStatus xto) {
		xtos.put(when, xto);
	}
	
	public boolean containsTo(XStatus to) {
		
		return to == this.to || this.xtos.containsValue(to);
	}
	
	public boolean isMulti() {
		return to == null && !this.xtos.isEmpty();
	}
	
}
