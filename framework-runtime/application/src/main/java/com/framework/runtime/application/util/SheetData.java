package com.framework.runtime.application.util;

import java.io.Serializable;
import java.util.List;

public class SheetData implements Serializable
{
	private static final long serialVersionUID = -3342239841791598049L;
	private String sheetName;// sheet名称
	private List<String> sheetTitles;// sheet中标题的集合
	private List<List<Object>> sheetContents;// sheet中的数据集合

	public SheetData()
	{
	}

	public SheetData(String sheetName, List<String> sheetTitles, List<List<Object>> sheetContents)
	{
		super();
		this.sheetName = sheetName;
		this.sheetTitles = sheetTitles;
		this.sheetContents = sheetContents;
	}

	public String getSheetName()
	{
		return sheetName;
	}

	public void setSheetName(String sheetName)
	{
		this.sheetName = sheetName;
	}

	public List<String> getSheetTitles()
	{
		return sheetTitles;
	}

	public void setSheetTitles(List<String> sheetTitles)
	{
		this.sheetTitles = sheetTitles;
	}

	public List<List<Object>> getSheetContents()
	{
		return sheetContents;
	}

	public void setSheetContents(List<List<Object>> sheetContents)
	{
		this.sheetContents = sheetContents;
	}

}
