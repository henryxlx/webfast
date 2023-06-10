package com.jetwinner.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xulixin
 */
public final class EasyDateUtil {

    private EasyDateUtil() {
        // reserved.
    }

    public static String today(String formatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        return dateFormat.format(new Date());
    }
}
