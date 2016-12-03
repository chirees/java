package com.framework.runtime.application.net.transport;

public class TransportId {
	private String id;
	private String childId;
	
	public TransportId(String id, String childId) {
		this.id = id;
		this.childId = childId;
	}
	
	public TransportId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public String toString() {
		return "[" + id + ":" + (childId == null ? "" : childId) + "]";
	}
	
}
