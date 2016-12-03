package com.framework.runtime.application.xflow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.xflow.node.XEvent;
import com.framework.runtime.application.xflow.node.XFlow;
import com.framework.runtime.application.xflow.node.XStartEvent;
import com.framework.runtime.application.xflow.node.XStatus;
import com.framework.runtime.application.xflow.node.XTrigger;

public class XFlowProcesser {
	private XFlow xflow;
	private Executor excuter;
	private List<XStatusListener> listeners = new CopyOnWriteArrayList();
	private List<XTriggerReceiver> triggerReceivers = new CopyOnWriteArrayList();
	private XErrorHandler errorHandler;
	
	public XFlowProcesser(Executor excuter, XFlow xflow) {
		this.excuter = excuter;
		this.xflow = xflow;
	}
	
	
	
	public XFlow getXflow() {
		return xflow;
	}



	public void addStatusListener(XStatusListener listener) {
		listeners.add(listener);
	}
	
	public void addTriggerReceiver(XTriggerReceiver triggerReceiver) {
		triggerReceivers.add(triggerReceiver);
	}
	
	
	
	public void setErrorHandler(XErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private void sendTrigger(XStatus status, XTrigger trigger, final XFlowSource source) throws XFlowException {
		for(XTriggerReceiver receiver:triggerReceivers) {
			receiver.receive(status, this, trigger, source);
		}
	}
	
	private void fireStatusChangeEvent(XStatus from, XStatus to, XEvent event, XFlowSource source) throws XFlowException {
		for(XStatusListener listener:listeners) {
			listener.statusChanged(from, to, event, source);
		}
		
		if(to.getTriggers() != null && !to.getTriggers().isEmpty()) {
			for(XTrigger trigger:to.getTriggers()) {
				sendTrigger(to, trigger, source);
			}
		}
	}
	
	public void start(final XEventCaller caller) throws XFlowException {
		final XStatus status = xflow.getStartStatus();
		
		if(status == null) {
			throw new XFlowException("流程中没有开始状态,流程：" + xflow);
		}
		
		final XEvent event = new XStartEvent(status);
		
		excuter.execute(new Runnable() {

			@Override
			public void run() {
				try {
					LogU.i(XFlowProcesser.this, "开始流程", "流程" + xflow.toString() + ","  + status + ","  + event);
					CallResult result = caller.call(null, event, null);
					if(result.getStatus() == null)
						return;
					if(result.getStatus() == status || result.getStatus().getCode().equals(status.getCode())) {
						fireStatusChangeEvent(null, result.getStatus(), event, result.getSource());
					}
				} catch (XFlowException e) {
					errorHandler.error(e, "开始流程发生错误", status, event, null);
				} catch (Exception e) {
					errorHandler.error(e, "开始流程发生错误", status, event, null);
				}
			}
			
		});
		
	}
	
	public void execute(String statusCode, String eventCode, final XEventCaller caller, final XFlowSource source) throws XFlowException {
		final XStatus status = xflow.getStatus(statusCode);
		if(status == null) {
			throw new XFlowException("流程中没有该状态,流程：" + xflow + ", 状态:" + statusCode);
		}
		
		final XEvent event = status.getEvent(eventCode);
		
		if(event == null) {
			throw new XFlowException("状态没有该事件,流程：" + xflow  + ", 状态:" + status + ", 事件:" + eventCode);
		}
		
		excuter.execute(new Runnable() {

			@Override
			public void run() {
				LogU.i(XFlowProcesser.this, "开始执行事件", "流程" + xflow.toString() + ","  + status + ","  + event + ", 数据源:" + source);
				try {
					CallResult result = caller.call(status, event, source);
					
					if(result.getStatus() == null)
						return;
					
					if(!event.containsTo(result.getStatus())) {
						errorHandler.error(new XFlowException("错误的目标状态：流程-" + xflow  + ", 状态-" + status + ", 事件-" + result.getStatus()), "", status, event, source);
					}
					
					if(result.getStatus() != status) {
						fireStatusChangeEvent(status, result.getStatus(), event, source);
					}
				} catch (Exception e) {
					LogU.e(XFlowProcesser.this, "执行事件发生错误", e);
					errorHandler.error(e, "执行事件发生错误", status, event, source);
				}
				
			}
			
		});
	}
	
	

}
