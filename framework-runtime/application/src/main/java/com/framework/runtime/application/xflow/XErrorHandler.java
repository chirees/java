package com.framework.runtime.application.xflow;

import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XStatus;

public interface XErrorHandler<T>  {

	void error(Throwable e, String message, XStatus status, XEvent event, XFlowSource<T>  source);
}
