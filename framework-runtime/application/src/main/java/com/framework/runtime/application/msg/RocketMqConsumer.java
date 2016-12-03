package com.framework.runtime.application.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.framework.runtime.application.LogU;
import com.framework.runtime.application.msg.QueueConsumer.EventJobWorker;

import kafka.consumer.ConsumerIterator;
import kafka.message.MessageAndMetadata;

public class RocketMqConsumer implements InitializingBean {
	private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();
	
	private DefaultMQPullConsumer  consumer ;
	private String groupName;
	private String namesrvAddr;
	private String topic;
	protected ExecutorService executor;
	private String instanceName;
	
	class EventJobWorker implements Runnable {

        private Set<MessageQueue> mqs;
        private MsgConsumer callback;

        public EventJobWorker(MsgConsumer cb, Set<MessageQueue> mqs) {
        	this.mqs = mqs;
            callback = cb;
        }

        public void run() {
        	try {
                // 必须加上此监听才能在消费过后，自动回写消费进度
                consumer.registerMessageQueueListener(topic, null);
                //循环每一个队列
                for (MessageQueue mq : mqs) {
                    int cnter = 0;
                //每个队列里无限循环，分批拉取未消费的消息，直到拉取不到新消息为止
                    SINGLE_MQ: while (cnter++ < 100) {
                        long offset = consumer.fetchConsumeOffset(mq, false);
                        offset = offset < 0 ? 0 : offset;
                        PullResult result = consumer.pull(mq, null, offset, 10);

                        SINGLE_SW:switch (result.getPullStatus()) {
                        case FOUND:
                            if (result.getMsgFoundList() != null) {
                                int prSize = result.getMsgFoundList().size();
                                if (prSize != 0) {
                                    for (MessageExt me : result.getMsgFoundList()) {
                                        // 消费每条消息，如果消费失败，比如更新数据库失败，就重新再拉一次消息
                                    	try {
                                    		callback.accept(me.getKeys(), new String(me.getBody()));
                                    	} catch (Exception e) {
                                    		break SINGLE_SW;
                                    	}
                                    }

                                }
                            }
                            // 获取下一个下标位置
                            offset = result.getNextBeginOffset();
                            // 消费完后，更新消费进度
                            consumer.updateConsumeOffset(mq, offset);
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            //拉取不到新消息，跳出 SINGLE_MQ 当前队列循环，开始下一队列循环。
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogU.e("RocketMq", "error", e);
            }
           
        	executor.submit(new EventJobWorker(callback, mqs));
        }

    }
	
	private MsgConsumer callback;
	
	public void start() throws Exception {
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic);  
		executor.submit(new EventJobWorker(callback, mqs));
	}
	
	
	private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offseTable.put(mq, offset);
    }


    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offseTable.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = Executors.newFixedThreadPool(2);
		consumer =  new DefaultMQPullConsumer(groupName);  
        consumer.setNamesrvAddr(namesrvAddr); 
        consumer.setInstanceName(instanceName);  
        consumer.start();  
        start();
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public MsgConsumer getCallback() {
		return callback;
	}


	public void setCallback(MsgConsumer callback) {
		this.callback = callback;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getNamesrvAddr() {
		return namesrvAddr;
	}


	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}


	public String getInstanceName() {
		return instanceName;
	}


	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	
	
	
}
