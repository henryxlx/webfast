package com.jetwinner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author xulixin
 */
public final class FastTimeUtil {

    private FastTimeUtil() {
        // reserved.
    }

    public static long now() {
        return System.currentTimeMillis();
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

    public static String timeToDateStr(String dateFormat, long time) {
        Date date = new Date(time);
        return new SimpleDateFormat(dateFormat).format(date);
    }

    private static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    private static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long timeForFirstDayOfMonth(long time) {
        LocalDateTime localDateTime = toLocalDateTime(time);
        LocalDateTime firstDayOfMonth = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        return toTimestamp(firstDayOfMonth);
    }

    public static long startTimeOfTheNextDay(long time) {
        LocalDateTime localDateTime = toLocalDateTime(time + 24 * 60 * 60 * 1000);
        return toTimestamp(localDateTime);
    }

    public static long startTimeOfDayLastMonth(long time) {
        LocalDateTime localDateTime = toLocalDateTime(time);
        return toTimestamp(localDateTime.minus(1, ChronoUnit.MONTHS));
    }

    public static long startTimeOfDayLastFewMonth(long time, int lastWhichMonth) {
        LocalDateTime localDateTime = toLocalDateTime(time);
        return toTimestamp(localDateTime.minus(lastWhichMonth, ChronoUnit.MONTHS));
    }
}
