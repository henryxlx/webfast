/*
 * Copyright (c) 2009-2020 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.validation.annotation;

import com.jetwinner.validation.EasyValidType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description on this file, you will change here.
 *
 * @author Lixin Xu
 * @since 1.0
 * Create with Intellij IDEA on 2020-01-19 0:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyValidParam {

    /**
     * 参数校验调用方法
     * @return
     */
    EasyValidType value() default EasyValidType.NotNull;

    /**
     * 多个值逗号隔开
     * @return
     */
    String express() default "";

    /**
     * 参数名称用.表示层级，最多支持2级如： entity.userName
     * @return
     */
    String paramName();

    /**
     * 参数类型取值举例：
     * java.lang.String
     */
    String paramType() default "";


    /**
     * 自定义错误提示信息
     * @return
     */
    String msg() default "";

}
