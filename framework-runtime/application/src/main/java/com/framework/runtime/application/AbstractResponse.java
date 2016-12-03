package com.framework.runtime.application;

import com.framework.runtime.application.respcode.RespCode;
import com.framework.runtime.application.respcode.RespCodeable;

public class AbstractResponse implements java.io.Serializable, RespCodeable {
	
	private RespCode sysCode;
	private RespCode logicCode;
	private boolean sync;
	
	

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
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

	
	
	
}
