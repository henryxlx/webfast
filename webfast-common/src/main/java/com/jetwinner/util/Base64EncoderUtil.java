package com.jetwinner.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author xulixin
 */
public class Base64EncoderUtil {

    private Base64EncoderUtil() {
        // reserved.
    }

    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(Object obj) {
        return obj == null ? null : decode(obj.toString());
    }

    public static String decode(String encodeStr) {
        return new String(Base64.getDecoder().decode(encodeStr), StandardCharsets.UTF_8);
    }
}
