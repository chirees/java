package com.framework.runtime.application;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import com.alibaba.tamper.BeanCopy;
import com.framework.runtime.application.benacopy.BeanSource;
import com.framework.runtime.application.benacopy.BeanTarget;
import com.framework.runtime.application.benacopy.ListObj;
import com.framework.runtime.application.util.BeanCopyUtil;

public class BeanCopyTest {

	public static boolean isWrapClass(Class clz) { 
        try { 
           return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) { 
            return false; 
        } 
    } 
	
	@Test
	public void test() throws Exception {
		BeanSource source = new BeanSource();
		List<ListObj> list = new ArrayList();
		ListObj obj = new ListObj();
		obj.setI(111);
		obj.setS("ssss");
		list.add(obj);
		source.setList(list);
		
		BeanTarget target = new BeanTarget();
		BeanCopyUtil.copyUseCglib(source, target);
		
		System.out.println(target.getList());
	}
	
	public void test2() {
		BeanSource source = new BeanSource();
		BeanTarget target = new BeanTarget();
		BeanCopy srcCopy    = BeanCopy.create(BeanSource.class, BeanTarget.class);  
//		BeanUtils.copyProperties(source, target);
		srcCopy.copy(source, target);
		System.out.println(target.getBean() + " " +target.getI());
	}
	
	public void test3() throws Exception {
		BeanSource source = new BeanSource();
		BeanTarget target = new BeanTarget();
		
		PropertyUtils.copyProperties(source, target);
		
		System.out.println(target.getBean().getS());
	}
}
