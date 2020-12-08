package com.xrlj.utils;


import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;


public class Parameter<K,V> extends HashMap<String, String> {

	private static final long serialVersionUID = -1870133372622081648L;

	public String getString(String key) {
		return this.get(key);
	}

	public Integer getInteger(String key) {
		return Integer.valueOf(this.get(key));
	}

	public Double getDouble(String key) {
		return Double.valueOf(this.get(key));
	}

	public Boolean getBoolean(String key) {
		return Boolean.valueOf(this.get(key));
	}

	public JSONObject getJSONObject(String key) {
		return JSONObject.parseObject(this.get(key));
	}

	public <T> T getObject(Class<T> javaBean) throws Exception {
		return (T) ReflexUtil.reflexObject(this, javaBean.newInstance(), javaBean);
	}

}
