/**
* @Project: framework-runtime-application
* @Package com.framework.runtime.application.mvc
* FileName：LoginRedisHandlerInterceptor.java
* Version：v1.0
* date：2016年3月21日
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.mvc;

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
import com.framework.runtime.application.redis.RedisService;

/**
* FileName: LoginRedisHandlerInterceptor.java
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月21日 下午1:45:56
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年3月21日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class LoginRedisHandlerInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	private static final String REQ_IN_TIME = "REQ_IN_TIME";

	@Autowired
	private RedisService redisService;
	
	private String CONTENT_TYPE = "text/html;charset=utf-8";

	private String accountAuthSessionName;
	private String noAuthUrl;

	public String getNoAuthUrl()
	{
		return noAuthUrl;
	}

	public void setNoAuthUrl(String noAuthUrl)
	{
		this.noAuthUrl = noAuthUrl;
	}

	public String getAccountAuthSessionName()
	{
		return accountAuthSessionName;
	}

	public void setAccountAuthSessionName(String accountAuthSessionName)
	{
		this.accountAuthSessionName = accountAuthSessionName;
	}
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		logger.info("********************************* REQUEST in *****************************************");
		logger.info("URL:" + request.getRequestURI() + " " + request.getQueryString());
		long time = System.currentTimeMillis();
		request.setAttribute(REQ_IN_TIME, time);
		if (!(handler instanceof HandlerMethod))
		{
			return super.preHandle(request, response, handler);
		}
		else
		{
			// 获取登录注解
			NeedLogin needLogin = ((HandlerMethod) handler).getMethod().getAnnotation(NeedLogin.class);
			if (null == needLogin)
			{
				needLogin = ((HandlerMethod) handler).getBeanType().getAnnotation(NeedLogin.class);
			}
			if (((needLogin == null) || needLogin.value()))
			{
				// session是否创建
				HttpSession session = request.getSession(false);
				if (null == session)
				{
					// 跳转至登录页面
					response.setContentType(CONTENT_TYPE);
					response.getWriter().print("<script type='text/javascript'>alert('暂未登录或会话已失效,请重新登录!');window.location.href='" + noAuthUrl + "'</script>");
					return false;
				}
				else
				{
					// 先从session中获取用户登录信息
					AccountAuthWrapper authWrapper = (AccountAuthWrapper) session.getAttribute(accountAuthSessionName);
					if (null == authWrapper || null == authWrapper.getAccountWrapper())
					{
						// 根据sessionid从redis中获取用户信息
						authWrapper = (AccountAuthWrapper) redisService.getJson(session.getId(), AccountAuthWrapper.class);
						if (null == authWrapper || null == authWrapper.getAccountWrapper())
						{
							// 跳转至登录页面
							response.setContentType(CONTENT_TYPE);
							response.getWriter().print("<script type='text/javascript'>alert('暂未登录或会话已失效,请重新登录!');window.location.href='" + noAuthUrl + "'</script>");
							return false;
						}
						else
						{
							session.setAttribute(accountAuthSessionName, authWrapper);
						}
					}
				}
			}
			return super.preHandle(request, response, handler);

		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
		logger.info("CTRL handle " + handler);
		super.postHandle(request, response, handler, modelAndView);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
		logger.info("CTRL after  " + handler);
		super.afterCompletion(request, response, handler, ex);
		long reqInTime = (Long) request.getAttribute(REQ_IN_TIME);
		logger.info("CTRL time:" + request.getRequestURI() + ", time=" + (System.currentTimeMillis() - reqInTime));
		logger.info("********************************* REQUEST finish *****************************************");
	}

}
