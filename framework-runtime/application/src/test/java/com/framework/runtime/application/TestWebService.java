package com.framework.runtime.application;

import com.framework.runtime.application.exception.ServiceException;

public class TestWebService {
	
	@WebMethod
	public AbstractResponse testWebService(String s, int i) {
		System.out.println(s + " " + i);
//		throw new ServiceException();
		return null;
	}
}
