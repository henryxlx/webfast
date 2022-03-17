package com.jetwinner.webfast.shiro;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author xulixin
 */
public class FastPasswordHelper {

    static final String DEFAULT_HASH_ALGORITHM_NAME = Sha256Hash.ALGORITHM_NAME;
    static final int DEFAULT_HASH_ITERATIONS = 1024;

    private FastPasswordHelper() {
        // reserved.
    }

    static String encodePassword(String password, String salt) {
        return encodePassword(password, salt, false);
    }

    static String encodePassword(String password, String salt, boolean isHexEncoded) {
        SimpleHash simpleHash = new SimpleHash(DEFAULT_HASH_ALGORITHM_NAME, password, salt, DEFAULT_HASH_ITERATIONS);
        return isHexEncoded ? simpleHash.toString() : simpleHash.toBase64();
    }
}
