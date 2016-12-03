package com.framework.runtime.application.msg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;

import com.framework.runtime.application.LogU;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;


public class QueueConsumer implements InitializingBean {

    class EventJobWorker implements Runnable {

        private KafkaStream<byte[], byte[]> m_stream;
        private int m_threadNumber;
        private MsgConsumer callback;

        public EventJobWorker(MsgConsumer cb, KafkaStream<byte[], byte[]> a_stream, int a_threadNumber) {
            m_threadNumber = a_threadNumber;
            m_stream = a_stream;
            callback = cb;
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
            while (it.hasNext()) {
                try {
                	MessageAndMetadata<byte[], byte[]> md = it.next();
                	String key = new String(md.key());
                	String value = new String(md.message());
                	callback.accept(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            LogU.i("MESSAGE ", "consumer thread end!!!!!!!!!!!!!!!!!");
        }

    }

    protected ConsumerConnector consumer;

    protected ExecutorService executor;
    
    protected String topic;
    protected String groupid;
    protected MsgConsumer callback;
    protected int totalThread;
    private String zk_addrs;
    
    public QueueConsumer(String zk_addrs, String topic, String groupid, MsgConsumer callback, int totalThread, ExecutorService executor) {
    	this.topic = topic;
    	this.groupid = groupid;
    	this.callback = callback;
    	this.totalThread = totalThread;
    	this.executor = executor;
    	this.zk_addrs = zk_addrs;
    	init();
    }
    
    public QueueConsumer() {
    	
    }
    
    private void init() {
    	Properties props = new Properties();
        props.put("zookeeper.connect", zk_addrs);
        props.put("group.id", groupid);
        props.put("zookeeper.session.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        ConsumerConfig cfg = new ConsumerConfig(props);
        
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(cfg);
    }
    

    public void start() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(totalThread));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        
        if (executor == null) {
        	executor = Executors.newFixedThreadPool(totalThread);
        }
        
        int threadNumber = 0;
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            executor.submit(new EventJobWorker(callback, stream, threadNumber));
            LogU.i("MESSAGE ", "add listener ... " + threadNumber + " for [" + topic + "]");
            threadNumber++;
        }
    }


	public ConsumerConnector getConsumer() {
		return consumer;
	}


	public void setConsumer(ConsumerConnector consumer) {
		this.consumer = consumer;
	}


	public ExecutorService getExecutor() {
		return executor;
	}


	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getGroupid() {
		return groupid;
	}


	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public MsgConsumer getCallback() {
		return callback;
	}


	public void setCallback(MsgConsumer callback) {
		this.callback = callback;
	}


	public int getTotalThread() {
		return totalThread;
	}


	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}
	
	

	public String getZk_addrs() {
		return zk_addrs;
	}

	public void setZk_addrs(String zk_addrs) {
		this.zk_addrs = zk_addrs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init() ;
		start();
	}
    
    

}
