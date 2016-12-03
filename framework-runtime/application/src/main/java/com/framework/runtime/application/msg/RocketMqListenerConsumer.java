package com.framework.runtime.application.msg;

import java.util.List;

import org.springframework.context.ApplicationListener;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.framework.runtime.application.LogU;
import com.framework.runtime.application.app.AppStartedEvent;

public class RocketMqListenerConsumer implements ApplicationListener<AppStartedEvent> {
	private String groupName;
	private String namesrvAddr;
	private String topic;
	private String instanceName;
	private MsgConsumer callback;

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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public MsgConsumer getCallback() {
		return callback;
	}

	public void setCallback(MsgConsumer callback) {
		this.callback = callback;
	}
	
	

	@Override
	public void onApplicationEvent(AppStartedEvent event) {
		try {
			LogU.i(this, "onApplicationEvent", "消息订阅启动");
			DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
			/**
			 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
			 * 如果非第一次启动，那么按照上次消费的位置继续消费
			 */
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.subscribe(topic, "push");
			consumer.setNamesrvAddr(namesrvAddr);
			consumer.setInstanceName(instanceName);

			consumer.registerMessageListener(new MessageListenerOrderly() {

				@Override
				public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
					context.setAutoCommit(true);
					for (MessageExt me : msgs) {
						try {
							callback.accept(me.getKeys(), new String(me.getBody()));
						} catch (Exception e) {
							LogU.e("rocket callback ", "error", e);
							// return
							// ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
						}
					}
					return ConsumeOrderlyStatus.SUCCESS;
				}
			});

			consumer.start();
		} catch (Exception e) {
			LogU.e(this, "init", e);
		}
	}

}
