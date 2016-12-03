package com.framework.runtime.application.xflow;

import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XStatus;

public interface XEventCaller {
	
	CallResult call(XStatus from, XEvent event, XFlowSource source) throws XFlowException;
	
	
}
