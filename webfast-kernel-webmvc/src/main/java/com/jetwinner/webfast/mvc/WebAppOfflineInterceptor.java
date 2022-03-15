package com.jetwinner.webfast.mvc;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.FastAppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xulixin
 */
@Component
public class WebAppOfflineInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebAppOfflineInterceptor.class);

    private final FastAppConst appConst;
    private final UserAccessControlService userAccessControlService;

    public WebAppOfflineInterceptor(FastAppConst appConst, UserAccessControlService userAccessControlService) {
        this.appConst = appConst;
        this.userAccessControlService = userAccessControlService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (appConst.getOffline() && !userAccessControlService.hasAnyRole("ROLE_ADMIN", "ROLE_SUPER_ADMIN")) {
            response.setContentType("text/html; charset=" + StandardCharsets.UTF_8);
            OutputStream out = response.getOutputStream();
            ClassPathResource resource = new ClassPathResource("/static/offline.html");
            FileCopyUtils.copy(resource.getInputStream(), out);
            out.flush();
            return false;
        }
        return true;
    }

}
