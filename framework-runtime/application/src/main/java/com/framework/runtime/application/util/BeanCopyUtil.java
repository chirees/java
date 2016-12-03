package com.framework.runtime.application.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import com.framework.runtime.application.Application;

public class BeanCopyUtil {
	private static final Logger logger = LoggerFactory.getLogger(Application
			.getInstance().getErrorLogger());

	public static <E> List<E> copyTo(List<?> source, Class<E> destClass) {
		try {
			if (source.size() == 0)
				return Collections.emptyList();
			List<E> destList = new ArrayList<E>(source.size());

			for (Object obj : source) {
				E e = destClass.newInstance();
				copyTo(obj, e);
				destList.add(e);
			}

			return destList;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public static <E> E[] copyArray(Object[] source, Class<E> destClass) {
		try {
			if (source.length == 0)
				return null;
			Object[] targets = (Object[])Array.newInstance(destClass, source.length);

			int i = 0;
			for (Object obj : source) {
				E e = destClass.newInstance();
				copyTo(obj, e);
				targets[i++] = e;
			}

			return (E[])targets;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public static <E> List<E> copyTo(Object[] source, Class<E> destClass) {
		try {
			if (source.length == 0)
				return Collections.emptyList();
			List<E> destList = new ArrayList<E>(source.length);

			for (Object obj : source) {
				E e = destClass.newInstance();
				copyTo(obj, e);
				destList.add(e);
			}

			return destList;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public static <T> T copyTo(Object source, T target)  {
		try {
			if(source == null)
				return null;
			copyUseCglib(source, target);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
		return target;
	}

	private static Map<String, BeanCopier> copiers = new HashMap();

	public static void copyUseCglib(Object source, final Object target)
			throws Exception {
		
		String key = source.getClass().getName() + target.getClass().getName();

		BeanCopier copier = copiers.get(key);
		if (copier == null) {
			copier = BeanCopier.create(source.getClass(), target.getClass(),
					true);
			copiers.put(key, copier);
		}

		copier.copy(source, target, new Converter() {

			@Override
			public Object convert(Object srcProperty,
					Class targetPropertyClass, Object setMethod) {
			
				if (srcProperty == null) {
					return null;
				} else if (srcProperty.getClass().equals(targetPropertyClass)) {
					return srcProperty;
				} else if(targetPropertyClass.isPrimitive()) {
					return srcProperty;
				} else if (srcProperty.getClass().isArray()) {
					Object[] srcs = (Object[]) srcProperty;
					
					Class cType = targetPropertyClass.getComponentType();

					Object[] targets = (Object[])Array.newInstance(cType, srcs.length);
					int i = 0;
					try {
						for (Object src : srcs) {
							Object target = cType.newInstance();
							BeanCopyUtil.copyUseCglib(src, target);
							targets[i++] = target;
						}
					} catch (Exception e) {
						logger.error("拷贝对象出错：" + target.getClass() + " " + setMethod, e);
					}
					return targets;
				} else if (List.class.isAssignableFrom(srcProperty.getClass())) {
					List targets = new ArrayList();
					try {
						String targetFieldName = setMethod.toString().substring("set".length());
						targetFieldName = StringUtils.uncapitalise(targetFieldName);
						Field field = target.getClass().getDeclaredField(targetFieldName);
						Type type = field.getGenericType();
						
						 
						Class cType = null;
			            if(type instanceof ParameterizedType) {   
		                    ParameterizedType pt = (ParameterizedType) type;  
		                    cType = (Class)pt.getActualTypeArguments()[0];
			            }
						
			            if(cType == null) throw new Exception("List 对象 必须指定泛型参数:" + target.getClass() + " " + setMethod);  
			            
						List list = (List)srcProperty;
					
						for (Object src : list) {
							Object target = cType.newInstance();
							BeanCopyUtil.copyUseCglib(src, target);
							targets.add(target);
						}
					} catch (Exception e) {
						logger.error("拷贝对象出错：" + target.getClass() + " " + setMethod, e);
					}
					return targets;
					
				} else {
					try {
						Object target = targetPropertyClass.newInstance();
						BeanCopyUtil.copyUseCglib(srcProperty, target);
						return target;
					} catch (Exception e) {
						logger.error("拷贝对象出错：" + target.getClass() + " " + setMethod, e);
					}
				}

				return null;
			}

		});

	}
}
