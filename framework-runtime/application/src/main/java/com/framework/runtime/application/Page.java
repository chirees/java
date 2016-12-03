package com.framework.runtime.application;

public class Page
{

	private long totalCount = 0;
	private int numPerPage = 10;
	private int pageNum = 1;

	public Page()
	{
	}

	public Page(long totalCount, int numPerPage, int pageNum)
	{
		super();
		this.totalCount = totalCount;
		this.numPerPage = numPerPage;
		this.pageNum = pageNum;
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getNumPerPage()
	{
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage)
	{
		this.numPerPage = numPerPage;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

}
