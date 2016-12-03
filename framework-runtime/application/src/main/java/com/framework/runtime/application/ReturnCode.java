package com.framework.runtime.application;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReturnCode implements java.io.Serializable {
	private String code;
	private String subCode;
	private String desc;
	private String appCode;
	private String appName;
	
	public ResponseCode getResponseCode() {
		return new ResponseCode(code + subCode, desc);
	}
	
	public enum COMMON_CODE {
		SUCCESS("00", "000", "调用成功"),
		DATA_NOTEXIST("00", "001", "数据不存在"),
		NO_AUTH("00", "002", "没有权限"),
		DATA_ALREADY_EXIST("00", "003", "数据已经存在"),
		PARAM_WRONG("01", "001", "调用失败, 参数错误"),
		TIMEOUT("01", "002", "调用失败, 超时"),
		INSERT_ERROR("96", "001", "插入数据失败"),
		SERVICE_ERROR("98", "002", "调用失败, 服务出错"),
		JSON_PARAM_ERROR("98", "004", "调用失败, JSON参数错误"),
		SYSTEM_ERROR("99", "999", "调用失败, 系统内部错误"),
		TOKEN_ERROR("98", "003", "调用失败, token失效."),
		SYSTEM_RETURNCODE_ERROR("99", "888", "调用失败, 系统内部错误, 未设置返回码.");
		
		
		private String code;
		private String subCode;
		private String desc;
		
		private COMMON_CODE(String code, String subCode, String desc) {
			this.code = code;
			this.subCode = subCode;
			this.desc = desc;
		}
		
		public String toString() {
			return "[code:" + code + ", subCode:" + subCode + ", desc:" + desc + "]";
		}
		
		public  ReturnCode getReturnCode() {
			return new ReturnCode(code, subCode, desc);
		}
	}
	
	
	public ReturnCode(String code, String subCode, String desc) {
		this.code = code;
		this.subCode = subCode;
		this.desc = desc;
	}
	
	public ReturnCode() {
		
	}
	
	public boolean isSuccess() {
		return code != null && code.equals(COMMON_CODE.SUCCESS.code) && subCode != null && subCode.equals(COMMON_CODE.SUCCESS.subCode);
	}
	
	public boolean isFail() {
		return code != null && !code.equals(COMMON_CODE.SUCCESS.code) && !code.equals(COMMON_CODE.SYSTEM_ERROR.code);
	}
	
	public boolean isError() {
		return code != null && code.equals(COMMON_CODE.SYSTEM_ERROR.code);
	}
	
	public  ReturnCode getMappedReturnCode() {
		return Application.getInstance().getMappedReturnCode(this);
	}
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}



	public String getAppName() {
		return appName;
	}



	public void setAppName(String appName) {
		this.appName = appName;
	}



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.DEFAULT_STYLE)
		.append("系统码",getAppCode())
		.append("系统名",getAppName())
		.append("返回码",getCode())
		.append("返回子码",getSubCode())
		.append("返回信息",getDesc()).toString();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ReturnCode == false) return false;
		if(this == obj) return true;
		ReturnCode other = (ReturnCode)obj;
		return new EqualsBuilder()
			.append(this.getCode(), other.getCode())
			.append(this.getSubCode(), other.getSubCode())
			.isEquals();
	}
	
}
