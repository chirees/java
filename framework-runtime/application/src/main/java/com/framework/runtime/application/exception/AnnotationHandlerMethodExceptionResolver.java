/**
 * 
 * Copyright (c) 2013-2014 framework,Inc.All Rights Reserved.
 */
package com.framework.runtime.application.exception;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.google.gson.Gson;

/**
 * 
 * @author yyl
 * @version $Id: AnnotationHandlerMethodExceptionResolver.java, v 0.1 2014-5-26 上午10:10:06 yyl Exp $
 */
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(AnnotationHandlerMethodExceptionResolver.class);
    
    private String defaultErrorView;  
    
    private Map<String, String> respMap;
    
    public String getDefaultErrorView() {  
        return defaultErrorView;  
    }  
  
    public void setDefaultErrorView(String defaultErrorView) {  
        this.defaultErrorView = defaultErrorView;  
    }  
  
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {  
        
        LOGGER.error(this.getClass().getSimpleName(), exception);
        
        if (handlerMethod == null) {  
            return null;  
        }  
          
        Method method = handlerMethod.getMethod();  
  
        if (method == null) {  
            return null;  
        }  
          
        ModelAndView returnValue = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);  
          
        ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);  
        if (responseBodyAnn != null) {
            Gson gson = new Gson();
            try {
            	response.setContentType("text/html;charset=UTF-8");
                response.getWriter().flush();
                response.getWriter().print(gson.toJson(respMap));
            } catch (IOException e) {
                LOGGER.error(this.getClass().getSimpleName(), e);
            }
            return null;
        }  
          
        if(returnValue.getViewName() == null){
            returnValue.setViewName(defaultErrorView);  
        }  
          
        return returnValue;  
          
    }

    public Map<String, String> getRespMap() {
        return respMap;
    }

    public void setRespMap(Map<String, String> respMap) {
        this.respMap = respMap;
    }
      
}
