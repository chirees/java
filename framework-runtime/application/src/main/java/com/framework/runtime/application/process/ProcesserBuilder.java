package com.framework.runtime.application.process;

import static au.com.ds.ef.FlowBuilder.from;
import static au.com.ds.ef.FlowBuilder.on;

import org.elasticsearch.common.lang3.StringUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.framework.runtime.application.LogU;
import com.framework.runtime.application.process.config.EventNode;
import com.framework.runtime.application.process.config.FlowNode;
import com.framework.runtime.application.process.config.ProcessNode;
import com.framework.runtime.application.process.config.StatusNode;
import com.framework.runtime.application.process.config.TriggerNode;
import com.framework.runtime.application.respcode.RespcodeManager;

import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.EventEnum;
import au.com.ds.ef.FlowBuilder;
import au.com.ds.ef.StateEnum;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.Transition;
import au.com.ds.ef.Trigger;
import au.com.ds.ef.call.ContextHandler;
import au.com.ds.ef.call.EventHandler;
import au.com.ds.ef.call.ExecutionErrorHandler;
import au.com.ds.ef.err.ExecutionError;

public class ProcesserBuilder {

	
	public ProcesserPool build(String path) throws Exception {
		 ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		 Resource[] resources = resourcePatternResolver
		          .getResources(path);
		 Serializer serializer = new Persister();
		 ProcesserPool pool = new ProcesserPool();
		 for(Resource resource:resources) {
			 FlowNode node = serializer.read(FlowNode.class, resource.getInputStream());
			 for(ProcessNode pn:node.getProcesses()) {
				 Processer p = new Processer();
				 p.setCode(pn.getCode());
				 p.setName(pn.getName());
				 p.setGroup(pn.getGroup());
				 p.setAsync(pn.isAsync());
				 int i = 0;
				 
				 for(StatusNode sn:pn.getStatuses()) {
					 Status s = new Status();
					 s.setCode(sn.getCode());
					 s.setName(sn.getName());
					 s.setTrigger(sn.getTrigger());
					 s.setStarted(sn.isStarted());
					 p.put(s);
					 
					 if(i == 0) {
						 p.setStart(s);
					 }
					 i++;
				 }
				 
				 for(StatusNode sn:pn.getStatuses()) {
					 Status s = p.get(sn.getCode());
					 if(sn.getEvents() != null) {
						 for(EventNode en:sn.getEvents()) {
							 Event e = new Event();
							 e.setCode(en.getCode());
							 e.setName(en.getName());
							 e.setValidate(en.getValidate());
							 e.setFail(en.getFail());
							 Status toStatus = p.get(en.getToStatus());
							 if(toStatus == null)
								 LogU.i("WRONG:", "to status is null," + en.getToStatus());
							 e.setToStatus(toStatus);
							 s.put(e);
						 }
					 }
					 
					 if(sn.getTriggers() != null) {
						 for(TriggerNode t:sn.getTriggers()) {
							 Trigger tg = new Trigger();
							 tg.setValue(t.getValue());
							 s.addTrigger(tg);
							 LogU.i("addTrigger:", sn.getCode() + ", " + t.getValue());
						 }
					 }
					 
				 }
				
				 buildFlow(p, pool);
				 
				 pool.put(p);
			 }
			 System.out.println(node);
		 }
		 
		 return pool;
	}
	
	private void buildFlow(final Processer p, final ProcesserPool pool) {
		 FlowBuilder<StatefulContext> builder = from(p.getStart());
		 Status start = p.getStart();
		 if(start.eventSize() > 0) {
			 Transition[] transitions = new Transition[start.eventSize()];
			 int i = 0;
			 for(Event event:start.events()) {
				 transitions[i] = on(event).to(event.getToStatus());
				 transitFlow(p, event.getToStatus(), transitions[i]);
				 i++;
				 
			 }
			 
			 EasyFlow flow = builder.transit(transitions);
			 if(!p.isAsync()) {
				 flow = flow.executor(new SingleSyncExecutor());
			 }
			 
			 p.setFlow(flow);
			 
			 
			 
			 for(Status status:p.statuses()) {
				 p.getFlow().whenEnter(status, new ContextHandler<StatefulContext>() {
		                @Override
		                public void call(StatefulContext context) throws Exception {
	                    	final StateEnum state = context.getState();
	                    	final EventEnum event = context.getLastEvent();
	                    	
	                    	if(context.getFlowMonitor() != null) {
	                    		if(state.isStarted()) {
	                    			context.getFlowMonitor().started(state, context.getData(), context.getResponse());
	                    		}
	                    		else {
	                    			context.getFlowMonitor().stateChanged(state, event, context.getData(), context.getResponse());
	                    		}
	                    	}
	                    	
	                    	if(state.trigger() != null) {
	                    		p.getFlow().trigger(((Status)state).getEvent(state.trigger()), context);
	                    	}
	                    	
	                    	if(state.triggers() != null) {
	                    		for(Trigger trigger:state.triggers()) {
	                    			TriggerEvent te = new TriggerEvent();
	                    			te.setData(context.getData());
	                    			te.setEvent(event);
	                    			te.setStatus(state);
	                    			te.setValue(trigger.getValue());
	                    			
	                    			pool.dispatch(te);
	                    		}
	                    	}
		                }
		         });

				
			 }
			 
			
		 }
		 
		 p.getFlow().whenError(new ExecutionErrorHandler<StatefulContext>() {

			@Override
			public void call(ExecutionError error, StatefulContext context) {
				 if(context.getFlowMonitor() != null) { 
					 context.getFlowMonitor().errored(error.getState(), error.getEvent(), error.getMessage(), error.getCause());
				 }
			}
			 
		 });
		 
		 p.getFlow().whenEvent(new EventHandler() {

			@Override
			public boolean call(EventEnum event, StateEnum from, StateEnum to, StatefulContext context)
					throws Exception {
				
				if(pool.getEventValidator() != null) {
					if(!pool.getEventValidator().validate(event, from, to, context.getData(), context.getResponse()))
						return false;
				}
				
				return context.getFlowMonitor().doEvent(event, from, to, context.getData(), context.getResponse());
			}
			 
		 });
		 
		 p.getFlow().whenEventFail(new EventHandler() {

				@Override
				public boolean call(EventEnum event, StateEnum from, StateEnum to, StatefulContext context)
						throws Exception {
					boolean failed = context.getFlowMonitor().eventFail(event, from, to, context.getData(), context.getResponse());
//					if(failed && !StringUtils.isEmpty(event.fail()) && !RespcodeManager.getInstance().isSuccess(context.getResponse()))
//						p.getFlow().trigger(((Status)from).getEvent(event.fail()), context);
					return true;
				}
				 
			 });
	}
	

	
	private void transitFlow(Processer p, Status status, Transition transition) {
		if(status.eventSize() > 0) {
			int i = 0;
			Transition[] transitions = new Transition[status.eventSize()];
			for(Event en:status.events()) {
				Status toStatus = en.getToStatus();
				if(toStatus.eventSize() == 0) {
					transitions[i] = on(en).finish(toStatus);
				}
				else {
					transitions[i] = on(en).to(toStatus);
				}
				
				if(toStatus.eventSize() > 0)
					transitFlow(p, toStatus, transitions[i]);
				
				i++;
			}
			
			transition.transit(transitions);
			
			
//			for(Event en:status.events()) {
//				Status toStatus = en.getToStatus();
//				
//				if(toStatus.eventSize() > 0)
//					transitFlow(p, toStatus, transition);
//			}
			
		}
	}
}
