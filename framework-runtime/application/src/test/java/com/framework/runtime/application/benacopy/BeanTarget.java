package com.framework.runtime.application.benacopy;

import java.util.Date;
import java.util.List;

public class BeanTarget {
	private int i=3;
	private Date d;
	private Bean2 bean;
	private int[] is = new int[]{3, 4};
	private Integer is2[];
	private Bean2 beans[];
	private List list;
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Date getD() {
		return d;
	}
	public void setD(Date d) {
		this.d = d;
	}
	public Bean2 getBean() {
		return bean;
	}
	public void setBean(Bean2 bean) {
		this.bean = bean;
	}
	public int[] getIs() {
		return is;
	}
	public void setIs(int[] is) {
		this.is = is;
	}
	public Integer[] getIs2() {
		return is2;
	}
	public void setIs2(Integer[] is2) {
		this.is2 = is2;
	}
	public Bean2[] getBeans() {
		return beans;
	}
	public void setBeans(Bean2[] beans) {
		this.beans = beans;
	}
	public List<ListObj> getList() {
		return list;
	}
	public void setList(List<ListObj> list) {
		this.list = list;
	}
	
	
}
