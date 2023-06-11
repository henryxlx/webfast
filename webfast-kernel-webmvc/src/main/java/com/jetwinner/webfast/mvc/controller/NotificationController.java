package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppNotificationService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jingjianxin
 */
@Controller("webfastSiteNotificationController")
public class NotificationController {

    private final AppUserService userService;
    private final AppNotificationService notificationService;

    public NotificationController(AppUserService userService, AppNotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @RequestMapping("/notification")
    public String indexPage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        if (user == null) {
            throw new RuntimeGoingException("Access denied.");
        }

        Paginator paginator = new Paginator(request,
                notificationService.getUserNotificationCount(user.getId()),
                20);

        model.addAttribute("notifications",
                notificationService.findUserNotifications(user.getId(),
                        paginator.getOffsetCount(), paginator.getPerPageCount()));

        notificationService.clearUserNewNotificationCounter(user.getId());
        user.setNewNotificationNum(0);
        model.addAttribute("paginator", paginator);
        return "/notification/index";
    }
}
