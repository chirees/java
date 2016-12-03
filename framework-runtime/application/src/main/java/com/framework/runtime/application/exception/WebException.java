package com.framework.runtime.application.exception;

public class WebException extends ApplicationException {
    public WebException() {
        super();
    }

    public WebException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public WebException(Throwable arg0) {
        super(arg0);
    }

    public WebException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public WebException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public WebException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
