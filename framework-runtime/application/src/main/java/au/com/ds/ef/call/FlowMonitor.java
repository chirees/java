package au.com.ds.ef.call;

import com.framework.runtime.application.respcode.RespCodeable;

import au.com.ds.ef.EventEnum;
import au.com.ds.ef.StateEnum;

public interface FlowMonitor {

	void started(StateEnum state, Object context, RespCodeable resp);
	
	boolean doEvent(EventEnum event, StateEnum from, StateEnum to, Object context, RespCodeable resp) throws Exception;
	
	boolean eventFail(EventEnum event, StateEnum from, StateEnum to, Object context, RespCodeable resp) throws Exception;
	
	void stateChanged(StateEnum state, EventEnum event, Object context, RespCodeable resp);
	
	void errored(StateEnum status, EventEnum event, String message, Throwable error);
	
}
