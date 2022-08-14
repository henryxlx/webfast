package com.jetwinner.toolbag;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public class ConvertIpToolkit {

    private static final IpRealDataSeeker IP_SEEKER = IpRealDataSeeker.getInstance();

    public static void convertIp(List<Map<String, Object>> list, String fieldName) {
        list.forEach(e -> {
            Object ip = e.get(fieldName);
            e.put(fieldName, ip != null ? convertIp(String.valueOf(ip)) : "不能查询IP");
        });
    }

    public static String convertIp(String ip) {
        return IP_SEEKER == null ? "不能查询IP" : IP_SEEKER.getAddress(ip);
    }
}
