package com.jetwinner.webfast.freemarker;

import com.jetwinner.webfast.kernel.FastAppConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xulixin
 */
@Configuration
public class FreeMarkerViewContextWebConfigurer implements WebMvcConfigurer {

    private final FreeMarkerViewReferenceInterceptor viewReferenceInterceptor;
    private final FastAppConst appConst;

    public FreeMarkerViewContextWebConfigurer(FreeMarkerViewReferenceInterceptor viewReferenceInterceptor,
                                              FastAppConst appConst) {

        this.viewReferenceInterceptor = viewReferenceInterceptor;
        this.appConst = appConst;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(viewReferenceInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/bundles/**", "/images/**", "/themes/**")
                .excludePathPatterns(appConst.getUploadPublicUrlPath() + "/**")
                .excludePathPatterns("/install/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(appConst.getUploadPublicUrlPath() + "/**")
                .addResourceLocations("file:" + appConst.getUploadPublicDirectory() + "/");
    }
}
