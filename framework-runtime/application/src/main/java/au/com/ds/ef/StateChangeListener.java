package au.com.ds.ef;

import java.util.Map;

import com.framework.runtime.application.respcode.RespCodeable;

public interface StateChangeListener {

	void stateChanged(StateEnum status, EventEnum event, Object context, RespCodeable resp);
	
	void finished(StateEnum status, EventEnum event, Object context, RespCodeable resp);
	
	void error(StateEnum status, EventEnum event, String message, Throwable error);
}
