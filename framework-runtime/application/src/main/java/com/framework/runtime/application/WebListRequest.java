package com.framework.runtime.application;

public class WebListRequest<T> extends WebRequest {
	private Page page = new Page();
	private T data;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
