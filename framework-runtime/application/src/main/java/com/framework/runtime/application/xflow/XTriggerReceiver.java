package com.framework.runtime.application.xflow;

import com.framework.runtime.application.xflow.node.XStatus;
import com.framework.runtime.application.xflow.node.XTrigger;

public interface XTriggerReceiver<T> {

	void receive(XStatus status, XFlowProcesser processer, XTrigger trigger, XFlowSource<T> source) throws XFlowException;
	
}
