package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppNotificationService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
@Controller("webfastSiteUserController")
public class UserController implements BlockRenderController {

    private final AppUserService userService;
    private final AppNotificationService notificationService;
    private final UserAccessControlService userAccessControlService;

    public UserController(AppUserService userService,
                          AppNotificationService notificationService,
                          UserAccessControlService userAccessControlService) {

        this.userService = userService;
        this.notificationService = notificationService;
        this.userAccessControlService = userAccessControlService;
    }

    @RequestMapping({"/user/{id}", "/user/{id}/about"})
    public String userHomePage(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("userProfile", userService.getUserProfile(id));
        return "/user/about";
    }

    @RequestMapping("/user/{id}/group")
    public String userHomeTagPage(@PathVariable Integer id, HttpServletRequest request, Model model) {
        String pathInfo = request.getServletPath();
        String pageNav = pathInfo.substring(pathInfo.lastIndexOf('/') + 1);
        model.addAttribute("pageNav", pageNav);
        model.addAttribute("user", userService.getUser(id));
        return "/user/user-home";
    }

    @RequestMapping("/user/{id}/following")
    private String followingAction(@PathVariable Integer id, Model model) {
        AppUser user = tryGetUser(id);
        //model.addAttribute("friends", userService.findAllUserFollowing(user.getId()));
        model.addAttribute("user", user);
        model.addAttribute("friendNav", "following");
        return "/user/friend";
    }

    @RequestMapping("/user/{id}/follower")
    public String followerAction(@PathVariable Integer id, Model model) {
        AppUser user = tryGetUser(id);
        //model.addAttribute("friends", userService.findAllUserFollower(user.getId()));
        model.addAttribute("user", user);
        model.addAttribute("friendNav", "follower");
        return "/user/friend";
    }

    @RequestMapping("/user/{id}/follow")
    @ResponseBody
    public Boolean followAction(@PathVariable Integer id, HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        if (!userAccessControlService.isLoggedIn()) {
            throw new RuntimeGoingException("请先登录再关注！");
        }
        try {
            this.userService.follow(user.getId(), id);
        } catch (ActionGraspException e) {
            return Boolean.FALSE;
        }

        String userShowUrl = request.getContextPath() + "/user/" + user.getId();
        String message = String.format("用户<a href='%s' target='_blank'>%s</a>已经关注了你！",
                userShowUrl, user.getUsername());
        this.notificationService.notify(id, "default", message);

        return Boolean.TRUE;
    }

    @RequestMapping("/user/{id}/unfollow")
    @ResponseBody
    public Boolean unfollowAction(@PathVariable Integer id, HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        if (!userAccessControlService.isLoggedIn()) {
            throw new RuntimeGoingException("请先登录再取消关注！");
        }

        try {
            this.userService.unFollow(user.getId(), id);
        } catch (ActionGraspException e) {
            return Boolean.FALSE;
        }

        String userShowUrl = request.getContextPath() + "/user/" + user.getId();
        String message = String.format("用户<a href='%s' target='_blank'>%s</a>对你已经取消了关注！",
                userShowUrl, user.getUsername());
        this.notificationService.notify(id, "default", message);

        return Boolean.TRUE;
    }

    private AppUser tryGetUser(Integer id) {
        AppUser user = userService.getUser(id);
        if (user == null) {
            throw new RuntimeGoingException("用户未找到！");
        }
        return user;
    }

    @GetMapping("/user/headerBlock")
    @BlockRenderMethod
    public String showHeaderBlock(AppUser user, Model model) {
        model.addAttribute("userProfile", userService.getUserProfile(user.getId()));
        return "/user/header-block";
    }
}
