package com.framework.runtime.application.posp;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.runtime.application.net.TransportChain;

public class PospServer implements InitializingBean {
	
	@Autowired
	private List<TransportChain> chains;

	@Override
	public void afterPropertiesSet() throws Exception {
		if(chains != null) {
			for(TransportChain chain:chains) {
				chain.startup();
			}
		}
	}

}
