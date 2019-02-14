package com.xrlj.utils.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;

/**
 * 生成密钥辅助类。
 * @author xinxiamu
 */
public class KeyTools {

	/**
	 * 对称加密算法生成密钥。每次都不一样。
	 * @param encodeType	加密类型。{@link KeyEnum}
	 * @param keyLenght	秘钥长度
	 * @return 返回十六进制密钥
	 * @throws Exception
	 */
	public static String generateKey(String encodeType,int keyLenght) {
		try {
			//获得密钥
			KeyGenerator keyGenerator = KeyGenerator.getInstance(encodeType);
			if (keyLenght != 0) {
				keyGenerator.init(keyLenght);//长度
			}
			SecretKey security = keyGenerator.generateKey();
			byte[] key = security.getEncoded();
			return Hex.encodeHexString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
