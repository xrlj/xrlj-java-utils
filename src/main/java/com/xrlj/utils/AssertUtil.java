package com.xrlj.utils;

import java.util.List;

public final class AssertUtil {

	private AssertUtil() {
	}

	public final static void notNull(Object object) {
		if (null == object) {
			throw new IllegalArgumentException("非法参数：null");
		}
	}

	public final static void notEmpty(String str) {
		if (str != null && "".equals(str)) {
			throw new IllegalArgumentException("非法参数：空字符串");
		}
	}

	public final static void nullOrEmpty(String str) {
		if (null == str || "".equals(str)) {
			throw new IllegalArgumentException("非法参数：null或空");
		}
	}

	public final static void nullOrEmpty(Object[] objects) {
		if (null == objects || objects.length == 0) {
			throw new IllegalArgumentException("非法参数：数组null或空");
		}
	}

	public final static void nullOrEmpty(List list) {
		if (null == list || list.size() == 0) {
			throw new IllegalArgumentException("非法参数：列表null或空");
		}
	}

}
