package com.xrlj.utils.security;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 消息摘要算法-SHA。
 * 防止消息在传送过程中被串改。
 * @author xinxiamu
 *
 */
public final class SHAUtils {

	private SHAUtils(){}
	
	/**
	 * sha1 160位。
	 * @param str
	 * @return
	 */
	public final static String getJdkSHA1Str(String str) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			
			byte[] byteArray = messageDigest.digest();

			//转成十六进制
			StringBuffer sha1StrBuff = new StringBuffer();

			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					sha1StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					sha1StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}

			return sha1StrBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}

	public final static String getCCSHA1Str(String str) {
		return DigestUtils.sha1Hex(str.getBytes());
	}
	
	public final static String getCCSHA256Str(String str) {
		return DigestUtils.sha256Hex(str.getBytes());
	}
}
