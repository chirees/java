package com.framework.runtime.application.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.framework.runtime.application.LogU;

public class SessionUserMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver  implements WebArgumentResolver  {
	private static Logger logger = LoggerFactory.getLogger(UserMethodArgumentResolver.class);
	

	
	public SessionUserMethodArgumentResolver() {
		super(null);
	}
	

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		 if (parameter.hasParameterAnnotation(SessionUser.class)) {
			    return true;
			}
			return false;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		SessionUser su = parameter.getParameterAnnotation(SessionUser.class);
		return new SessionUserParamNamedValueInfo(su);
	}
	
	private static class SessionUserParamNamedValueInfo extends NamedValueInfo {

		private SessionUserParamNamedValueInfo() {
			super("", false, null);
		}
		
		private SessionUserParamNamedValueInfo(SessionUser annotation) {
			super(annotation.value(), annotation.required(), null);
		}
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest)
			throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		
		UserWrapper user = (UserWrapper)session.getAttribute(UserWrapper.KEY);
		
		
		logger.info("get  user from session:" + user);
		
		return user;
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter)
			throws ServletException {
		 String paramType = parameter.getParameterType().getName();
		    throw new ServletRequestBindingException(
	                "Missing request session user parameter '" + name + "' for method parameter type [" + paramType + "]");
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest)
			throws Exception {
		 if(!supportsParameter(methodParameter)) {
	            return WebArgumentResolver.UNRESOLVED;
	        }
	        return resolveArgument(methodParameter, null, webRequest, null);
	}
}
