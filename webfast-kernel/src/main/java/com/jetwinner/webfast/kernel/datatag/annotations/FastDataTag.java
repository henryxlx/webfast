package com.jetwinner.webfast.kernel.datatag.annotations;

import java.lang.annotation.*;

/**
 * @author xulixin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastDataTag {

    String value() default "";
}
