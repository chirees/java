package com.framework.runtime.application.net;

import java.util.Collection;
import java.util.Stack;

import com.framework.runtime.application.net.transport.TransportId;
import com.framework.runtime.application.util.NumberUtil;

public class TransportMessage<T> {
	
	private T data;
	private Stack<TransportId> ids = new Stack();
	private String ukey;
	private TransportId currentId;
	
	public TransportMessage() {
		
	}
	
	
	
	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}



	public String toString() {
		return ids.toString() + ", data = " + printData();
	}
	
	protected String printData() {
		if(data instanceof byte[]) {
			return NumberUtil.toHex((byte[])data);
		}
		
		return null;
	}
	
	public TransportMessage(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public void push(TransportId id) {
		ids.push(id);
	}
	
	public TransportId pop() {
		return ids.pop();
	}
	
	public TransportId peek() {
		return ids.peek();
	}
	
	public boolean isEmpty() {
		return ids.isEmpty();
	}
	
	public Collection<TransportId> allIds() {
		return ids;
	}
	
	public void addAllIds(Collection<TransportId> ids) {
		this.ids.addAll(ids);
	}



	public TransportId getCurrentId() {
		return currentId;
	}

	public void rsetCurrentId() {
		this.currentId = this.ids.pop();
	}
	
	
}
