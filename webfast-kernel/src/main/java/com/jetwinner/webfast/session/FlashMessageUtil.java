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
        String flashBagKey = "flashbag";
        FlashBag flashBag = (FlashBag) session.getAttribute(flashBagKey);
        if (flashBag == null) {
            flashBag = new FlashBag();
            session.setAttribute(flashBagKey, flashBag);
        }
        return flashBag;
    }
    public static void setFlashMessage(String level, String message, HttpSession session) {
        getFlashBag(session).add(level, message);
    }
}
