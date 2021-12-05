package com.jetwinner.platform;

import com.jetwinner.util.ValueParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: xulixin
 * Date: 2013-11-9
 * Time: 下午9:51
 */
public class SystemInfoBean {

    private Properties sysProps;

    private Runtime rt;

    public SystemInfoBean() {
        sysProps = System.getProperties();
        rt = Runtime.getRuntime();
    }

    public Map<String, Object> getSystemMap() {
        Map<String, Object> props = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy年MM月dd日 E");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        props.put("System Date", dateFormatter.format(new Date()));
        props.put("System Time", timeFormatter.format(new Date()));
        props.put("Operating System", getOsInfo());
        return props;
    }

    public Map<String, Object> getJavaMap() {
        Map<String, Object> props = new HashMap<>();
        props.put("Java Version", sysProps.getProperty("java.version"));
        props.put("Java Vendor", sysProps.getProperty("java.vendor"));
        props.put("Java Runtime", sysProps.getProperty("java.runtime.name"));
        return props;
    }

    public Map<String, Object> getJavaVirtualMachineMap() {
        Map<String, Object> props = new HashMap<>();
        props.put("JVM Version", sysProps.getProperty("java.vm.specification.version"));
        props.put("JVM Vendor", sysProps.getProperty("java.vm.specification.vendor"));
        props.put("JVM Implementation Version", sysProps.getProperty("java.vm.version"));
        props.put("Java VM", sysProps.getProperty("java.vm.name"));
        return props;
    }

    public Map<String, Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEEE, dd MMM yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        props.put("System Date", dateFormatter.format(new Date()));
        props.put("System Time", timeFormatter.format(new Date()));
        props.put("System Favourite Colour", "Chartreuse");
        props.put("Java Version", sysProps.getProperty("java.version"));
        props.put("Java Vendor", sysProps.getProperty("java.vendor"));
        props.put("JVM Version", sysProps.getProperty("java.vm.specification.version"));
        props.put("JVM Vendor", sysProps.getProperty("java.vm.specification.vendor"));
        props.put("JVM Implementation Version", sysProps.getProperty("java.vm.version"));
        props.put("Java Runtime", sysProps.getProperty("java.runtime.name"));
        props.put("Java VM", sysProps.getProperty("java.vm.name"));
        props.put("User Name", sysProps.getProperty("user.name"));
        props.put("User Timezone", sysProps.getProperty("user.timezone"));
        props.put("Operating System", sysProps.getProperty("os.name") + " " + sysProps.getProperty("os.version"));
        props.put("OS Architecture", sysProps.getProperty("os.arch"));
        return props;
    }

    public String getOsInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append(sysProps.getProperty("os.name")).append("(");
        buf.append(sysProps.getProperty("os.version")).append(" ");
        buf.append(sysProps.getProperty("os.arch")).append(")");
        return buf.toString();
    }

    public String getJavaInfo() {
        return sysProps.getProperty("java.vendor") + " " + sysProps.getProperty("java.version");
    }

    public Integer getJavaVersion() {
        return ValueParser.createInteger(sysProps.getProperty("java.version"));
    }

    public String getJvmVersion() {
        return sysProps.getProperty("java.vm.version");
    }

    public Map<String, Object> getJvmStats() {
        Map<String, Object> jvmstats = new HashMap<>();
        jvmstats.put("Total Memory", "" + getTotalMemory());
        jvmstats.put("Free Memory", "" + getFreeMemory());
        jvmstats.put("Used Memory", "" + getUsedMemory());
        return jvmstats;
    }

    public String getUptime(Long startupTime) {
        if (startupTime == null) {
            return "N/A";
        } else {
            long currentTime = System.currentTimeMillis();
            return dateDifference(startupTime.longValue(), currentTime, 4L);
        }
    }

    public long getPercentageUsed() {
        return getPercentage(getUsedMemory(), getTotalMemory());
    }

    public long getPercentageFree() {
        return getPercentage(getFreeMemory(), getTotalMemory());
    }

    private long getTotalMemory() {
        return rt.totalMemory() / 1048576L;
    }

    private long getFreeMemory() {
        return rt.freeMemory() / 1048576L;
    }

    private long getUsedMemory() {
        return getTotalMemory() - getFreeMemory();
    }

/*
    private long getPercentage(long numerator, long denom) {
        return (new MathBean()).getPercentage(numerator, denom);
    }
*/

    private long getPercentage(long portion, long total) {        // TODO: This is not finished!
        long columnWidth = Math.round(((double) portion / (double) total) * 100D);
        return columnWidth;
    }

    private final String dateDifference(long dateA, long dateB, long resolution) {
        StringBuffer sb = new StringBuffer();
        long difference = Math.abs(dateB - dateA);
        if (resolution > 0L) {
            resolution--;
            long months = difference / 2678400000L;
            if (months > 0L) {
                difference %= 2678400000L;
                if (months > 1L) {
                    sb.append("" + months + " 月" + ", ");
                }
            }
        }
        if (resolution <= 0L && sb.length() == 0) {
            return "0 月";
        }
        resolution--;
        long days = difference / 86400000L;
        if (days > 0L) {
            difference %= 86400000L;
            if (days > 1L) {
                sb.append("" + days + " 天" + ", ");
            }
        }
        if (resolution <= 0L && sb.length() == 0) {
            return "0 天";
        }
        resolution--;
        long hours = difference / 3600000L;
        if (hours > 0L) {
            difference %= 3600000L;
            if (hours > 1L) {
                sb.append("" + hours + " 小时" + ", ");
            }
        }
        if (resolution <= 0L && sb.length() == 0) {
            return "0 小时";
        }
        resolution--;
        long minutes = difference / 60000L;
        if (minutes > 0L) {
            difference %= 60000L;
            if (minutes > 1L) {
                sb.append("" + minutes + " 分" + ", ");
            }
        }
        if (resolution <= 0L && sb.length() == 0) {
            return "0 分";
        }
        resolution--;
        long seconds = difference / 1000L;
        if (seconds > 0L) {
            difference %= 1000L;
            if (seconds > 1L) {
                sb.append("" + seconds + " 秒" + ", ");
            }
        }
        if (resolution <= 0L && sb.length() == 0) {
            return "0 秒";
        }
        if (sb.length() > 2) {
            return sb.substring(0, sb.length() - 2);
        } else {
            return "";
        }
    }
}

