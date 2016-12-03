/**
* @Project: framework-runtime-application
* @Package com.framework.runtime.application.mvc
* FileName：PermissionRedisHandlerInterceptor.java
* Version：v1.0
* date：2016年3月21日
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.mvc;

import java.util.List;

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
* FileName: PermissionRedisHandlerInterceptor.java
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月21日 下午4:52:32
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
public class PermissionRedisHandlerInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger());
	private static final String REQ_IN_TIME = "REQ_IN_TIME";

	@Autowired
	private RedisService redisService;
	private String CONTENT_TYPE = "text/html;charset=utf-8";
	private String TIP_MESSAGE = "<script type='text/javascript'>alert('对不起,非法操作！');window.history.back();</script>";

	private String accountAuthSessionName;

	public String getAccountAuthSessionName()
	{
		return accountAuthSessionName;
	}

	public void setAccountAuthSessionName(String accountAuthSessionName)
	{
		this.accountAuthSessionName = accountAuthSessionName;
	}

	/**
	* (not Javadoc)
	* Title: preHandle
	* Description:请求之前调用方法
	* @param request
	* @param response
	* @param handler
	* @return
	* @throws Exception
	* @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	*/
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
			// 获取请求方法上的注解，判断是否具有该权限
			Permission permission = ((HandlerMethod) handler).getMethod().getAnnotation(Permission.class);
			if (null == permission)
			{
				permission = ((HandlerMethod) handler).getBeanType().getAnnotation(Permission.class);
			}
			else
			{
				HttpSession session = request.getSession(false);
				if (null == session)
				{
					response.setContentType(CONTENT_TYPE);
					response.getWriter().print(TIP_MESSAGE);
					return false;
				}
				else
				{
					// 先从session中获取用户登权限信息
					AccountAuthWrapper authWrapper = (AccountAuthWrapper) session.getAttribute(accountAuthSessionName);
					if (null == authWrapper || null == authWrapper.getAccountWrapper() || null == authWrapper.getResourceWrapper())
					{
						// 根据sessionid从redis中获取用户权限信息
						authWrapper = (AccountAuthWrapper) redisService.getJson(request.getSession().getId(), AccountAuthWrapper.class);
					}
					if (null == authWrapper || null == authWrapper.getAccountWrapper() || null == authWrapper.getResourceWrapper())
					{
						response.setContentType(CONTENT_TYPE);
						response.getWriter().print(TIP_MESSAGE);
						return false;
					}
					else
					{
						// 重新存入session
						session.setAttribute(accountAuthSessionName, authWrapper);

						// 权限遍历
						List<ResourceWrapper> resources = authWrapper.getResourceWrapper().getResources();
						boolean passPermission = false;
						for (ResourceWrapper resource : resources)
						{
							if (permission.model().equals(resource.getModel()) && permission.value().equals(resource.getValue()))
							{
								passPermission = true;
								break;
							}
						}
						if (!passPermission)
						{
							response.setContentType(CONTENT_TYPE);
							response.getWriter().print(TIP_MESSAGE);
						}
						return passPermission;
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
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
