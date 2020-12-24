package org.tydd.utils.encryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: minkun
 * date: 2020/10/30
 * Description: MD5工具类
 */
public class Md5Util {

    private static String MD5 = "md5";

    /**
     * 生成32位MD5值
     * @param message
     * @return
     */
    public static String encode(String message) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance(MD5).digest(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法");
        }
        String md5encode = new BigInteger(1, secretBytes).toString(16);
        int length = md5encode.length();
        for (int i = 0; i < 32 - length; i++) {
            md5encode = "0" + md5encode;
        }
        return md5encode;
    }

    /**
     * 生成32位MD5值（大写）
     * @param message
     * @return
     */
    public static String encodeWithUpCase(String message) {
        return encode(message).toUpperCase();
    }

}
