package com.jetwinner.webfast.mvc.extension.csrf;

import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xulixin
 */
public class CsrfValidationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CsrfValidationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("POST".equals(request.getMethod())) {
            if (verifyCsrfToken(request) == false) {
                logger.info(String.format("CsrfValidationInterceptor cross-site request forgery failed: client[%s, url: %s]",
                        getRemoteHost(request), request.getServletPath()));
                throw new RuntimeGoingException("");
            }
        }

        return true;
    }

    private boolean verifyCsrfToken(HttpServletRequest request) {
        String csrfToken = request.getParameter(SessionCsrfProvider.CSRF_TOKEN_NAME_IN_FORM);
        HttpSession session = request.getSession(false);
        String intention = (String) session.getAttribute(SessionCsrfProvider.CSRF_INTENTION_KEY_IN_SESSION);
        SessionCsrfProvider csrfProvider = SessionCsrfProvider.getDefaultInstance(session);
        if (csrfToken == null) {
            return true;
        }
        return csrfToken.equals(csrfProvider.generateCsrfToken(intention));
    }

    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
