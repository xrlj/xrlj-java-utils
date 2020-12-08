package com.xrlj.utils;


import java.io.UnsupportedEncodingException;


public class EncodingUtils {

	public static boolean isUTF8(String string) {
		try {
			byte[] bytes = string.replace('?', 'a').getBytes("ISO-8859-1");
			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] == 63) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isGBK(String string)
			throws UnsupportedEncodingException {
		byte[] bytes = string.replace('?', 'a').getBytes("ISO-8859-1");
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 63) {
				return true;
			}
		}

		return false;
	}

	public static String toGBK(String string)
			throws UnsupportedEncodingException {
		if (string == null)
			return "";

		if (!isGBK(string)) {
			return new String(string.getBytes("ISO-8859-1"), "GBK");
		}

		return string;
	}

	public static String toISO_8859_1(String string)
			throws UnsupportedEncodingException {
		if (string == null)
			return "";
		if (isGBK(string)) {
			return new String(string.getBytes("GBK"), "ISO-8859-1");
		}

		return string;
	}

	public static String fixTheCode(String value){
		try {
			if (EncodingUtils.isUTF8(value)) {
				return value;
			} else if (EncodingUtils.isGBK(value)) {
				return value;
			} else if (java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(value)) {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				return value;
			} else {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				return value;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

}
