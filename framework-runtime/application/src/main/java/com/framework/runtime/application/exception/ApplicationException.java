package com.framework.runtime.application.exception;

public class ApplicationException extends RuntimeException {


    /**  */
    private static final long serialVersionUID = 734800406789120008L;

    protected String          errorCode;

    protected String          message;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable arg0) {
        super(arg0);
    }

    public ApplicationException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApplicationException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     * 
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     * 
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
