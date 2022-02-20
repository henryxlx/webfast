package com.jetwinner.util;

import java.util.Objects;

/**
 * @author xulixin
 */
public class FastStringEqualUtil {

    private FastStringEqualUtil() {
        // reserved.
    }

    public static boolean equals(Object src, Object dest) {
        if (src != null && dest != null) {
            String str1 = src.toString().trim();
            String str2 = dest.toString().trim();
            return str1.equals(str2);
        }
        return Objects.equals(src, dest);
    }

    public static boolean notEquals(Object src, Object dest) {
        return !equals(src, dest);
    }
}
