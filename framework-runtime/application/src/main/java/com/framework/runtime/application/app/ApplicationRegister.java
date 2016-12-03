package com.framework.runtime.application.app;

import com.framework.runtime.application.exception.ApplicationException;

public interface ApplicationRegister {

	void login(String code, String name, String ip) throws ApplicationException ;
	
	void start(String ip);
}
