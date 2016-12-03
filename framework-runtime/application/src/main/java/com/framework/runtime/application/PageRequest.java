package com.framework.runtime.application;

import com.framework.runtime.application.page.Pageable;

public abstract class PageRequest extends AbstractRequest {
	private boolean paged = true;
	private int page = 0;
	private int size = 20;
	
	
	
	public boolean isPaged() {
		return paged;
	}
	public void setPaged(boolean paged) {
		this.paged = paged;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public Pageable getPageable() {
		return new Pageable(page, size);
	}

}
