package com.framework.runtime.application.process.config;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

@Root
public class FlowNode {
	@ElementList(entry="process", type=ProcessNode.class, inline=true) 
	private List<ProcessNode> processes;

	public List<ProcessNode> getProcesses() {
		return processes;
	}

	public void setProcesses(List<ProcessNode> processes) {
		this.processes = processes;
	}
	
	
}
