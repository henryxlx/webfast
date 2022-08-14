package com.jetwinner.util;

import static org.springframework.util.StringUtils.capitalize;
import static org.springframework.util.StringUtils.uncapitalize;

/**
 * @author xulixin
 */
public final class NamingUtil {

    private static final char SEPARATOR = '_';

    private NamingUtil() {
        // reserved.
    }

    /**
     * 首字母大写
     */
    public static String cap(String str){
        return capitalize(str);
    }

    /**
     * 首字母小写
     */
    public static String uncap(String str){
        return uncapitalize(str);
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String camelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = i != 1; // 不允许第二个字符是大写
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String capCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = camelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String uncamelCase(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
