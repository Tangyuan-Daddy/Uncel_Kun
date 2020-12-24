package org.tydd.utils.encryption;


import java.util.Base64;

/**
 * @author: minkun
 * date: 2020/10/30
 * Description: Base64工具
 */
public class Base64Util {

    /**
     * 加密
     * @param message 明文
     * @return
     */
    public static String encode(String message) {
        return new String(Base64.getEncoder().encode(message.getBytes()));
    }

    /**
     * 解密
     * @param message 密文
     * @return
     */
    public static String decode(String message) {
        return new String(Base64.getDecoder().decode(message));
    }
}
