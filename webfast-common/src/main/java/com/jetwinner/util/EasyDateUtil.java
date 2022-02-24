package com.jetwinner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EasyDateUtil {

    public static long toLongTime(Object obj) {
        String dateStr = String.valueOf(obj);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return System.currentTimeMillis();
        }

    }

}
