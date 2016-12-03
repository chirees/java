package com.framework.runtime.application.xflow;

import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XStatus;

public interface XStatusListener<T> {

	void statusChanged(XStatus from, XStatus to, XEvent event, XFlowSource<T>  source);
}
