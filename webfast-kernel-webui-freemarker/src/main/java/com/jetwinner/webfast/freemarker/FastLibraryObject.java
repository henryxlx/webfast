package com.jetwinner.webfast.freemarker;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.FastTimeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public class FastLibraryObject {

    public static final String MODEL_VAR_NAME = "fastLib";

    Integer percent(Integer part, Integer whole) {
        if (part != null && whole != null) {
            return (int) (part * 1.0 / whole * 1.0 * 100);
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

    public String duration(long value) {
        long minutes = value / (60 * 1000);
        long seconds = (value - minutes * 60 * 1000) / 1000;
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    public String plainText(String text) {
        if (text != null) {
            return EasyStringUtil.plainTextFilter(text);
        }
        return text;
    }

    public String plainText(String text, int length) {
        if (text != null && text.length() > length) {
            return EasyStringUtil.plainTextFilter(text, length);
        }
        return text;
    }

    public String fileSize(long size) {
        double currentValue = 0.0;
        String currentUnit = "";
        Map<String, Double> unitExps = new HashMap<>(4);
        unitExps.put("B", 0.0);
        unitExps.put("KB", 1.0);
        unitExps.put("MB", 2.0);
        unitExps.put("GB", 3.0);
        for (String unit : unitExps.keySet()) {
            double exp = unitExps.get(unit);
            double divisor = Math.pow(1000, exp);
            currentUnit = unit;
            currentValue = size / divisor;
            if (currentValue < 1000) {
                break;
            }
        }
        return String.format("%.1f", currentValue) + currentUnit;
    }

    public String smartTime(long time) {
        long diff = (System.currentTimeMillis() - time) / 1000;
        if (diff < 0) {
            return "未来";
        }

        if (diff == 0) {
            return "刚刚";
        }

        if (diff < 60) {
            return diff + "秒前";
        }

        if (diff < 3600) {
            return Math.round(diff / 60) + "分钟前";
        }

        if (diff < 86400) {
            return Math.round(diff / 3600) + "小时前";
        }

        if (diff < 2592000) {
            return Math.round(diff / 86400) + "天前";
        }

        if (diff < 31536000) {
            return FastTimeUtil.timeToDateTimeStr("MM-dd", time);
        }

        return FastTimeUtil.timeToDateTimeStr("yyyy-MM-dd", time);
    }
}
