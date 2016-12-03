package com.framework.runtime.application.respcode;

public abstract class AbstractRespCode implements RespCodeable, java.io.Serializable {
	private RespCode sysCode;
	private RespCode logicCode;
	private boolean sync;
	
	public AbstractRespCode(RespCode sysCode, RespCode logicCode) {
		this.sysCode = sysCode;
		this.logicCode = logicCode;
	}
	
	public AbstractRespCode() {
		
	}
	
	public RespCode getSysCode() {
		return sysCode;
	}
	public void setSysCode(RespCode sysCode) {
		this.sysCode = sysCode;
	}
	public RespCode getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(RespCode logicCode) {
		this.logicCode = logicCode;
	}
	@Override
	public String respString() {
		return " S:" + getSysCode() + ", L" + getLogicCode();
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
	
	
	
}
