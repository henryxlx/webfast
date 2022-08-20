package com.jetwinner.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author xulixin
 */
public final class FastTimeUtil {

    private FastTimeUtil() {
        // reserved.
    }

    public static final long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;

    public static long now() {
        return System.currentTimeMillis();
    }

    public static long dateStrToLong(Object dateStrObj) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(String.valueOf(dateStrObj), dtf);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long strToTime(Object obj, String formatPattern) {
        return strToTime(String.valueOf(obj), formatPattern);
    }

    public static long strToTime(String dateTimeStr, String formatPattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatPattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, dtf);
        return toTimestamp(localDateTime);
    }

    public static String timeToDateStr(String formatPattern, long time) {
        LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
        return DateTimeFormatter.ofPattern(formatPattern).format(date);
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

    public static long timeForTheNextDay(long time) {
        LocalDateTime localDateTime = toLocalDateTime(time + ONE_DAY_MILLIS);
        return toTimestamp(localDateTime);
    }

    public static long timeForDayOfLastMonth(long time) {
        LocalDateTime localDateTime = toLocalDateTime(time);
        return toTimestamp(localDateTime.minus(1, ChronoUnit.MONTHS));
    }

    public static long timeForDayOfPreviousMonth(long time, int previousMonth) {
        LocalDateTime localDateTime = toLocalDateTime(time);
        return toTimestamp(localDateTime.minus(previousMonth, ChronoUnit.MONTHS));
    }
}
