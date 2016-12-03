package com.framework.runtime.application.app;

import java.util.HashMap;
import java.util.Map;

public class ApplicationTable implements java.io.Serializable {
	private Map<String, ApplicationNode> applicationNodes = new HashMap();
	private int count = 0;

	public Map<String, ApplicationNode> getApplicationNodes() {
		return applicationNodes;
	}

	public void setApplicationNodes(Map<String, ApplicationNode> applicationNodes) {
		this.applicationNodes = applicationNodes;
	}
	
	public ApplicationNode getApplicationNode(String key) {
		return applicationNodes.get(key);
	}
	
	
	public void put(ApplicationNode node) {
		applicationNodes.put(node.getCode(), node);
		count ++;
		node.setId(count);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void check(String code, String ip, long time) {
		for(ApplicationNode node:applicationNodes.values()) {
			node.check(code, ip, time);
		}
	}
	
}
