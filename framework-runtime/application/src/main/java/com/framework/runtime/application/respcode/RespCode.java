package com.framework.runtime.application.respcode;

public class RespCode implements java.io.Serializable {
	
	public static final String SYS_SUCCESS = "000";
	public static final String SYS_NET_TIMEOUT = "001";
	public static final String SYS_DB_ERROR = "002";
	public static final String SYS_DATA_NOEXIST = "004";
	public static final String SYS_DATA_DUPLICATE = "005";
	public static final String SYS_ERROR = "777";
	public static final String SYS_LOGIC_ERROR = "999";
	
	public static final String SYS_LOGIC_NOTCONFIRM = "900";
	
	public static final String SYS_PARAM_WRONG = "006";
	
	private String code;
	private String message;
	
	
	public RespCode(String code, String message) {
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
		return "RESPCode[" + code + ", " + message + "]";
	}
	
	public boolean isSuccess() {
		String code = getCode();
		if(code == null || code.length() != 6)
			return false;
		
		code = code.substring(3);
		return code.equals(RespCode.SYS_SUCCESS);
	}
	
	
}
