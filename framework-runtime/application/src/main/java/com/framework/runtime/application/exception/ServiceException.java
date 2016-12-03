package com.framework.runtime.application.exception;

public class ServiceException extends ApplicationException {
	
    /**  */
    private static final long serialVersionUID = -8801952505203103469L;
    private boolean sysError;


    public ServiceException(boolean sysError, String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
        this.sysError = sysError;
    }

    public ServiceException(boolean sysError, String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
        this.sysError = sysError;
    }
    
    public boolean isSysError() {
    	
    	return sysError;
    }
}
