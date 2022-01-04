package com.jetwinner.toolbag;

/**
 * @author xulixin
 */
public class ConvertIpToolkit {

    private static IpRealDataSeeker ipSeeker = IpRealDataSeeker.getInstance();

    public static String convertIp(String ip) {
        return ipSeeker == null ? "不能查询IP" : ipSeeker.getAddress(ip);
    }
}
