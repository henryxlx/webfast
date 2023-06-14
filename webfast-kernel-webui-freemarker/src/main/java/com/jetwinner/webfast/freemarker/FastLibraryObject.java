package com.jetwinner.webfast.freemarker;

/**
 * @author xulixin
 */
public class FastLibraryObject {

    public static final String MODEL_VAR_NAME = "fastLib";

    Integer percent(Integer part, Integer whole) {
        if (part != null && whole != null) {
            return (int) (part.intValue() * 1.0 / whole.intValue() * 1.0 * 100);
        }
        return 0;
    }

    public String remainTime(Long value) {
        long remain = value - System.currentTimeMillis();

        if (remain <= 0) {
            return "0分钟";
        }

        if (remain <= 3600000) {
            return (remain / 60) + "分钟";
        }

        if (remain < 86400000) {
            return (remain / 3600000) + "小时";
        }

        return (remain / 86400000) + "天";
    }
}
