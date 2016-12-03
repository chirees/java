package com.framework.runtime.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath:/spring/applicationContext-config.xml") 
public class WebServiceTest {
	@Autowired
	private TestWebService testWebService;
	
	@Test
	public void test() {
		System.out.println(testWebService.testWebService("test", 1));
	}
}
