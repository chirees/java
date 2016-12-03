package com.framework.runtime.application.xflow.node;

import java.util.HashMap;
import java.util.Map;

public class XFlow {
	private String code;
	private String name;
	private Map<String, XStatus> statuses =  new HashMap();
	private XStatus startStatus;
	
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
	public XStatus getStatus(String code) {
		return statuses.get(code);
	}
	
	public void putStatus(String code, XStatus status) {
		if(status.isStarted()) {
			this.startStatus = status;
		}
		statuses.put(code, status);
	}
	public XStatus getStartStatus() {
		return startStatus;
	}
	
	public String toString() {
		return "[" + code + ", " + name + "]";
	}
	
}
