package com.jetwinner.webfast.mvc;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    public static void lookupAdminLayout(HttpServletRequest request, Model model) {
        String layoutName = request.getParameter("layout");
        if (EasyStringUtil.isNotBlank(layoutName)) {
            model.addAttribute("layout", String.format("/admin/%s/layout.ftl", layoutName));
        }
    }

    public static ModelAndView redirect(String gotoUrl) {
        return new ModelAndView("redirect:" + gotoUrl);
    }

    public static String generateUrl(String baseUrl, Map<String, Object> params) {
        StringBuilder buf = new StringBuilder(baseUrl);
        int lastCharIndex = -1;
        if (params.size() > 0) {
            buf.append('?');
            params.forEach((k, v) -> {
                buf.append(k).append("=").append(v).append("&");
            });
            lastCharIndex = buf.lastIndexOf("&");
        }
        return lastCharIndex != -1 ? buf.substring(0, lastCharIndex) : buf.toString();
    }
}
