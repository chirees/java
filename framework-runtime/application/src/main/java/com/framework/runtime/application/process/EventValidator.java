package com.framework.runtime.application.process;

import com.framework.runtime.application.respcode.RespCodeable;

import au.com.ds.ef.EventEnum;
import au.com.ds.ef.StateEnum;

public interface EventValidator {

	boolean validate(EventEnum event, StateEnum from, StateEnum to, Object data, RespCodeable resp);
	
}
