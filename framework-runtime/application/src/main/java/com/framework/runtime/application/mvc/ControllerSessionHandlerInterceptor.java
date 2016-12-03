package com.framework.runtime.application.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.runtime.application.Application;
import com.framework.runtime.application.ResponseCode;
import com.framework.runtime.application.ReturnCode;
import com.framework.runtime.application.WebResponse;
import com.google.gson.Gson;

public class ControllerSessionHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(Application.getInstance().getRuntimeLogger()); 
	private static final String REQ_IN_TIME = "REQ_IN_TIME";
	
	private String noAuthUrl;
	
	private RoleChecker roleChecker;
	
	public String getNoAuthUrl() {
		return noAuthUrl;
	}

	public void setNoAuthUrl(String noAuthUrl) {
		this.noAuthUrl = noAuthUrl;
	}
	

	public RoleChecker getRoleChecker() {
		return roleChecker;
	}

	public void setRoleChecker(RoleChecker roleChecker) {
		this.roleChecker = roleChecker;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("********************************* REQUEST in *****************************************");
		logger.info("URL:" + request.getRequestURI() + " " + request.getQueryString());
		long time = System.currentTimeMillis();
		request.setAttribute(REQ_IN_TIME, time);
		if(request.getRequestURI().contains("api-docs")) {
			return super.preHandle(request, response, handler);
		}
		
		if(!(handler instanceof HandlerMethod)) {
			HttpSession session = request.getSession();
        	UserWrapper user = (UserWrapper)session.getAttribute(UserWrapper.KEY);
        	if(roleChecker != null && !roleChecker.check(user, null, request.getRequestURI())) {
				response.sendRedirect(request.getContextPath() + noAuthUrl);
    			return false;
			}
			boolean result = super.preHandle(request, response, handler);
         	return result;
		}
		else {
			NeedLogin needLogin = ((HandlerMethod) handler).getMethod().getAnnotation(NeedLogin.class);
			
	        if(needLogin == null){
	            needLogin = ((HandlerMethod) handler).getBeanType().getAnnotation(NeedLogin.class);
	        }
	        
	        if (((needLogin == null) || needLogin.value())) {
	        	ResponseBody body = ((HandlerMethod) handler).getMethod().getAnnotation(ResponseBody.class);
	        	
	        	
	        	HttpSession session = request.getSession();
	        	UserWrapper user = (UserWrapper)session.getAttribute(UserWrapper.KEY);
	        	if(body != null) {
		    		if(user == null) {
		    			returnNoAuthError(response);
		    			return false;
		    		}
		    		else {
		    			if(needLogin != null && needLogin.role() != null && roleChecker != null && !roleChecker.check(user, needLogin.role(), request.getRequestURI())) {
		    				returnNoAuthError(response);
			    			return false;
		    			}
		    		}
	        	}
	        	else {
	        		if(user == null) {
		        		response.sendRedirect(request.getContextPath() + noAuthUrl);
		        		return false;
	        		}
	        		else {
		    			if(needLogin != null && needLogin.role() != null && roleChecker != null && !roleChecker.check(user, needLogin.role(), request.getRequestURI())) {
		    				response.sendRedirect(request.getContextPath() + noAuthUrl);
			    			return false;
		    			}
		    		}
	        	}
	    		
	    		logger.info("USER:" + user.getUser());
	        	
	         	boolean result = super.preHandle(request, response, handler);
	         	return result;
	        }
	        else {
	        	
	        	boolean result = super.preHandle(request, response, handler);
	        	return result;
	        }
		}
	}
	
	private void returnNoAuthError(HttpServletResponse response) {
		WebResponse webResponse = new WebResponse();
		ResponseCode responseCode = ReturnCode.COMMON_CODE.NO_AUTH.getReturnCode().getResponseCode();
		webResponse.setResponseCode(responseCode);
		Gson gson = new Gson();
        try {
        	response.setContentType("text/html;charset=UTF-8");
            response.getWriter().flush();
            response.getWriter().print(gson.toJson(webResponse));
        } catch (IOException e) {
        	logger.error(this.getClass().getSimpleName(), e);
        }
	}
	
	public static final String readString(InputStream in) throws UnsupportedEncodingException,
		IOException {
		if (in == null)
			return "";
		
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("CTRL handle " + handler);
    	super.postHandle(request, response, handler, modelAndView);
    	
    }
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("CTRL after  " + handler);
		super.afterCompletion(request, response, handler, ex);
		long reqInTime = (Long) request.getAttribute(REQ_IN_TIME);
		logger.info("CTRL time:" + request.getRequestURI() + ", time=" + (System.currentTimeMillis() - reqInTime));
		logger.info("********************************* REQUEST finish *****************************************");
	}
	
}
