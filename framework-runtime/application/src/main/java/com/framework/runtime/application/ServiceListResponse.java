package com.framework.runtime.application;

import java.util.List;

public class ServiceListResponse<T extends List> extends AbstractResponse {
	private T list;
	private long total = 0;

	public T getList() {
		return list;
	}

	public void setList(T list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public String toString() {
		return "list size: " + total;
	}


}
