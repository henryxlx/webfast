package com.jetwinner.webfast.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author xulixin
 */
public class FastPasswordHelper {

    private FastPasswordHelper() {
        // reserved.
    }

    static String encodePassword(String password, String salt) {
        return encodePassword(password, salt, false);
    }

    static String encodePassword(String password, String salt, boolean isHexEncoded) {
        // 算法名称
        String algorithmName = ShiroConfig.DEFAULT_HASH_ALGORITHM_NAME;
        // 加密次数
        int times = ShiroConfig.DEFAULT_HASH_ITERATIONS;
        SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, times);
        return isHexEncoded ? simpleHash.toString() : simpleHash.toBase64();
    }
}
