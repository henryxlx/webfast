package com.jetwinner.webfast.kernel.datatag.annotations;

import com.jetwinner.webfast.kernel.datatag.FastDataTagRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动类上面@FastDataTagScan(basePackages={"扫描的类路径"}) 类似 @ComponentScan(basePackages = {"com.jetwinner.webfast.*"})
 * @author xulixin
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import({FastDataTagRegister.class})
public @interface FastDataTagScan {

    String[] basePackages() default {};
}
