package com.framework.runtime.application;

public class ResponseCode implements java.io.Serializable {
	private String code;
	private String message;
	
	public ResponseCode() {
		
	}
	
	public ResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "[code:" + code + ", message:" + message + "]";
	}
	
}
