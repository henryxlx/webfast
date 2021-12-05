/*
 * Copyright (c) 2009-2020 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.validation;

import com.jetwinner.util.EasyStringUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description on this file, you will change here.
 *
 * @author Lixin Xu
 * @since 1.0
 * Create with Intellij IDEA on 2020-01-21 20:10
 */
public final class EasyValidHelper {

    private EasyValidHelper() {
        // reserved.
    }

    /**
     * 判断value == null
     * @param value       字段值
     * @param express 这里不需要，只是为了参数统一
     * @return true or false
     */
    public static Boolean isNull(Object value, String express) {
        if (null != value) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    /**
     * 判断value != null
     * @param value       字段值
     * @param express 这里不需要，只是为了参数统一
     * @return true or false
     */
    public static Boolean isNotNull(Object value, String express) {
        if (null == value) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    /**
     * 判断value ==null || length size <= 0
     * 支持字符串判断
     * 支持集合判断
     */
    public static Boolean isEmpty(Object value, String express) {
        return !isNotEmpty(value, express);
    }


    /**
     * 判断value !=null && length、size > 0
     * 支持字符串判断
     * 支持集合判断
     */
    public static Boolean isNotEmpty(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String && "".equals(((String) value).trim())) {
            return Boolean.FALSE;
        }
        if(value instanceof Collection && CollectionUtils.isEmpty((Collection) value)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static Boolean isNotBlank(Object value, String express) {
        return EasyStringUtil.isNotBlank(value);
    }


    /**
     * 判断参数是否是 true
     * 支持Boolean类型
     * 支持String类型
     */
    public static Boolean isTrue(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof Boolean) {
            return (Boolean) value;
        }
        if(value instanceof String) {
            try {
                return Boolean.parseBoolean((String) value);
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断参数是否是 false
     * 支持Boolean类型
     * 支持String类型
     */
    public static Boolean isFalse(Object value, String express) {
        return !isTrue(value, express);
    }


    /**
     * 判断参数是否是一个日期
     * 支持Date类型
     * 支持LocalDate类型
     * 支持String类型，yyyy-MM-dd、yyyyMMdd、yyyy/MM/dd格式； 默认仅支持yyyy-MM-dd
     */
    public static Boolean isDate(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {      // 通常json格式参数，都是以字符串类型传递，优先判断
            // 验证参数，不能处理掉所有异常的符号
            // String v = ((String) value).trim().replaceAll("[-/\\s]", "");
            String v = ((String) value); //.replaceAll("[-/]", "");
            try {
                LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return Boolean.TRUE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        }
        if(value instanceof Date) {
            return Boolean.TRUE;
        }
        if(value instanceof LocalDate) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 判断参数是否是一个日期
     * 支持Date类型
     * 支持LocalDateTime类型
     * 支持String类型，yyyy-MM-dd HH:mm:ss、yyyyMMddHHmmss、yyyy/MM/dd HH:mm:ss格式； 默认仅支持yyyy-MM-dd HH:mm:ss
     */
    public static Boolean isDateTime(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {   // 通常json格式参数，都是以字符串类型传递，优先判断
            String v = ((String) value); //.replaceAll("[-/]", "");  // 验证参数，不能处理掉所有异常的符号
            try {
                LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return Boolean.TRUE;
            } catch (Exception e) {
                /*try {
                    LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    return Boolean.TRUE;
                } catch (Exception e1) {
                    return Boolean.FALSE;
                }*/
                return Boolean.FALSE;
            }
        }
        if(value instanceof Date) {
            return Boolean.TRUE;
        }
        if(value instanceof LocalDateTime) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断参数是否是一个过去时间
     * 支持Date类型
     * 支持LocalDate类型
     * 支持LocalDateTime类型
     * 支持String类型，yyyy-MM-dd、yyyyMMdd、yyyy/MM/dd格式； 默认仅支持yyyy-MM-dd
     * 支持String类型，yyyy-MM-dd HH:mm:ss、yyyyMMddHHmmss、yyyy/MM/dd HH:mm:ss格式； 默认仅支持yyyy-MM-dd HH:mm:ss
     * @param value
     * @param express
     * @return
     */
    public static Boolean isPast(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {   // 通常json格式参数，都是以字符串类型传递，优先判断
            String v = ((String) value); //.replaceAll("[-/]", "");  // 验证参数，不能处理掉所有异常的符号
            if(v.length() <= 10) { // 日期
                try {
                    LocalDate ld = LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return LocalDate.now().isAfter(ld);
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            }
            try {
                LocalDateTime ldt = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return LocalDateTime.now().isAfter(ldt);
            } catch (Exception e) {
                /*try {
                    LocalDateTime ldt = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    return LocalDateTime.now().isAfter(ldt);
                } catch (Exception e1) {
                    return Boolean.FALSE;
                }*/
                return Boolean.FALSE;
            }
        }
        if(value instanceof Date) {
            return new Date().after((Date) value);
        }
        if(value instanceof LocalDate) {
            return LocalDate.now().isAfter((LocalDate) value);
        }
        if(value instanceof LocalDateTime) {
            return LocalDateTime.now().isAfter((LocalDateTime) value);
        }
        return Boolean.FALSE;
    }

    /**
     * 判断参数是否是一个将来时间
     * 支持Date类型
     * 支持LocalDate类型
     * 支持LocalDateTime类型
     * 支持String类型，yyyy-MM-dd、yyyyMMdd、yyyy/MM/dd格式; 默认仅支持yyyy-MM-dd
     * 支持String类型，yyyy-MM-dd HH:mm:ss、yyyyMMddHHmmss、yyyy/MM/dd HH:mm:ss格式； 默认仅支持yyyy-MM-dd HH:mm:ss
     * @param value
     * @param express
     * @return
     */
    public static Boolean isFuture(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {   // 通常json格式参数，都是以字符串类型传递，优先判断
            String v = ((String) value); // .replaceAll("[-/]", "");  // 验证参数，不能处理掉所有异常的符号
            if(v.length() <= 10) { // 日期
                try {
                    LocalDate ld = LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return LocalDate.now().isBefore(ld);
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            }
            try {
                LocalDateTime ldt = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return LocalDateTime.now().isBefore(ldt);
            } catch (Exception e) {
                /*try {
                    LocalDateTime ldt = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    return LocalDateTime.now().isBefore(ldt);
                } catch (Exception e1) {
                    return Boolean.FALSE;
                }*/
                return Boolean.FALSE;
            }
        }
        if(value instanceof Date) {
            return new Date().before((Date) value);
        }
        if(value instanceof LocalDate) {
            return LocalDate.now().isBefore((LocalDate) value);
        }
        if(value instanceof LocalDateTime) {
            return LocalDateTime.now().isBefore((LocalDateTime) value);
        }
        return Boolean.FALSE;
    }


    /**
     * 判断是否是今天的日期
     * 支持Date类型
     * 支持LocalDate类型
     * 支持String类型，默认仅支持yyyy-MM-dd
     * @param value
     * @param express
     * @return
     */
    public static Boolean isToday(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {   // 通常json格式参数，都是以字符串类型传递，优先判断
            String v = ((String) value); // .replaceAll("[-/]", "");  // 验证参数，不能处理掉所有异常的符号
            try {
                LocalDate ld = LocalDate.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return LocalDate.now().equals(ld);
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        }
        if(value instanceof Date) {
            return new Date().equals((Date) value);
        }
        if(value instanceof LocalDate) {
            return LocalDate.now().equals((LocalDate) value);
        }
        return Boolean.FALSE;
    }


    /**
     * 判断是否是邮箱
     * 使用正则表达式判断
     * @param value
     * @param express
     * @return
     */
    public static Boolean isEmail(Object value, String express) {
        if(isNull(value, express)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {
            String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher((String) value);
            if (m.matches()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断参数的取值范围，逗号隔开，无空格；闭区间
     * 支持Integer、Long、Short、Float、Double、BigDecimal
     * @param value
     * @param rangeStr
     * @return
     */
    public static Boolean inRange(Object value, String rangeStr) {
        if(isNull(value, rangeStr)) {
            return Boolean.FALSE;
        }
        if(null == rangeStr || "".equals(rangeStr)) {
            return Boolean.FALSE;
        }
        if(value instanceof Integer) {
            Integer begin = Integer.valueOf(rangeStr.split(",")[0]);
            Integer end = Integer.valueOf(rangeStr.split(",")[1]);
            Integer v = ((Integer) value);
            return  begin <= v && v <= end;
        }
        if(value instanceof Long) {
            Long begin = Long.valueOf(rangeStr.split(",")[0]);
            Long end = Long.valueOf(rangeStr.split(",")[1]);
            Long v = ((Long) value);
            return  begin <= v && v <= end;
        }
        if(value instanceof Short) {
            Short begin = Short.valueOf(rangeStr.split(",")[0]);
            Short end = Short.valueOf(rangeStr.split(",")[1]);
            Short v = ((Short) value);
            return  begin <= v && v <= end;
        }
        if(value instanceof Float) {
            Float begin = Float.valueOf(rangeStr.split(",")[0]);
            Float end = Float.valueOf(rangeStr.split(",")[1]);
            Float v = ((Float) value);
            return  begin <= v && v <= end;
        }
        if(value instanceof Double) {
            Double begin = Double.valueOf(rangeStr.split(",")[0]);
            Double end = Double.valueOf(rangeStr.split(",")[1]);
            Double v = ((Double) value);
            return  begin <= v && v <= end;
        }
        if(value instanceof BigDecimal) {
            BigDecimal begin = new BigDecimal(rangeStr.split(",")[0]);
            BigDecimal end = new BigDecimal(rangeStr.split(",")[1]);
            BigDecimal v = ((BigDecimal) value);
            return  begin.compareTo(v) <=0 && v.compareTo(end) <= 0;
        }

        return Boolean.FALSE;
    }


    /**
     * 等价于  !Range
     * @param value
     * @param rangeStr
     * @return
     */
    public static Boolean outRange(Object value, String rangeStr) {
        return !inRange(value, rangeStr);
    }


    /**
     * 判断参数的取值范围，逗号隔开，无空格；闭区间
     * 判断String的length范围, rangeStr取值举例："6,18"
     * @param value
     * @param rangeStr
     * @return
     */
    public static Boolean inLength(Object value, String rangeStr) {
        if(isNull(value, rangeStr)) {
            return Boolean.FALSE;
        }
        if(null == rangeStr || "".equals(rangeStr)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {
            Integer begin = Integer.valueOf(rangeStr.split(",")[0]);
            Integer end = Integer.valueOf(rangeStr.split(",")[1]);
            Integer v = ((String) value).length();
            return  begin <= v && v <= end;
        }
        return Boolean.FALSE;
    }


    /**
     * 判断参数是否在枚举的数据中, 枚举的表达式用 英文逗号隔开，无空格，如： "男,女,太监"
     * 校验过程，不在对表达式进行校验，所以请确保表达式的格式正确
     * 支持String
     * 支持Integer Short Long
     * @param value
     * @param enumStr
     * @return
     */
    public static Boolean inEnum(Object value, String enumStr) {
        if(isNull(value, null)) {
            return Boolean.FALSE;
        }
        if(null == enumStr || "".equals(enumStr)) {
            return Boolean.FALSE;
        }
        String[] array = enumStr.split(",");
        Set<String> set = new HashSet<>(Arrays.asList(array));
        return set.contains(value.toString());
    }



    /**
     * 是否大于指定值
     * 支持Integer、Long、Short、Float、Double、BigDecimal
     * 支持String，判断length值
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isGreaterThan(Object value, String express) {
        if (value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof Integer) {
            return ((Integer) value) > Integer.valueOf(express);
        }
        if(value instanceof Long) {
            return ((Long) value) > Long.valueOf(express);
        }
        if(value instanceof Short) {
            return ((Short) value) > Short.valueOf(express);
        }
        if(value instanceof Float) {
            return ((Float) value) > Float.valueOf(express);
        }
        if(value instanceof Double) {
            return ((Double) value) > Double.valueOf(express);
        }
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(express)) > 0;
        }
        if(value instanceof String) {
            return ((String) value).length() > Integer.valueOf(express);
        }
        if(value instanceof Collection) {
            return  ((Collection) value).size() > Integer.valueOf(express);
        }
        return Boolean.FALSE;
    }


    /**
     * 是否大于等于
     * 支持Integer、Long、Short、Float、Double、BigDecimal
     * 支持String，判断length值
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isGreaterThanEqual(Object value, String express) {
        if (value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof Integer) {
            return ((Integer) value) >= Integer.valueOf(express);
        }
        if(value instanceof Long) {
            return ((Long) value) >= Long.valueOf(express);
        }
        if(value instanceof Short) {
            return ((Short) value) >= Short.valueOf(express);
        }
        if(value instanceof Float) {
            return ((Float) value) >= Float.valueOf(express);
        }
        if(value instanceof Double) {
            return ((Double) value) >= Double.valueOf(express);
        }
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(express)) >= 0;
        }
        if(value instanceof String) {
            return ((String) value).length() >= Integer.valueOf(express);
        }
        if(value instanceof Collection) {
            return  ((Collection) value).size() >= Integer.valueOf(express);
        }
        return Boolean.FALSE;

    }

    /**
     * 是否少于
     * 支持Integer、Long、Short、Float、Double、BigDecimal
     * 支持String，判断length值
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isLessThan(Object value, String express) {
        if (value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof Integer) {
            return ((Integer) value) < Integer.valueOf(express);
        }
        if(value instanceof Long) {
            return ((Long) value) < Long.valueOf(express);
        }
        if(value instanceof Short) {
            return ((Short) value) < Short.valueOf(express);
        }
        if(value instanceof Float) {
            return ((Float) value) < Float.valueOf(express);
        }
        if(value instanceof Double) {
            return ((Double) value) < Double.valueOf(express);
        }
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(express)) < 0;
        }
        if(value instanceof String) {
            return ((String) value).length() < Integer.valueOf(express);
        }
        if(value instanceof Collection) {
            return  ((Collection) value).size() < Integer.valueOf(express);
        }
        return Boolean.FALSE;
    }

    /**
     * 是否少于等于
     * 支持Integer、Long、Short、Float、Double、BigDecimal
     * 支持String，判断length值
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isLessThanEqual(Object value, String express) {
        if (value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof Integer) {
            return ((Integer) value) <= Integer.valueOf(express);
        }
        if(value instanceof Long) {
            return ((Long) value) <= Long.valueOf(express);
        }
        if(value instanceof Short) {
            return ((Short) value) <= Short.valueOf(express);
        }
        if(value instanceof Float) {
            return ((Float) value) <= Float.valueOf(express);
        }
        if(value instanceof Double) {
            return ((Double) value) <= Double.valueOf(express);
        }
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(express)) <= 0;
        }
        if(value instanceof String) {
            return ((String) value).length() <= Integer.valueOf(express);
        }
        if(value instanceof Collection) {
            return  ((Collection) value).size() <= Integer.valueOf(express);
        }
        return Boolean.FALSE;
    }

    /**
     * 判断是否 notEqual指定的值
     * 支持String、Integer、Long、Short、Float、Double、BigDecimal
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isNotEqual(Object value, String express) {
        return !isEqual(value, express);
    }


    /**
     * 判断是否Equal指定的值
     * 支持String、Integer、Long、Short、Float、Double、BigDecimal
     * 支持Collection，判断size的值
     * @param value
     * @param express
     * @return
     */
    public static Boolean isEqual(Object value, String express) {
        if (value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {
            return ((String) value).equals(express);
        }
        if(value instanceof Integer) {
            return ((Integer) value).equals(Integer.valueOf(express));
        }
        if(value instanceof Long) {
            return ((Long) value).equals(Long.valueOf(express));
        }
        if(value instanceof Short) {
            return ((Short) value).equals(Short.valueOf(express));
        }
        if(value instanceof Float) {
            return ((Float) value).equals(Float.valueOf(express));
        }
        if(value instanceof Double) {
            return ((Double) value).equals(Double.valueOf(express));
        }
        if(value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(new BigDecimal(express)) == 0;
        }
        if(value instanceof Collection) {
            return  ((Collection) value).size() == Integer.valueOf(express);
        }
        return Boolean.FALSE;
    }


    /**
     * 判断String是否满足正则表达式
     * @param value
     * @param regEx 正则表达式
     * @return
     */
    public static Boolean isPattern(Object value, String regEx) {
        if(isNull(value, null)) {
            return Boolean.FALSE;
        }
        if(value instanceof String) {
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher((String) value);
            if (m.matches()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


}
