package com.xrlj.utils.security;

/**
 * 加密类型枚举。AES,MAC等。
 * @author xinxiamu
 */
public enum KeyEnum {
	HMAC_MD5("HmacMD5"),
	HmacSHA256("HmacSHA256"),
	AES("AES");
	
	private String value;

	KeyEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
