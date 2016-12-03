package com.framework.runtime.application.page;


public class Pageable {
	private static final long serialVersionUID = 7280485938848398236L;

	private final int page;
	private final int size;
	
	private String orderBy;

	public Pageable(int page, int size) {

		if (page < 0) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (size < 0) {
			throw new IllegalArgumentException("Page size must not be less than zero!");
		}

		this.page = page;
		this.size = size;
	}

	public int getPageSize() {

		return size;
	}

	public int getPageNumber() {
		return page;
	}

	public int getOffset() {
		return (page - 1) * size;
	}

	public boolean hasPrevious() {
		return page > 0;
	}

	public Pageable next() {
		return new Pageable(page + 1, size);
	}

	public Pageable previousOrFirst() {
		return hasPrevious() ? new Pageable(page - 1, size) : this;
	}

	public Pageable first() {
		return new Pageable(0, size);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", page, size);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
