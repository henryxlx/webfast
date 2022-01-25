package com.jetwinner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author xulixin
 */
public final class FastTimeUtil {

    private FastTimeUtil() {
        // reserved.
    }

    public static long dateStrToLong(Object dateStrObj) {
        return dateStrToLong(dateStrObj, "yyyy-MM-dd HH:mm");
    }

    public static long dateStrToLong(Object dateStrObj, String formatPattern) {
        long result = 0;
            SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
            try {
                result = sdf.parse(String.valueOf(dateStrObj)).getTime();
            } catch (ParseException e) {
                throw new RuntimeException("FastTimeUtil dateStrToLong error: " + e.getMessage());
            }
        return result;
    }
}
