package com.jetwinner.webfast.mvc.viewobj;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public class TimeRange {

    private long startTime;

    private long endTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> model = new HashMap<>(2);
        model.put("startTime", this.startTime);
        model.put("endTime", this.endTime);
        return model;
    }

    public static TimeRange create(long startTime, long endTime) {
        TimeRange timeRange = new TimeRange();
        timeRange.setStartTime(startTime);
        timeRange.setEndTime(endTime);
        return timeRange;
    }

}
