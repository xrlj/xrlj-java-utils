package com.xrlj.utils.security;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Base64工具类。基于apche common code库。
 * 
 * @author xinxiamu
 *
 */
public final class Base64Utils {

	private static final byte[] base64Alphabet = new byte[255];

	private Base64Utils() {
	}

	private static boolean isBase64(byte octect) {
		if (octect == 61) {
			return true;
		} else {
			return octect >= 0 && base64Alphabet[octect] != -1;
		}
	}

	public static boolean isBase64(byte[] arrayOctect) {
		arrayOctect = discardWhitespace(arrayOctect);
		int length = arrayOctect.length;
		if (length == 0) {
			return true;
		} else {
			for(int i = 0; i < length; ++i) {
				if (!isBase64(arrayOctect[i])) {
					return false;
				}
			}

			return true;
		}
	}

	static byte[] discardWhitespace(byte[] data) {
		byte[] groomedData = new byte[data.length];
		int bytesCopied = 0;
		byte[] packedData = data;
		int var4 = data.length;
		int var5 = 0;

		while(var5 < var4) {
			byte aByte = packedData[var5];
			switch(aByte) {
				default:
					groomedData[bytesCopied++] = aByte;
				case 9:
				case 10:
				case 13:
				case 32:
					++var5;
			}
		}

		packedData = new byte[bytesCopied];
		System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
		return packedData;
	}

	/**
	 * 将文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param filePath
	 *            文件所在路径。
	 * @return 返回图片生成base64码的字符串，否则返回null
	 */
	public static String getFileBase64Str(String filePath) {
		try {
			byte[] data = FileUtils.readFileToByteArray(new File(filePath));
			// 对字节数组Base64编码
			String str = base64Encode(data);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getFileBase64Str(InputStream fileInputStream) {
		try {
			byte[] data = IOUtils.toByteArray(fileInputStream);
			// 对字节数组Base64编码
			String str = base64Encode(data);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * base64字符串转化成文件
	 * 
	 * @param base64Str
	 *            图片base64字符串
	 * @param fileDestinationPath
	 *            解码生成图片保存的路径
	 * @return 返回图片保存路径，否则返回null
	 */
	public static String generateFileByBase64Str(String base64Str, String fileDestinationPath) {
		try {
			if (base64Str == null || base64Str == null) { // 图像数据为空
				throw new NullPointerException("图片base64字符串为NULL");
			}

			// 解码
			byte[] data = base64DecodeToBytes(base64Str);
			FileUtils.writeByteArrayToFile(new File(fileDestinationPath), data);

			return fileDestinationPath;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * base64加密。
	 * 
	 * @param encodeStr
	 * @return
	 */
	public final static String base64Encode(String encodeStr) {
		nullOrEmptyException(encodeStr);
		byte[] encodeBytes = Base64.encodeBase64(encodeStr.getBytes());
		return new String(encodeBytes);
	}

	public final static String base64Encode(byte[] bytes) {
		if (bytes == null || bytes.length < 0) {
			throw new IllegalAccessError("非法参数");
		}
		byte[] encodeBytes = Base64.encodeBase64(bytes);
		return new String(encodeBytes);
	}

	/**
	 * base64解密为明文。
	 * 
	 * @param base64EncodeStr
	 * @return
	 */
	public final static String base64Decode(String base64EncodeStr) {
		nullOrEmptyException(base64EncodeStr);
		return new String(Base64.decodeBase64(base64EncodeStr.getBytes()));
	}

	public final static byte[] base64DecodeToBytes(String base64EncodeStr) {
		nullOrEmptyException(base64EncodeStr);
		return Base64.decodeBase64(base64EncodeStr.getBytes());
	}

	private static void nullOrEmptyException(String str) {
		if (null == str || "".equals(str)) {
			throw new NullPointerException("加密字符串不能为null或者空");
		}
	}
}
