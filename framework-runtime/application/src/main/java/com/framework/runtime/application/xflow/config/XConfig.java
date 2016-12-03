package com.framework.runtime.application.xflow.config;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

@Root
public class XConfig {
	@ElementList(entry="flow", type=XFlowNode.class, inline=true) 
	private List<XFlowNode> flows;

	public List<XFlowNode> getFlows() {
		return flows;
	}

	public void setFlows(List<XFlowNode> flows) {
		this.flows = flows;
	}

	
	
	
}
