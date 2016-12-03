package com.framework.runtime.application.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.LogU;
import com.framework.runtime.application.ResponseCode;
import com.framework.runtime.application.ReturnCode;
import com.framework.runtime.application.WebResponse;
import com.framework.runtime.application.exception.TokenException;
import com.framework.runtime.application.redis.RedisService;
import com.framework.runtime.application.trace.TraceIdLocal;
import com.google.gson.Gson;

public class ControllerRedisHandlerInterceptor extends HandlerInterceptorAdapter
{
	private static final String REQ_IN_TIME = "REQ_IN_TIME";
	// @Autowired
	// private RedisClient redisClient;

	@Autowired
	private RedisService redisService;

	private String noAuthUrl;

	private RoleChecker roleChecker;

	public String getNoAuthUrl()
	{
		return noAuthUrl;
	}

	public void setNoAuthUrl(String noAuthUrl)
	{
		this.noAuthUrl = noAuthUrl;
	}

	public RoleChecker getRoleChecker()
	{
		return roleChecker;
	}

	public void setRoleChecker(RoleChecker roleChecker)
	{
		this.roleChecker = roleChecker;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		if (request.getRequestURI().contains("api-docs") || request.getRequestURI().contains("transactionCallbackListener"))
		{
			String env = System.getenv("env");
			if(request.getRequestURI().contains("api-docs") && "prod".equals(env)){
				return false;
			}
			return super.preHandle(request, response, handler);
		}
		
		String traceId = UUID.randomUUID().toString();
		TraceIdLocal.setId(traceId);
		LogU.r(this, "********************************* REQUEST in *****************************************");
		LogU.r(this, "URL:" + request.getRequestURI(), "TraceId:" + traceId, (request.getQueryString() != null ? request.getQueryString() : ""));
		long time = System.currentTimeMillis();
		request.setAttribute(REQ_IN_TIME, time);
		

		if (!(handler instanceof HandlerMethod))
		{
			// HttpSession session = request.getSession();
			// UserWrapper user = (UserWrapper) session.getAttribute(UserWrapper.KEY);
			// if (roleChecker != null && !roleChecker.check(user, null, request.getRequestURI())) {
			// response.sendRedirect(request.getContextPath() + noAuthUrl);
			// return false;
			// }
			boolean result = super.preHandle(request, response, handler);
			return result;
		}
		else
		{

			NeedLogin needLogin = ((HandlerMethod) handler).getMethod().getAnnotation(NeedLogin.class);
			if (needLogin == null)
			{
				needLogin = ((HandlerMethod) handler).getBeanType().getAnnotation(NeedLogin.class);
			}

			if (((needLogin == null) || needLogin.value()))
			{
				String token = request.getParameter("token");
				if (token == null)
				{
					HttpSession session = request.getSession(false);
					if (session != null)
					{
						token = (String) session.getAttribute("token");
						LogU.r(this, "Get token from session, use for swagger api.");
					}
				}

				if (token == null)
				{
					throw new TokenException();
				}
				else
				{
					Serializable user = redisService.get(getToken(token));
					// if (user == null) {
					if (user == null)
					{
						// returnTokenError(response);
						throw new TokenException();
						// return false;
					}

					// logger.info("USER:" + user.getUser());
					LogU.r(this, "USER:" + user);
				}

				boolean result = super.preHandle(request, response, handler);
				return result;
				
			}
			else
			{

				boolean result = super.preHandle(request, response, handler);
				return result;
			}
		}
		
	}
	
	protected String getToken(String token){
		return token;
	}

	private void returnTokenError(HttpServletResponse response)
	{
		WebResponse webResponse = new WebResponse();
		ResponseCode responseCode = ReturnCode.COMMON_CODE.TOKEN_ERROR.getReturnCode().getResponseCode();
		webResponse.setResponseCode(responseCode);
		Gson gson = new Gson();
		try
		{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().flush();
			response.getWriter().print(gson.toJson(webResponse));
		}
		catch (IOException e)
		{
			LogU.e(this, "", e);
		}
	}

	public static final String readString(InputStream in) throws UnsupportedEncodingException, IOException
	{
		if (in == null)
			return "";

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;)
		{
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
//		LogU.r(this, "CTRL handle " + handler);
		super.postHandle(request, response, handler, modelAndView);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
//		LogU.r(this, "CTRL after  " + handler);
		super.afterCompletion(request, response, handler, ex);
		Long reqInTime = (Long) request.getAttribute(REQ_IN_TIME);
		if(reqInTime != null) {
			LogU.r(this, "REQ time:" + request.getRequestURI(), "time=" + (System.currentTimeMillis() - reqInTime));
			LogU.r(this, "********************************* REQUEST finish *****************************************");
		}
	}

}
