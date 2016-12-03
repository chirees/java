package com.framework.runtime.application.net.loadbalance;

import java.util.Collection;

public interface LoadbalanceAlgorithm {
	
	LoadbalanceNode calculate(String key);
	
	
	void add(LoadbalanceNode node);
	
	void remove(LoadbalanceNode node);
	
}
