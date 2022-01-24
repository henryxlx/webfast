package com.jetwinner.webfast.mvc;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.webfast.kernel.AppUser;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
public class BaseControllerHelper {

    /**
     * 创建消息提示响应
     *
     * @param  type        消息类型：info, warning, error
     * @param  message     消息内容
     * @param  title       消息抬头
     * @param  duration    消息显示持续的时间
     * @param  gotoUrl     消息跳转的页面
     * @return Response
     */
    public static ModelAndView createMessageResponse(String type, String message, String title, int duration, String gotoUrl) {
        if (!ArrayUtil.inArray(type, "info", "warning", "error")) {
            throw new RuntimeException("type不正确");
        }

        ModelAndView mav = new ModelAndView("/default/message");
        mav.addObject("type", type);
        mav.addObject("message", message);
        mav.addObject("title", title);
        mav.addObject("duration", duration);
        mav.addObject("gotoUrl", gotoUrl);
        return mav;
    }

    public static ModelAndView createMessageResponse(String type, String message) {
        return createMessageResponse(type, message, "", 0, null);
    }

    public static AppUser getCurrentUser(HttpServletRequest request) {
        AppUser appUser = (AppUser) request.getAttribute(AppUser.MODEL_VAR_NAME);
        return appUser == null ? new AppUser() : appUser;
    }
}
