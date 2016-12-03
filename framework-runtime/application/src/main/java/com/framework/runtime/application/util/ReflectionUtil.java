package com.framework.runtime.application.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ReflectionUtil {

	private static final String TYPE_NAME_PREFIX = "class ";

	public static Map<Object, Object> parseDomaintoMap(Object domain) {
		Map<Object, Object> objectMap = new HashMap<Object, Object>();

		addKeyVal(objectMap, domain.getClass(), domain, false);
//		addKeyVal(objectMap, domain.getClass().getSuperclass(), domain, true);
		return objectMap;
	}

	private static void addKeyVal(Map<Object, Object> m, Class<?> clazz, Object domain, boolean isSuper) {
		if (null == clazz) {
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		Object value = null;
		Field field = null;
		try {
			for (int i = 0; i < fields.length; i++) {
				field = fields[i];
				field.setAccessible(true);
				value = field.get(domain);
				Class<?> clz = field.getType();
				if (checkHandledType(clz, value)) {
					if (null != value) {
						if (isSuper && field.getName().equals("id")) {
							m.put("_id", value);
							continue;
						}
						m.put(field.getName(), value);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static boolean checkHandledType(Class<?> clazz, Object value) {
		if (null == value) {
			return false;
		}
		
		String retType = clazz.getSimpleName();
		if ((retType.equalsIgnoreCase("String") && StringUtils.isNotEmpty((String) value))
				|| retType.equalsIgnoreCase("Integer") || retType.equalsIgnoreCase("Long")
				|| retType.equalsIgnoreCase("Double") || retType.equalsIgnoreCase("Date")
				|| retType.equalsIgnoreCase("Char") || retType.equalsIgnoreCase("Float")
				|| retType.equalsIgnoreCase("Short") || retType.equalsIgnoreCase("Byte")) {
			return true;
		}
		return false;
	}
	
	public static Type[] getParameterizedTypes(Object object) {
		Type superclassType = object.getClass().getGenericSuperclass();
		if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
			return null;
		}
		return ((ParameterizedType) superclassType).getActualTypeArguments();
	}
	
	public static String getClassName(Type type) {
	    if (type==null) {
	        return "";
	    }
	    String className = type.toString();
	    if (className.startsWith(TYPE_NAME_PREFIX)) {
	        className = className.substring(TYPE_NAME_PREFIX.length());
	    }
	    return className;
	}
	
	public static Class getParameterClass(Object obj) throws ClassNotFoundException {
		Type[] types = getParameterizedTypes(obj);
		Class clazz = (Class)getClass(types[0]);
		return clazz;
	}

	public static Class getClass(Type type) throws ClassNotFoundException {
		String className = getClassName(type);
		if (className == null || className.isEmpty()) {
			return null;
		}
		return Class.forName(className);
	}
}
