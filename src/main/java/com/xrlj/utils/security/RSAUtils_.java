package com.xrlj.utils.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 非对称加密算法-RSA.
 * 应用比较广泛。用来加密数据。
 * <P/>
 * 也可作为数字签名算法。验证数据完整性，来源，抗否认性。
 * @author xinxiamu
 */
public final class RSAUtils_ {

	private RSAUtils_(){}
	
	// -------------------- 公钥加密，私钥解密 ------------------//

	/**
	 * 私钥加密
	 * 
	 * @param privateKeyBase64
	 *            生成的base64私钥
	 * @param src
	 *            要加密内容
	 * @retursn 加密后内容以base64编码返回
	 */
	public final static String jdkRSAEncodeByPriKey(String privateKeyBase64,
			String src) {
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory
					.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(src.getBytes());
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 公钥解密。
	 * 
	 * @param pubKeyBase64
	 *            公钥。
	 * @param encodeStrBase64
	 *            加密后的内容(base64编码)
	 * @return
	 */
	public final static String jdkRSADecodeByPubKey(String pubKeyBase64,
			String encodeStrBase64) {
		try {
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					Base64.decodeBase64(pubKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] result = cipher
					.doFinal(Base64.decodeBase64(encodeStrBase64));
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// -------------------- 公钥加密，私钥解密 ------------------//

	/**
	 * 公钥加密。
	 * 
	 * @param pubKeyBase64
	 * @param src
	 * @return
	 */
	public final static String jdkRSAEncodeByPubKey(String pubKeyBase64, String src) {
		try {
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					Base64.decodeBase64(pubKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] result = cipher.doFinal(src.getBytes());
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 私钥解密。
	 * 
	 * @param priKeyBase64
	 * @param encodeStrBase64
	 * @return
	 */
	public final static String jdkRSADecodeByPriKey(String priKeyBase64,
			String encodeStrBase64) {
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(priKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory
					.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] result = cipher
					.doFinal(Base64.decodeBase64(encodeStrBase64));
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//-------------------- 数字签名：私钥签名，公钥验证 ---------------------------//
	
	/**
	 * 私钥签名。MD5withRSA方式，其他的百度参考。
	 * @param priKeyBase64 私钥，base64编码。
	 * @param src	要签名的内容
	 * @return	base64编码返回签名。
	 */
	public final static String jdkRSASign(String priKeyBase64,String src) {
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(src.getBytes());
			byte[] result = signature.sign();
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * 公钥验证。MD5withRSA方式，其他的百度参考。
	 * @param pubKeyBase64	公钥，base64编码。
	 * @param src	原签名内容
	 * @param signBase64	签名。base64编码。
	 * @return
	 */
	public final static Boolean jdkRSAVerify(String pubKeyBase64,String src,String signBase64) {
		boolean flg = false;
		try {
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(src.getBytes());
			boolean bool = signature.verify(Base64.decodeBase64(signBase64));
			flg = bool;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flg;
	}

	// --------------------- 初始化密钥
	public final static void generateKey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			byte[] pubKey = rsaPublicKey.getEncoded(); // 公钥
			byte[] priKey = rsaPrivateKey.getEncoded();// 私钥
			System.out.println("pubKey:" + Base64.encodeBase64String(pubKey));
			System.out.println("priKey:" + Base64.encodeBase64String(priKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		generateKey();
//	}

}
