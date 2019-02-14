package com.xrlj.utils;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表字段和实体bean之间映射。
 */
public class TableEntityMapperUtil {

	/**
	 * 按大小写用下划线分割字符串并转成大写。
	 * @param fieldName
	 * @return
	 */
	public static String separationDomainField(String fieldName) {
		StringBuffer result = new StringBuffer();
		char[] c = fieldName.toCharArray();
		for (int i_ = 0; i_ < c.length; i_++) {
			char ch = fieldName.charAt(i_);
			if (Character.isUpperCase(ch) && i_ != 0) {
				result.append("_");
				result.append(Character.toLowerCase(ch));
			} else {
				result.append(ch);
			}
		}
		return result.toString().toUpperCase();
	}

	/**
	 * 把表、字段名转换为单词首字母大写形式的实体、属性名
	 * @param name
	 * @return
	 */
	public static String mapperToProperty(String name) {
		StringBuffer result = new StringBuffer();
		result.append(Character.toLowerCase(name.charAt(0)));
		for (int i = 1; i < name.toCharArray().length; i++) {
			char ch = name.charAt(i);
			if (ch == '_') {
				if (i+1 < name.toCharArray().length && Character.isLowerCase(name.charAt(i+1))) {
					result.append(Character.toUpperCase(name.charAt(i+1)));
					i++;
					continue;
				}
			}
			result.append(ch);
		}
		return result.toString();
	}

	/**
	 * 把实体、属性名转换为加下划线(_)形式的表、字段名
	 * @param name
	 * @return
	 */
	public static String mapperToDB(String name) {
		StringBuffer result = new StringBuffer();
		result.append(Character.toLowerCase(name.charAt(0)));
		for (int i = 1; i < name.toCharArray().length; i++) {
			char ch = name.charAt(i);
			if (Character.isUpperCase(ch)) {
				result.append("_");
				result.append(Character.toLowerCase(ch));
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}
	
	public static Map<String, Object> beanToMap(Object source, String... ignoreProperties) {
		if (null == source) {
			throw new NullPointerException("Source must not be null");
		}

		Class<?> beanClass = source.getClass();
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		PropertyDescriptor propertyDescriptor = null;
		for (Field field : beanClass.getDeclaredFields()) {
			
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			try {
				propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
				Method readMethod = propertyDescriptor.getReadMethod();

				if (readMethod == null
						|| (ignoreList != null && ignoreList.contains(propertyDescriptor.getName()))
						|| field.getAnnotation(Transient.class) != null
						|| readMethod.getAnnotation(Transient.class) != null) {
					continue;
				}
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = null;
				try {
					value = readMethod.invoke(source);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				//不复制null值
				if (value == null) {
					continue;
				}
				result.put(field.getName(), value);
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/*public static void main(String[] args) {
		String entity = "StudentClass";
		String table = "student_class_name";
		
		System.out.println(TableEntityMapperUtil.mapperToDB(entity));
		System.out.println(TableEntityMapperUtil.mapperToEntity(table));
	}*/
}
