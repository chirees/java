package com.framework.runtime.application.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.runtime.application.AbstractRequest;
import com.framework.runtime.application.AbstractResponse;
import com.framework.runtime.application.Application;
import com.framework.runtime.application.ReturnCode;
import com.framework.runtime.application.exception.ServiceException;
import com.framework.runtime.application.exception.ValidateException;
import com.framework.runtime.application.respcode.RespcodeManager;
import com.google.gson.Gson;

public class WebServiceInterceptor
{

	private static Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	private static Logger errorLogger = LoggerFactory.getLogger(Application.getInstance().getErrorLogger());

	public static final Gson gson = new Gson();

	@Around("@annotation(com.framework.runtime.application.WebMethod)")
	private Object aroundCall(ProceedingJoinPoint point) throws Throwable
	{
		Object[] parameters = point.getArgs();

		StringBuffer sb = new StringBuffer();
		sb.append("before call:");
		sb.append(point.getSignature().getDeclaringType().getSimpleName() + "." + point.getSignature().getName() + "(");
		int i = 0;
		for (Object parameter : parameters)
		{
			if (i > 0)
				sb.append(", ");

			sb.append(gson.toJson(parameter));
			i++;
		}
		sb.append(")");
		logger.info(sb.toString());

		for (Object parameter : parameters)
		{
			if (parameter instanceof AbstractRequest)
			{
				AbstractRequest request = (AbstractRequest) parameter;
				try
				{
					request.validate();
				}
				catch (ValidateException e)
				{
					AbstractResponse response = new AbstractResponse();
					RespcodeManager.getInstance().genSysParamError(response, "dubbo 接口");

					logger.info("end call:" + response);
					return response;
				}
			}
		}

		try
		{
			long ptime = System.currentTimeMillis();
			Object result = point.proceed(parameters);
			long time = System.currentTimeMillis() - ptime;
			logger.info("end call:" + gson.toJson(result) + "; time:" + time);
			return result;
		}
		catch (ServiceException e)
		{
			errorLogger.error(sb.toString(), e);
			AbstractResponse response = new AbstractResponse();
			RespcodeManager.getInstance().genSysError(response, "系统错误");
			return response;
		}
		catch (Throwable t)
		{
			errorLogger.error(sb.toString(), t);
			AbstractResponse response = new AbstractResponse();
			RespcodeManager.getInstance().genSysError(response, "系统错误");
			return response;
		}

	}
}
