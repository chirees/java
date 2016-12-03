package com.framework.runtime.application.msg;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.framework.runtime.application.LogU;

public class RocketMqProducer implements InitializingBean, MsgProducer {
	
	private TransactionMQProducer producer;
	private String groupName;
	private String namesrvAddr;
	private String instanceName;
	
	
	
	

	public String getInstanceName() {
		return instanceName;
	}



	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}



	public TransactionMQProducer getProducer() {
		return producer;
	}



	public void setProducer(TransactionMQProducer producer) {
		this.producer = producer;
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

	public boolean send(String topic, String key, String value) {
		try {
			LogU.n("rockt send " + topic, key, value);
			Message msg = new Message(topic,  "push", key, value.getBytes() );
            SendResult sendResult = producer.send(msg);
            boolean result = sendResult.getSendStatus() == SendStatus.SEND_OK;
            LogU.n(this, "send result", sendResult.toString());
            return result;
		} catch (Exception e) {
			LogU.e("RocketMq", "send", e);
			return false;
		}
	}
	
	public boolean sendOrder(String topic, String key, String value, final long orderId) {
		try {
			LogU.n("rockt send by order" + topic, key, value);
			Message msg = new Message(topic,  "push", key, value.getBytes() );
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                	Long id = (Long) arg;
                	int index = (int)(id % mqs.size());
                    return mqs.get(index);
                }
            }, orderId);
            LogU.n(this, "sendOrder result", sendResult.toString());
	            return sendResult.getSendStatus() == SendStatus.SEND_OK;
		} catch (Exception e) {
			LogU.e("RocketMq", "send", e);
			return false;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initClient();
	}
	
	private void initClient() throws Exception {
		producer = new TransactionMQProducer(groupName);  
        producer.setNamesrvAddr(namesrvAddr);  
        producer.setInstanceName(instanceName);  
        producer.start();  
        //服务器回调Producer，检查本地事务分支成功还是失败  
        producer.setTransactionCheckListener( new TransactionCheckListener() {  
              
            public LocalTransactionState checkLocalTransactionState(MessageExt msg) {  
                System.out.println("checkLocalTransactionState --"+new String(msg.getBody()));  
                return LocalTransactionState.COMMIT_MESSAGE;  
            }  
        });  
	}
}
