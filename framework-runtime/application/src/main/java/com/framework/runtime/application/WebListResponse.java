package com.framework.runtime.application;

import java.util.List;

public class WebListResponse<T> extends WebResponse {
	private Page page;
	private List<T> list;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
