package com.framework.runtime.application.posp;

import org.jpos.iso.ISOMsg;

import com.framework.runtime.application.net.TransportMessage;

public class PospTransportMessage extends TransportMessage<ISOMsg> {
	private String tpdu;
	private String header;

	public PospTransportMessage(ISOMsg data) {
		super(data);
	}

	public String getTpdu() {
		return tpdu;
	}

	public void setTpdu(String tpdu) {
		this.tpdu = tpdu;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public ISOMsg getMessage() {
		return (ISOMsg) getData();
	}

	protected String printData() {
		return getMessage().toString();
	}
	
	public String getString(int no) {
		return this.getMessage().getString(no);
	}
	
	public String getString(String path) {
		return this.getMessage().getString(path);
	}
	
	public int getInt(int no) {
		try {
			return Integer.parseInt(this.getMessage().getString(no).trim());
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int getInt(String path) {
		try {
			return Integer.parseInt(this.getMessage().getString(path).trim());
		} catch (Exception e) {
			return -1;
		}
	}
	
	public boolean hasField(int no) {
		return this.getMessage().hasField(no);
	}
	
	public boolean hasField(String path) {
		try {
			return this.getMessage().hasField(path);
		} catch (Exception e) {
			return false;
		}
	}
}
