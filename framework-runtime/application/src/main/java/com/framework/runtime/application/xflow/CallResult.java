package com.framework.runtime.application.xflow;

import com.framework.runtime.application.xflow.node.XStatus;

public class CallResult {
	private XStatus status;
	private XFlowSource source;
	
	public CallResult(XStatus status, XFlowSource source) {
		this.status = status;
		this.source = source;
	}

	public XStatus getStatus() {
		return status;
	}

	public void setStatus(XStatus status) {
		this.status = status;
	}

	public XFlowSource getSource() {
		return source;
	}

	public void setSource(XFlowSource source) {
		this.source = source;
	}
	
	
}
