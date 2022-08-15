package com.jetwinner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xulixin [ work at jetwinner home]
 * @since 1.0
 */
public final class ValueParser {

    private static final Logger logger = LoggerFactory.getLogger(ValueParser.class);

    private static final String ZERO_STRING = "0";

    public ValueParser() {
        // reserved.
    }

    public static int parseInt(String str) {
        int result = 0;

        str = str != null ? str.trim() : ZERO_STRING;

        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logger.warn("Function parseInt error: " + e + " result is " + result);
        }
        return result;
    }

    public static int parseInt(Object obj) {
        return parseInt(String.valueOf(obj));
    }

    public static Integer toInteger(String str) {
        return parseInt(str);
    }

    public static Integer toInteger(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return parseInt(String.valueOf(obj));
    }

    public static long parseLong(String str) {
        long result = 0;

        str = str != null ? str.trim() : ZERO_STRING;

        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            logger.warn("Function parseLong error: " + e + " result is " + result);
        }
        return result;
    }

    public static long parseLong(Object obj) {
        return parseLong(String.valueOf(obj));
    }

    public static Long toLong(String str) {
        return parseLong(str);
    }

    public static Long toLong(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        return parseLong(String.valueOf(obj));
    }

    public static float parseFloat(String str) {
        float result = 0;

        str = str != null ? str.trim() : ZERO_STRING;

        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            logger.warn("Function parseFloat error: " + e + " result is " + result);
        }
        return result;
    }

    public static float parseFloat(Object obj) {
        return parseFloat(String.valueOf(obj));
    }

    public static Float toFloat(String str) {
        return parseFloat(str);
    }

    public static Float toFloat(Object obj) {
        if (obj instanceof Float) {
            return (Float) obj;
        }
        return parseFloat(String.valueOf(obj));
    }

    public static double parseDouble(String str) {
        double result = 0.0;

        str = str != null ? str.trim() : ZERO_STRING;

        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            logger.warn("Function parseDouble error: " + e + " result is " + result);
        }
        return result;
    }

    public static double parseDouble(Object obj) {
        return parseDouble(String.valueOf(obj));
    }

    public static Double toDouble(String str) {
        return parseDouble(str);
    }

    public static Double toDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        return parseDouble(String.valueOf(obj));
    }
}
