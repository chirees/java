package com.framework.runtime.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.framework.runtime.application.xflow.CallResult;
import com.framework.runtime.application.xflow.XEventCaller;
import com.framework.runtime.application.xflow.XFlowBuilder;
import com.framework.runtime.application.xflow.XFlowException;
import com.framework.runtime.application.xflow.XFlowProcesser;
import com.framework.runtime.application.xflow.XFlowSource;
import com.framework.runtime.application.xflow.XStatusListener;
import com.framework.runtime.application.xflow.XTriggerReceiver;
import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XEventTrigger;
import com.framework.runtime.application.xflow.node.XStatus;
import com.framework.runtime.application.xflow.node.XTrigger;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:/spring/applicationContext-config.xml"}) 
public class XFlowTest {

	@Test
	public void test() throws Exception {
		XFlowBuilder.build("classpath*:/flow/*.xml");
		
		XFlowProcesser processer = XFlowBuilder.getProcesser("010001");
		processer.addTriggerReceiver(new XTriggerReceiver() {

			@Override
			public void receive(XStatus status, XFlowProcesser processer, XTrigger trigger, XFlowSource source) throws XFlowException {
				if(trigger instanceof XEventTrigger) {
					XEventTrigger xet = (XEventTrigger)trigger;
					processer.execute(status.getCode(), xet.getEvent(), new XEventCaller() {

						@Override
						public CallResult call(XStatus from, XEvent event, XFlowSource source) {
							return new CallResult(event.getTo(), source);
						}
						
					}, source);
				}
				System.out.println("trigger receive : " + trigger.getCode());
			}

			
			
		});
		processer.addStatusListener(new XStatusListener() {

			@Override
			public void statusChanged(XStatus from, XStatus to, XEvent event, XFlowSource source) {
				System.out.println("status changed : " + to);
			}
			
		});
		processer.start(new XEventCaller() {

			@Override
			public CallResult call(XStatus from, XEvent event, XFlowSource source) {
				System.out.println(from + " " + event);
				return new CallResult(event.getTo(), null);
			}
			
		});
		
//		Thread.currentThread().sleep(100000);
	}
	
}
