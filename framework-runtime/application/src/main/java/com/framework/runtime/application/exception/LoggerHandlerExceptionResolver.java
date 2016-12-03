package com.framework.runtime.application.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class LoggerHandlerExceptionResolver implements HandlerExceptionResolver {
	private static Logger logger = LoggerFactory.getLogger(LoggerHandlerExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		
		logger.error(handler != null ? handler.toString() : "null");
		logger.error("", ex);
		
		// 根据不同错误转向不同页面
		if (ex instanceof ServiceException) {
			return new ModelAndView("error-service", model);
		} else if (ex instanceof WebException) {
			return new ModelAndView("error-web", model);
		} else {
			return new ModelAndView("error", model);
		}
	}

}
