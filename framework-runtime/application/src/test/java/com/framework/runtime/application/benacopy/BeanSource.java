package com.framework.runtime.application.benacopy;

import java.util.Date;
import java.util.List;

public class BeanSource {
	
	private int i=5;
	private Date d = new Date();
	private Bean1 bean = new Bean1();
	private int[] is = new int[]{1, 2};
	private Integer is2[] = new Integer[] {22,33};
	private Bean1 beans[] = new Bean1[]{new Bean1()};
	private List<ListObj> list;
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
	public Bean1 getBean() {
		return bean;
	}
	public void setBean(Bean1 bean) {
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
	public Bean1[] getBeans() {
		return beans;
	}
	public void setBeans(Bean1[] beans) {
		this.beans = beans;
	}
	public List<ListObj> getList() {
		return list;
	}
	public void setList(List<ListObj> list) {
		this.list = list;
	}
	
	
}
