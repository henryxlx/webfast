/*
 * Copyright (c) 2009-2020 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.validation;

import java.util.function.BiFunction;

/**
 * Description on this file, you will change here.
 *
 * @author Lixin Xu
 * @since 1.0
 * Create with Intellij IDEA on 2020-01-21 20:08
 */
public enum EasyValidType {

    Null("参数必须为 null", EasyValidHelper::isNull),

    NotNull("参数必须不为 null", EasyValidHelper::isNotNull),

    Empty("参数必须为空", EasyValidHelper::isEmpty),

    NotEmpty("参数必须非空", EasyValidHelper::isNotEmpty),

    NotBlank("参数必须非空白字符", EasyValidHelper::isNotBlank),

    True("参数必须为 true", EasyValidHelper::isTrue),

    False("参数必须为 false", EasyValidHelper::isFalse),

    Date("参数必须是一个日期 yyyy-MM-dd", EasyValidHelper::isDate),

    DateTime("参数必须是一个日期时间 yyyy-MM-dd HH:mm:ss", EasyValidHelper::isDateTime),

    Past("参数必须是一个过去的日期", EasyValidHelper::isPast),

    Future("参数必须是一个将来的日期", EasyValidHelper::isFuture),

    Today("参数必须今天的日期", EasyValidHelper::isToday),

    Enum("参数必须在枚举中", EasyValidHelper::inEnum),

    Email("参数必须是Email地址", EasyValidHelper::isEmail),

    Range("参数必须在合适的范围内", EasyValidHelper::inRange),

    NotIn("参数必须不在指定的范围内", EasyValidHelper::outRange),

    Length("参数长度必须在指定范围内", EasyValidHelper::inLength),

    gt("参数必须大于指定值", EasyValidHelper::isGreaterThan),

    lt("参数必须小于指定值", EasyValidHelper::isLessThan),

    ge("参数必须大于等于指定值", EasyValidHelper::isGreaterThanEqual),

    le("参数必须小于等于指定值", EasyValidHelper::isLessThanEqual),

    ne("参数必须不等于指定值", EasyValidHelper::isNotEqual),

    Equal("参数必须不等于指定值", EasyValidHelper::isEqual),

    Pattern("参数必须符合指定的正则表达式", EasyValidHelper::isPattern)	;

    public String msg;

    /**
     * BiFunction：接收字段值(Object)和 表达式(String)，返回是否符合规则(Boolean)
     */
    public BiFunction<Object, String, Boolean> fun;

    EasyValidType(String msg, BiFunction<Object, String, Boolean> fun) {
        this.msg = msg;
        this.fun = fun;
    }

}
