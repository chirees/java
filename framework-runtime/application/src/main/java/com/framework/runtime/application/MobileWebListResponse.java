package com.framework.runtime.application;

import java.util.List;

public class MobileWebListResponse <T> extends WebResponse {
	private List<T> list;
	private int total;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
