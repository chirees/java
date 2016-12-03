package com.framework.runtime.application.exception;

public class RemoteException extends ApplicationException {
	
    /**  */
    private static final long serialVersionUID = 1L;

    public RemoteException() {
        super();
    }

    public RemoteException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public RemoteException(Throwable arg0) {
        super(arg0);
    }

    public RemoteException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public RemoteException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public RemoteException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
	
} 
