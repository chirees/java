package com.framework.runtime.application.net.loadbalance;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash implements LoadbalanceAlgorithm {

	private final HashFunction hashFunction;
	private final int numberOfReplicas; // 虚拟节点
	private final SortedMap<Long, LoadbalanceNode> circle = new TreeMap<Long, LoadbalanceNode>(); // 用来存储虚拟节点hash值
																		// 到真实node的映射

	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<LoadbalanceNode> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;

		for (LoadbalanceNode node : nodes) {
			add(node);
		}
	}
	
	
	
	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;
	}
	
	

	public void add(LoadbalanceNode node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}

	public void remove(LoadbalanceNode node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}

	/**
	 * 获得一个最近的顺时针节点
	 * 
	 * @param key
	 *            为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
	 * @return
	 */
	public LoadbalanceNode get(String key) {
		if (circle.isEmpty()) {
			return null;
		}
		long hash = hashFunction.hash( key);
		if (!circle.containsKey(hash)) {
			SortedMap<Long, LoadbalanceNode> tailMap = circle.tailMap(hash); // //返回此映射的部分视图，其键大于等于
																// hash
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	public long getSize() {
		return circle.size();
	}
	
	

	@Override
	public LoadbalanceNode calculate(String key) {
		return get(key);
	}



}
