package com.jetwinner.util;

import org.junit.Test;

public class FastTimeUtilTest {

    @Test
    public void dateStrToLong() {
        String dateStr = "2023-02-22";
        long time = FastTimeUtil.dateStrToLong(dateStr);
        System.out.println("time = " + time);
    }

    @Test
    public void dateTimeStrToLong() {
        String dateTimeStr = "2023-02-22 17:27:22";
        long time = FastTimeUtil.dateTimeStrToLong(dateTimeStr);
        System.out.println("time = " + time);
    }

}