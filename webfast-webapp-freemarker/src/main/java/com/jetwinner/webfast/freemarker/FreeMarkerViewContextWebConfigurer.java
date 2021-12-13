package com.jetwinner.webfast.freemarker;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xulixin
 */
@Configuration
public class FreeMarkerViewContextWebConfigurer implements WebMvcConfigurer {

    private final FreeMarkerViewReferenceInterceptor viewReferenceInterceptor;

    public FreeMarkerViewContextWebConfigurer(FreeMarkerViewReferenceInterceptor viewReferenceInterceptor) {
        this.viewReferenceInterceptor = viewReferenceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(viewReferenceInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/bundles/**", "/images/**", "/themes/**")
                .excludePathPatterns("/install/**");
    }

}
