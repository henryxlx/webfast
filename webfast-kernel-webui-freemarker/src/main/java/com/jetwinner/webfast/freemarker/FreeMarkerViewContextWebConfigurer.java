package com.jetwinner.webfast.freemarker;

import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.mvc.WebAppOfflineInterceptor;
import com.jetwinner.webfast.mvc.extension.csrf.CsrfValidationInterceptor;
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
    private final WebAppOfflineInterceptor offlineInterceptor;
    private final FastAppConst appConst;

    public FreeMarkerViewContextWebConfigurer(FreeMarkerViewReferenceInterceptor viewReferenceInterceptor,
                                              WebAppOfflineInterceptor offlineInterceptor,
                                              FastAppConst appConst) {

        this.viewReferenceInterceptor = viewReferenceInterceptor;
        this.offlineInterceptor = offlineInterceptor;
        this.appConst = appConst;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(offlineInterceptor).order(0).addPathPatterns("/*");
        registry.addInterceptor(new CsrfValidationInterceptor()).order(1).addPathPatterns("/*");
        registry.addInterceptor(viewReferenceInterceptor).order(9999).addPathPatterns("/**")
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
