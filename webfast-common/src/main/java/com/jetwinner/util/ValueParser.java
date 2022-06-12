package com.jetwinner.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author henry [xulixin at jetwinner home]
 * @since 1.0
 */
public abstract class ValueParser {

    public static int parseInt(String value) {
        int result = 0;
        try {
            result = Integer.parseInt(value != null ? value.trim() : value);
        } catch (NumberFormatException e) {
            // noops.
        }
        return result;
    }

    public static int parseInt(Object value) {
        return parseInt(value != null ? value.toString() : null);
    }

    public static Integer createInteger(String value) {
        Integer result;
        try {
            result = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            result = Integer.valueOf(0);
        }
        return result;
    }

    public static Integer createInteger(Object value) {
        return createInteger(value != null ? value.toString() : null);
    }

    public static List<Integer> createIntegerList(String ids) {
        List<Integer> list = new ArrayList<Integer>();
        if (ids != null) {
            String[] array = ids.split(",");
            for (String s : array) {
                list.add(createInteger(s));
            }
        }
        return list;
    }

    public static double parseDouble(String value) {
        double result = 0.0;
        try {
            result = Double.parseDouble(value != null ? value.trim() : value);
        } catch (NumberFormatException e) {
            // noops.
        }
        return result;
    }

    public static double parseDouble(Object value) {
        return parseDouble(value == null ? null : value.toString());
    }

    public static Double createDouble(String value) {
        Double result;
        try {
            result = Double.valueOf(value);
        } catch (NumberFormatException e) {
            result = Double.valueOf(0);
        }
        return result;
    }

    public static Double createDouble(Object value) {
        return createDouble(value == null ? null : value.toString());
    }

    public static float parseFloat(String value) {
        float result = 0;
        try {
            result = Float.parseFloat(value != null ? value.trim() : value);
        } catch (NumberFormatException e) {
            // noops.
        }
        return result;
    }

    public static float parseFloat(Object value) {
        return parseFloat(value == null ? null : value.toString());
    }

    public static Float createFloat(String value) {
        Float result;
        try {
            result = Float.valueOf(value);
        } catch (NumberFormatException e) {
            result = Float.valueOf(0);
        }
        return result;
    }

    public static Float createFloat(Object value) {
        return createFloat(value == null ? null : value.toString());
    }

    public static Long toLong(Object obj) {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        Long value;
        try {
            value = Long.valueOf(obj == null ? "0" : String.valueOf(obj));
        } catch (NumberFormatException e) {
            value = 0L;
        }
        return value;
    }

    public static long parseLong(String value) {
        long result = 0;
        try {
            result = Long.parseLong(value != null ? value.trim() : value);
        } catch (NumberFormatException e) {
            // noops.
        }
        return result;
    }

    public static long parseLong(Object value) {
        return parseLong(value != null ? value.toString() : null);
    }
}
