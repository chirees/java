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
import com.framework.runtime.application.ReturnCode;
import com.framework.runtime.application.exception.ServiceException;
import com.framework.runtime.application.exception.ValidateException;
import com.framework.runtime.application.respcode.RespCode;
import com.framework.runtime.application.respcode.RespcodeManager;
import com.framework.runtime.application.trace.TraceIdLocal;
import com.google.gson.Gson;

public class WebServiceFilter implements Filter {
	public static final Gson gson = new Gson();
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		Object[] parameters = invocation.getArguments();
		
		
		for(Object parameter:parameters) {
			if(parameter instanceof AbstractRequest) {
				AbstractRequest request = (AbstractRequest)parameter;
				try {
					TraceIdLocal.setId(request.getRequestTraceId());
					request.validate();
				} catch (ValidateException e) {
					RpcResult result = new RpcResult();
					AbstractResponse response = new AbstractResponse();
					RespcodeManager.getInstance().genSysParamError(response, "dubbo 接口");
					result.setValue(response);
					
					LogU.r(this, "validate fail call:" + invocation.getMethodName(), response.respString());
					return result;
				}
			}
		}
		
		LogU.r(this, "before call:" + invocation.getMethodName() , gson.toJson(invocation.getArguments()));
		
		try {
			long ptime = System.currentTimeMillis();
			Result result = invoker.invoke(invocation);
			long time = System.currentTimeMillis() - ptime;
			if(result.hasException()){
				LogU.e(this, invocation.getMethodName(), result.getException());
				result = new RpcResult();
				
				AbstractResponse response = new AbstractResponse();
				RespcodeManager.getInstance().genSysError(response, "系统错误");
				((RpcResult)result).setValue(response);
				
				LogU.r(this, "exception call:" + invocation.getMethodName(), response.respString() , "  time:" + time);
				
			}else if(result.getValue() instanceof AbstractResponse) {
				AbstractResponse response = (AbstractResponse)result.getValue();
				if(response.getSysCode() == null) {
					LogU.r(this, "错误：未设置返回码.");
					RespcodeManager.getInstance().genSysError(response, "系统错误");
					LogU.r(this, "exception call:" + invocation.getMethodName(), response.respString(), " time:" + time);
				}
				else {
					LogU.r(this, "end call:" + invocation.getMethodName(), response.respString(), " time:" + time);
				}
			}
			else {
				LogU.r(this, "end call:" + invocation.getMethodName() , gson.toJson(result) , "  time:" + time);
			}
			
			return result;
		} catch (ServiceException e) {
			LogU.e(this, invocation.getMethodName(), e);
			RpcResult result = new RpcResult();
			AbstractResponse response = new AbstractResponse();
			RespcodeManager.getInstance().genSysError(response, "系统错误");
			result.setValue(response);
			LogU.r(this, "exception call:" + invocation.getMethodName() , response.respString());
			return result;
		} catch (Throwable t) {
			LogU.e(this, invocation.getMethodName(), t);
			RpcResult result = new RpcResult();
			AbstractResponse response = new AbstractResponse();
			RespcodeManager.getInstance().genSysError(response, "系统错误");
			result.setValue(response);
			LogU.r(this, "exception call:" + invocation.getMethodName() , response.respString());
			return result;
		} 
	}

}
