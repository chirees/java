package com.framework.runtime.application.respcode;

import java.util.HashMap;
import java.util.Map;

import com.framework.runtime.application.Application;

public class RespcodeManager {
	
	private static RespcodeManager instance = new RespcodeManager();
	
	private Map<String, String> sysCodes = new HashMap();
	private Map<String, String> logicCodes = new HashMap();
	
	static {
		instance.putSysCode(RespCode.SYS_SUCCESS, "成功");
		instance.putSysCode(RespCode.SYS_DATA_DUPLICATE, "数据重复");
		instance.putSysCode(RespCode.SYS_DATA_NOEXIST, "数据不存在");
		instance.putSysCode(RespCode.SYS_DB_ERROR, "调用数据库错误");
		instance.putSysCode(RespCode.SYS_NET_TIMEOUT, "网络超时");
		instance.putSysCode(RespCode.SYS_ERROR, "系统错误");
		instance.putSysCode(RespCode.SYS_LOGIC_ERROR, "系统错误-逻辑");
		instance.putSysCode(RespCode.SYS_PARAM_WRONG, "传参数错误");
	}
	
	public static RespcodeManager getInstance() {
		return instance;
	}
	
	public String getMessage(RespCodeable response) {
		if(this.isSuccess(response)) {
			return "成功";
		}
		else {
			if(response.getLogicCode() != null && this.isLogicError(response)) {
				return response.getLogicCode().getMessage();
			}
			
			return response.getSysCode().getMessage();
		}
		
	}
	
	public void copy(RespCodeable from, RespCodeable to) {
		to.setSysCode(from.getSysCode());
		to.setLogicCode(from.getLogicCode());
	}
	
	public boolean isSuccess(RespCodeable response) {
		String code = response.getSysCode().getCode();
		if(code == null || code.length() != 6)
			return false;
		
		code = code.substring(3);
		return code.equals(RespCode.SYS_SUCCESS);
	}
	
	public boolean isSuccess(String code) {
		if(code == null || code.length() != 6)
			return false;
		
		code = code.substring(3);
		return code.equals(RespCode.SYS_SUCCESS);
	}
	
	public boolean isLogicError(RespCodeable response) {
		String code = response.getSysCode().getCode();
		if(code == null || code.length() != 6)
			return false;
		
		code = code.substring(3);
		return code.equals(RespCode.SYS_LOGIC_ERROR);
	}
	
	private void putSysCode(String code, String message) {
		sysCodes.put(code, message);
	}
	
	public void putLogicCode(String code, String message) {
		logicCodes.put(code, message);
	} 
	
	public void genLogicResp(RespCodeable response, String code, String message) {
		response.setLogicCode(new RespCode(code,  message));
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_LOGIC_ERROR, message));
	}
	
	public void genSysResp(RespCodeable response, String code, String message) {
		response.setSysCode(new RespCode(code, sysCodes.get(code) + ":" + message));
	}
	
	public void genSysSuccess(RespCodeable response) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_SUCCESS, sysCodes.get(RespCode.SYS_SUCCESS)));
	}
	
	public void genSysDataDuplicate(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_DATA_DUPLICATE, sysCodes.get(RespCode.SYS_DATA_DUPLICATE) + ":" + message));
	}
	
	public void genSysDataNoExists(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_DATA_NOEXIST, sysCodes.get(RespCode.SYS_DATA_NOEXIST) + ":" + message));
	}
	
	public void genSysDbError(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_DB_ERROR, sysCodes.get(RespCode.SYS_DB_ERROR) + ":" + message));
	}
	
	public void genSysNetTimeout(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_NET_TIMEOUT, sysCodes.get(RespCode.SYS_NET_TIMEOUT) + ":" + message));
	}
	
	public void genSysError(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_ERROR, sysCodes.get(RespCode.SYS_ERROR) + ":" + message));
	}
	
	public void genSysParamError(RespCodeable response, String message) {
		response.setSysCode(new RespCode(Application.getInstance().getCode() + RespCode.SYS_PARAM_WRONG, sysCodes.get(RespCode.SYS_PARAM_WRONG) + ":" + message));
	}
}
