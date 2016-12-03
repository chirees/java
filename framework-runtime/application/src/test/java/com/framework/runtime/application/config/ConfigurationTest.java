package com.framework.runtime.application.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author xiongliang
 *
 */

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("classpath:/spring/applicationContext-config.xml") 
public class ConfigurationTest {
	
	 
	@Test
	public void test() throws Exception {
		 Resource res2 = new ClassPathResource("flow"); 
		System.out.println(res2.getFile());
	}

}
