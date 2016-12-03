package com.framework.runtime.application.exception;

public class ValidateException extends ApplicationException {

	public ValidateException() {
        super();
    }

    public ValidateException(String message) {
        super();
        this.message = message;
    }
}
