package com.framework.runtime.application.net;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.framework.runtime.application.Application;

public class NetworkEngine implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	
	private List<TransportChain> transportChains = new ArrayList();

	public List<TransportChain> getTransportChains() {
		return transportChains;
	}

	public void setTransportChains(List<TransportChain> transportChains) {
		this.transportChains = transportChains;
	}
	
	public void addTransportChain(TransportChain transportChain) {
		transportChains.add(transportChain);
	}

	public void startup() {
		try {
			for (TransportChain transportChain : transportChains) {
				transportChain.startup();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void shutdown() {
		try {
			for (TransportChain transportChain : transportChains) {
				transportChain.shutdown();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		startup();
	}

}
