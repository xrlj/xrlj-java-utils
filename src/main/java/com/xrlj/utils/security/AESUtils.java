package com.xrlj.utils.security;

import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 对称加密算法-AES,DES的替代者。
 * 
 * @author xinxiamu
 */
public final class AESUtils {
	
	private AESUtils(){}


	/**
	 * AES加密。
	 * 
	 * @param hexKeyStr
	 *            十六进制密钥。
	 * @param str
	 *            要加密的字符串。
	 * @return 返回加密后的十六进制字符串
	 */
	public final static String jdkAESEncode(String hexKeyStr, String str) {
		try {
			// key转换
			Key key = new SecretKeySpec(Hex.decodeHex(hexKeyStr.toCharArray()),
					"AES");

			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(str.getBytes());
			return Hex.encodeHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * AES解密。
	 * 
	 * @param hexKeyStr
	 *            十六进制密钥。
	 * @param encodeStr
	 *            要解密的十六进制字符串。
	 * @return
	 */
	public final static String jdkAESDecode(String hexKeyStr, String encodeStr) {
		try {
			// key转换
			Key key = new SecretKeySpec(Hex.decodeHex(hexKeyStr.toCharArray()),
					"AES");

			//解密。
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Hex.decodeHex(encodeStr.toCharArray())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String jdkAESDecode(String hexKeyStr, InputStream inputStream) {
		return hexKeyStr;
	}

}
