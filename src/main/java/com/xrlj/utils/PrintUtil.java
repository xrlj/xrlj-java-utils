package com.xrlj.utils;

import com.alibaba.fastjson.JSONObject;

public class PrintUtil {

	private PrintUtil(){}
	
	public static void println(Object msg) {
		System.out.println(msg);
	}

	public static void printlnJsonFormat(JSONObject jsonObject) {
		System.out.print(FormatUtil.formatJSON(jsonObject.toJSONString()));
	}
	
	public static void print(Object msg) {
		System.out.print(msg);
	}
}
