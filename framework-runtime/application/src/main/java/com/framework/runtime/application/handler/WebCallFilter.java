package com.framework.runtime.application.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.framework.runtime.application.AbstractRequest;
import com.framework.runtime.application.AbstractResponse;
import com.framework.runtime.application.Application;
import com.framework.runtime.application.LogU;
import com.framework.runtime.application.exception.ServiceException;
import com.framework.runtime.application.exception.ValidateException;
import com.framework.runtime.application.respcode.RespcodeManager;
import com.framework.runtime.application.trace.TraceIdLocal;
import com.google.gson.Gson;

public class WebCallFilter implements Filter  {
	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	private static Logger errorLogger = LoggerFactory.getLogger(Application.getInstance().getErrorLogger());
	public static final Gson gson = new Gson();
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		Object[] parameters = invocation.getArguments();
		StringBuffer sb = new StringBuffer();
		sb.append("before call:" + invocation.getMethodName() + ",");
		LogU.r(this, "before call:" + invocation.getMethodName(), gson.toJson(invocation.getArguments()));
		for(Object parameter:parameters) {
			if(parameter instanceof AbstractRequest) {
				AbstractRequest request = (AbstractRequest)parameter;
				request.setRequestTraceId(TraceIdLocal.getId());
			}
		}
		
		try {
			long ptime = System.currentTimeMillis();
			Result result = invoker.invoke(invocation);
			long time = System.currentTimeMillis() - ptime;
			
			
			if(result.getValue() instanceof AbstractResponse) {
				AbstractResponse response = null;
				response = (AbstractResponse)result.getValue();
				LogU.r(this, "end call:" + invocation.getMethodName(), response.respString(), " time:" + time);
			}
			else {
				LogU.r(this, "end call:" + invocation.getMethodName(), " time:" + time);
			}
			return result;
		} catch (RpcException e) {
			LogU.e(this, sb.toString(), e);
			LogU.r(this, "rpc exception call:" + invocation.getMethodName());
			throw e;
		} catch (Exception e) {
			LogU.e(this, sb.toString(), e);
			
			LogU.r(this, "exception call:" + invocation.getMethodName());
			throw new RpcException(e);
		}
	}

}
