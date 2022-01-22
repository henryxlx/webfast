package com.jetwinner.util;

import java.io.File;

/**
 * @author xulixin
 */
public class PhpStringUtil {

    private static boolean contains(String str, char ch) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (ch == str.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    public static String ltrim(String str, String trimStr) {
        int pos = 0, len = str.length();
        for (int i = 0; i < len; i++) {
            if (contains(trimStr, str.charAt(i))) {
                pos += 1;
            } else {
                break;
            }
        }
        return str.substring(pos);
    }

    public static String rtrim(String str, String trimStr) {
        int pos = str.length() - 1;
        for (int i = pos; i >= 0 ; i--) {
            if (contains(trimStr, str.charAt(i))) {
                pos -= 1;
            } else{
                break;
            }
        }
        return str.substring(0, pos + 1);
    }

    public static String dirname (String path) {
        return dirname(path, 1);
    }
    public static String dirname (String path, int levels) {
        if (levels < 1) {
            levels = 1;
        }
        char pathSeparatorChar = getPathSeparatorChar(path);
        int endIndex = path.lastIndexOf(pathSeparatorChar);
        if (endIndex == -1) {
            return path;
        }
        int fromIndex = endIndex - 1;
        int pos = 0;
        for (int i = 0; i < levels; i++) {
            pos = path.lastIndexOf(pathSeparatorChar, fromIndex);
            if (pos == 0) {
                break;
            }
            fromIndex = pos - 1;
        }
        if (pos == -1) {
            pos = 0;
        }
        return path.substring(pos, endIndex);
    }

    private static char getPathSeparatorChar(String path) {
        if (path.lastIndexOf('/') > 0) {
            return '/';
        }
        if (path.lastIndexOf('\\') > 0) {
            return '\\';
        }
        return File.separatorChar;
    }

    public static String basename(String path) {
        return basename(path, null);
    }

    public static String basename(String path, String suffix) {
        int pos = getPathAndFilenameSeparatorPosition(path);
        String filename = path.substring(pos > 0 ? pos + 1 : 0);
        return suffix == null ? filename : filename.replace(suffix, "");
    }

    private static int getPathAndFilenameSeparatorPosition(String fullPath) {
        int pos = fullPath.lastIndexOf('/');
        if (pos > 0) {
            return pos;
        }
        pos = fullPath.lastIndexOf('\\');
        return pos > 0 ? pos : 0;
    }

    public static String ucfirst(String s) {
        char[] cs = s.toCharArray();
        if (cs[0] >= 97 && cs[0] <= 122) {
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return s;
    }
}
