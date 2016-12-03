package com.framework.runtime.application.net.loadbalance;

import java.util.Collection;


public class AlgorithmManager {
	private LoadbalanceAlgorithm algorithm = new ConsistentHash (new HashFunction(), 160);
	
	
	public LoadbalanceNode calculate(String key) {
		return algorithm.calculate(key);
	}
	
	public void add(LoadbalanceNode node) {
		algorithm.add(node);
	}
	
	public void remove(LoadbalanceNode node) {
		algorithm.remove(node);
	}
}
