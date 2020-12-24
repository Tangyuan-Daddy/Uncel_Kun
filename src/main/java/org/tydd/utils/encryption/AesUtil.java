package org.tydd.utils.encryption;


/**
 * @author: minkun
 * date: 2020/10/30
 * Description:
 */
public class AesUtil {

    private static String AES = "aes";

    private static int KEY_SIZE = 128;

    private static String DEFAULT_KEY = "AES-default-key";

    /**
     * AES加密（使用默认秘钥）
     * @param message
     * @return
     */
    public static String encode(String message) {
        return EncryptUtil.keyGeneratorES(message, AES, DEFAULT_KEY, KEY_SIZE, true);
    }

    /**
     * AES加密（使用自定义秘钥）
     * @param message
     * @param key
     * @return
     */
    public static String encode(String message, String key) {
        return EncryptUtil.keyGeneratorES(message, AES, key, KEY_SIZE, true);
    }

    public static String decode(String message) {
        return EncryptUtil.keyGeneratorES(message, AES, DEFAULT_KEY, KEY_SIZE, false);
    }
}
