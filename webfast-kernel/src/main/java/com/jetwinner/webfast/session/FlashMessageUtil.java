package com.jetwinner.webfast.session;

import javax.servlet.http.HttpSession;

/**
 * @author xulixin
 */
public final class FlashMessageUtil {

    private FlashMessageUtil() {
        // reserved.
    }

    private static FlashBag getFlashBag(HttpSession session) {
        return (FlashBag) session.getAttribute(FlashBag.DEFAULT_SESSION_KEY);
    }
    public static void setFlashMessage(String level, String message, HttpSession session) {
        getFlashBag(session).add(level, message);
    }
}
