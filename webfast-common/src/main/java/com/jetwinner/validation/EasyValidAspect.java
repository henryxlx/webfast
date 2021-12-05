/*
 * Copyright (c) 2009-2020 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.validation;

import com.jetwinner.util.JsonUtil;
import com.jetwinner.validation.annotation.EasyValidParam;
import com.jetwinner.validation.annotation.EasyValidParams;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Description on this file, you will change here.
 *
 * @author Lixin Xu
 * @since 1.0
 * Create with Intellij IDEA on 2020-01-21 20:42
 */
@Aspect
public class EasyValidAspect {

    @Pointcut("@annotation(com.jetwinner.validation.annotation.EasyValidParam)")
    public void easyValidParam() {
    }

    @Pointcut("@annotation(com.jetwinner.validation.annotation.EasyValidParams)")
    public void easyValidParams() {
    }

    @Around("easyValidParam()")
    public Object verifyOne(ProceedingJoinPoint point) throws Throwable {
        String msg = verifyMethodParameter(point, false);
        if (null != msg) {
            throw new RuntimeException(msg);
        }
        // 通过校验，继续执行原有方法
        return point.proceed();
    }


    @Around("easyValidParams()")
    public Object verifyMore(ProceedingJoinPoint point) throws Throwable {
        String msg = verifyMethodParameter(point, true);
        if (null != msg) {
            throw new RuntimeException(msg);
        }
        return point.proceed();
    }


    /**
     * 参数校验
     *
     * @param point          切点
     * @param moreParameters 多参数校验
     * @return 错误信息
     */
    private String verifyMethodParameter(JoinPoint point, boolean moreParameters) {
        Method method = this.getMethod(point);
        String[] paramName = this.getParamName(point);
        Object[] arguments = point.getArgs();    // 获取接口传递的所有参数

        Boolean isValid = true;
        String msg = null;
        if (moreParameters) {
            EasyValidParams annotation = method.getAnnotation(EasyValidParams.class);    // AOP监听带注解的方法，所以不用判断注解是否为空
            EasyValidParam[] annos = annotation.value();
            for (EasyValidParam anno : annos) {
                String argName = anno.paramName();
                Object value = this.getParamValue(arguments, paramName, argName);    //参数值
                isValid = anno.value().fun.apply(value, anno.express());    // 执行判断 // 调用枚举类的 CheckUtil类方法
                if (!isValid) {    // 只要有一个参数判断不通过，立即返回
                    msg = anno.msg();
                    if (null == msg || "".equals(msg)) {
                        msg = argName + ": " + anno.value().msg + " " + anno.express();
                    }
                    break;
                }
            }
        } else {    // 单个参数校验
            EasyValidParam anno = method.getAnnotation(EasyValidParam.class);        // AOP监听带注解的方法，所以不用判断注解是否为空

            String argName = anno.paramName();
            Object value = this.getParamValue(arguments, paramName, argName);    //参数值
            isValid = anno.value().fun.apply(value, anno.express());    // 执行判断 // 调用枚举类的 CheckUtil类方法
            msg = anno.msg();
            if (null == msg || "".equals(msg)) {
                msg = argName + ": " + anno.value().msg + " " + anno.express();
            }
        }
        if (isValid) {
            // log.info("参数校验通过");
            return null;
        } else {
            // log.error("参数校验不通过");
            return msg;
        }
    }


    /**
     * 获取参数名称
     * jdk 1.8 特性
     *
     * @param joinPoint
     * @return
     */
    private String[] getParamName(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] strings = methodSignature.getParameterNames();
        return strings;
    }


    /**
     * 根据参数名称，获取参数值
     *
     * @param arguments
     * @param paramName
     * @param argName
     * @return
     */
    private Object getParamValue(Object[] arguments, String[] paramName, String argName) {
        Object value = null;
        String name = argName;
        if (argName.contains(".")) {
            name = argName.split("\\.")[0];
        }
        int index = 0;
        for (String string : paramName) {
            if (string.equals(name)) {
                value = arguments[index];    //基本类型取值	// 不做空判断，如果注解配置的参数名称不存在，则取值为null
                break;
            }
            index++;
        }
        if (argName.contains(".")) {    //从对象中取值
            argName = argName.split("\\.")[1];
            Map map = JsonUtil.stringToObject(value.toString(), Map.class);
            // 从实体对象中取值
            value = map.get(argName);
        }
        return value;
    }


    /**
     * 获取方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 方法
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                // log.error("" + e);
            }
        }
        return method;
    }

}
