package com.framework.runtime.application.exception;

/**
 * 
 * @author yyl
 * @version $Id: ExcelException.java, v 0.1 2014-6-5 上午11:20:19 yyl Exp $
 */
public class ExcelException extends RuntimeException {
  
    public ExcelException(String message) {  
        super(message);  
        // TODO Auto-generated constructor stub  
    }  
  
    public ExcelException(Throwable cause) {  
        super(cause);  
        // TODO Auto-generated constructor stub  
    }  
  
    public ExcelException(String message, Throwable cause) {  
        super(message, cause);  
        // TODO Auto-generated constructor stub  
    }  
}
