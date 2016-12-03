package com.framework.runtime.application.xflow;

import java.util.HashMap;
import java.util.Map;

public class XFlowSource<T> {
	private Map attributes = new HashMap();
	private T source;
	
	public XFlowSource(T source) {
		this.source = source;
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void putAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}
	
	public T getSource() {
		return source;
	}

}
