package com.xrlj.utils;

import java.math.BigDecimal;
import java.util.*;

public class Lang {

    private Lang(){}

	/**
	 * 将CheckedException转换为RuntimeException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将CheckedException转换为RuntimeException.
	 */
	public static RuntimeException unchecked(Exception e, String message, Object... args) {
		return new RuntimeException(String.format(message, args), e);
	}

	/**
	 * 获取最初的消息异常
	 *
	 * @param e
	 * @return
	 */
	public static Throwable getMessageCause(Throwable e) {
		while (e.getCause() != null && e.getCause().getMessage() != null) {
			e = e.getCause();
		}
		return e;
	}

	/**
	 * 新建一个Map，必须是偶数个参数 注意，这是一个同步的Map，且key和value不能为null
	 *
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map(Object... args) {
		Map<K, V> map = new LinkedHashMap<K, V>();
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException("The number of arguments must be an even number");
			}
			for (int i = 0; i < args.length; i += 2) {
				map.put((K) args[i], (V) args[i + 1]);
			}
		}
		return map;
	}

	/**
	 * 将输入参数转换为集合
	 * 
	 * @param args
	 * @return
	 */
	public static <T> List<T> list(Object... args) {
		args = Objects.requireNonNull(args);
		List<T> list = new ArrayList<>();
		for (Object arg : args) {
			if (arg instanceof Collection) {
				list.addAll((Collection) arg);
			} else {
				list.add((T) arg);
			}
		}
		return list;
	}

	/**
	 * 根据字符串生成大数字类型
	 */
	public static BigDecimal toBigDecimal(Object number) {
		if (number == null) {
			throw new IllegalArgumentException("The number should not be null");
		}
		if(number instanceof BigDecimal){
			return (BigDecimal)number;
		}
		if(number instanceof Numbers.BigNumber){
			return ((Numbers.BigNumber)number).get();
		}
		return new BigDecimal(number.toString());
	}

	/**
	 * 判断一个类型是否是数字类型
	 * @param cls 类型参数
	 * @return 如果该类型是数字，则返回true
	 */
	public static boolean isNumberType(Class cls){
		if(cls.isPrimitive()){
			if(boolean.class.equals(cls)){
				return false;
			}
			if(char.class.equals(cls)){
				return false;
			}
			return true;
		}
		else {
			return Number.class.isAssignableFrom(cls);
		}
	}

	/**
	 * 用来安全的比较两个对象是否相等
	 * @param o1 对象1
	 * @param o2 对象2
	 * @return 是否相等，如果相等则返回true，如果不相等则返回false。无法比较的类型则抛出异常
	 */
	public static boolean match(Object o1, Object o2){
		if(o1==o2){
			return true;
		}
		if(o1==null || o2==null){
			return false;
		}
		if(o1.equals(o2)){
			return true;
		}
		if(o1.getClass().isEnum()){
			return ((Enum)o1).name().equals(o2.toString());
		}
		else if(o2.getClass().isEnum()){
			return ((Enum)o2).name().equals(o1.toString());
		}
		if(isNumberType(o1.getClass())||isNumberType(o2.getClass())){
			return new BigDecimal(o1.toString()).compareTo(new BigDecimal(o2.toString()))==0;
		}
		if(o1.getClass().equals(Boolean.class)||o1.getClass().equals(boolean.class)||o2.getClass().equals(Boolean.class)||o2.getClass().equals(boolean.class)){
			return o1.toString().equals(o2.toString());
		}
		if(o1 instanceof Date && o2 instanceof Date){
			return ((Date)o1).getTime()==((Date)o2).getTime();
		}
		if(o1.getClass().isAssignableFrom(o2.getClass())||o2.getClass().isAssignableFrom(o1.getClass())){
			return false;
		}
		throw new IllegalArgumentException(String.format("It is not possible to determine whether the value of type %s:%s is equal to the value of type %s:%s",o1.getClass().getCanonicalName(),o1,o2.getClass().getCanonicalName(),o2));
	}

}
