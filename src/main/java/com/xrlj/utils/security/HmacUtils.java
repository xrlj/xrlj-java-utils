package com.xrlj.utils.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法-MAC。 防止消息被篡改。公布key。明文，摘要消息都发送给客户端，客户端比对校验，看内容是否被篡改。
 *
 * @author xinxiamu
 */
public final class HmacUtils {

    public final static String getJdkHmacMD5(String hexKey, String str) {
        try {
            SecretKey restoreSecretKey = new SecretKeySpec(Hex.decodeHex(hexKey
                    .toCharArray()), "HmacMD5");// 还原密钥
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            mac.init(restoreSecretKey);
            return Hex.encodeHexString(mac.doFinal(str.getBytes()));// 执行消息摘要并转换成十六进制
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public final static String getJdkHmacSHA256(String hexKey, String str) {
        try {
            SecretKey restoreSecretKey = new SecretKeySpec(Hex.decodeHex(hexKey
                    .toCharArray()), "HmacSHA256");// 还原密钥
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            mac.init(restoreSecretKey);
            return Hex.encodeHexString(mac.doFinal(str.getBytes()));// 执行消息摘要并转换成十六进制
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static byte[] getJdkHmacSHA256(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data 签名内容
     * @param key  秘钥
     * @return
     */
    public static byte[] getJdkHmacSHA256(byte[] data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密并返回加密后哈希散列字符串。
     * @param key
     * @param data
     * @return
     */
    public static String getJdkHmacSHA256(String key,byte[] data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byteArrayToHexString(mac.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }


}
