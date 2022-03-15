package com.jetwinner.webfast.mvc;

import com.jetwinner.webfast.kernel.FastAppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xulixin
 */
@Component
public class WebAppOfflineInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebAppOfflineInterceptor.class);

    private final FastAppConst appConst;

    public WebAppOfflineInterceptor(FastAppConst appConst) {
        this.appConst = appConst;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (appConst.getOffline()) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView mav)
            throws Exception {

        if (appConst.getOffline()) {
            mav.setViewName("/offline");
        }
    }
}
